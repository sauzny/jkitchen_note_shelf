package com.sauzny.canal;

import com.alibaba.otter.canal.instance.manager.CanalInstanceWithManager;
import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.ClientIdentity;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.server.embedded.CanalServerWithEmbedded;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class CanalEmbedSelector {

    private static final Logger logger = LoggerFactory.getLogger(CanalEmbedSelector.class);

    private CustomCanalParameter customCanalParameter;

    private CanalServerWithEmbedded canalServer;

    private ClientIdentity clientIdentity;

    private String destination;
    private short clientId;
    private String filter;

    private int batchSize = 10000;
    private long batchTimeout = -1L;
    private int maxEmptyTimes = 10;

    private boolean dump = true;
    private boolean dumpDetail = true;

    // 是否处于运行中
    private volatile boolean running = false;
    private volatile long lastEntryTime = 0;

    public CanalEmbedSelector(CustomCanalParameter customCanalParameter) {
        canalServer = new CanalServerWithEmbedded();
        this.customCanalParameter = customCanalParameter;
        destination = this.customCanalParameter.getCanalName();
        clientId = this.customCanalParameter.getClientId();
        filter = this.customCanalParameter.getFilter();
    }


    public boolean isStart() {
        return running;
    }

    public void start() {
        if (running) {
            return;
        }

        canalServer.setCanalInstanceGenerator(destination -> new CanalInstanceWithManager(CanalFactory.buildCanal(customCanalParameter), filter));

        canalServer.start();
        canalServer.start(customCanalParameter.getCanalName());
        this.clientIdentity = new ClientIdentity(destination, clientId, filter);
        canalServer.subscribe(clientIdentity);// 发起一次订阅

        running = true;
    }

    public void stop() {
        if (!running) {
            return;
        }
        canalServer.stop(destination);
        canalServer.stop();
        running = false;
    }

    public Message selector() throws InterruptedException {
        int emptyTimes = 0;
        Message message = null;
        if (batchTimeout < 0) {// 进行轮询处理
            while (running) {
                message = canalServer.getWithoutAck(clientIdentity, batchSize);
                if (message == null || message.getId() == -1L) { // 代表没数据
                    applyWait(emptyTimes++);
                } else {
                    break;
                }
            }
            if (!running) {
                throw new InterruptedException();
            }
        } else { // 进行超时控制
            while (running) {
                message = canalServer.getWithoutAck(clientIdentity, batchSize, batchTimeout, TimeUnit.MILLISECONDS);
                if (message == null || message.getId() == -1L) { // 代表没数据
                    continue;
                } else {
                    break;
                }
            }
            if (!running) {
                throw new InterruptedException();
            }
        }

        List<CanalEntry.Entry> entries = null;
        if (message != null && message.isRaw()) {
            entries = new ArrayList<>(message.getRawEntries().size());
            for (ByteString entry : message.getRawEntries()) {
                try {
                    entries.add(CanalEntry.Entry.parseFrom(entry));
                } catch (InvalidProtocolBufferException e) {
                    logger.error("message.isRaw() == true, CanalEntry.Entry.parseFrom error", e);
                }
            }
        } else {
            entries = message.getEntries();
        }


        // 记录最后执行的时间
        recordLastEntryTime(entries);
        dumpMessages(entries);

        return message;
    }

    // 处理无数据的情况，避免空循环挂死
    private void applyWait(int emptyTimes) {
        int newEmptyTimes = Math.min(emptyTimes, maxEmptyTimes);
        if (emptyTimes <= 3) { // 3次以内
            Thread.yield();
        } else { // 超过3次，最多只sleep 10ms
            LockSupport.parkNanos(1000 * 1000L * newEmptyTimes);
        }
    }

    private void recordLastEntryTime(List<CanalEntry.Entry> entries) {

        // 更新一下最后的entry时间，包括被过滤的数据
        if (!CollectionUtils.isEmpty(entries)) {
            long lastEntryTime = entries.get(entries.size() - 1).getHeader().getExecuteTime();
            if (lastEntryTime > 0) {// oracle的时间可能为0
                this.lastEntryTime = lastEntryTime;
            }
        }
    }

    private void dumpMessages(List<CanalEntry.Entry> entries) {
        if (dump && logger.isInfoEnabled()) {
            String startPosition = null;
            String endPosition = null;
            if (!CollectionUtils.isEmpty(entries)) {
                startPosition = buildPositionForDump(entries.get(0));
                endPosition = buildPositionForDump(entries.get(entries.size() - 1));
            }

            // 记录一下，方便追查问题
            String SEP = SystemUtils.LINE_SEPARATOR;
            try {
                MDC.put("clientId", String.valueOf(clientId));
                logger.info(SEP + "****************************************************" + SEP);
                //logger.info(MessageDumper.dumpMessageInfo(message, startPosition, endPosition, total));
                logger.info("****************************************************" + SEP);
                if (dumpDetail) {// 判断一下是否需要打印详细信息
                    //dumpEventDatas(message.getDatas());
                    logger.info("****************************************************" + SEP);
                }
            } finally {
                MDC.remove("clientId");
            }
        }
    }

    private String buildPositionForDump(CanalEntry.Entry entry) {
        long time = entry.getHeader().getExecuteTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
                + entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
    }

    public void ack(Long messageId) {
        canalServer.ack(clientIdentity, messageId);
    }

}
