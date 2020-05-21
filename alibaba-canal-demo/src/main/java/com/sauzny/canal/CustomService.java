package com.sauzny.canal;

import com.alibaba.otter.canal.common.MQMessageUtils;
import com.alibaba.otter.canal.parse.inbound.mysql.dbsync.LogEventConvert;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

public class CustomService {

    public static void main(String[] args) {

        short cilentId = 1;
        long canalId = 1L;
        String canalName = "example01";
        String canalDesc = "my standalone server test ";
        String dbMasterAddress = "127.0.0.1";
        String dbStandbyAddress = "127.0.0.1";
        Integer dbMasterPort = 3306;
        Integer dbStandbyPort = 3306;
        String dbUserName = "mysql01";
        String dbPassWord = "mysql01";
        //protected static final String   FILTER         = ".*\\\\..*";
        String filter = "test001.canal01";

        CustomCanalParameter customCanalParameter = new CustomCanalParameter(
                cilentId,
                canalId,
                canalName,
                canalDesc,
                dbMasterAddress,
                dbStandbyAddress,
                dbMasterPort,
                dbStandbyPort,
                dbUserName,
                dbPassWord,
                filter
        );

        CanalEmbedSelector canalEmbedSelector = new CanalEmbedSelector(customCanalParameter);
        canalEmbedSelector.start();
        new Thread(() -> {
            while (true) {
                try {
                    Message message = canalEmbedSelector.selector();



                    for ( CanalEntry.Entry entry : message.getEntries()) {

                        switch (entry.getEntryType()) {
                            case TRANSACTIONBEGIN:
                                System.out.println("事务开始");
                                break;
                            case ROWDATA:
                                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                                rowChange.getRowDatasList();
                                break;
                            case TRANSACTIONEND:
                                System.out.println("事务结束");
                                break;

                            default:
                                break;

                        }


                    }
                } catch (InterruptedException | InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //LogEventConvert
    }
}
