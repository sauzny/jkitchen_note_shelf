package com.sauzny.canal;

import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.*;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.UUID;

public class CanalFactory {

    //protected static final String   DETECTING_SQL  = "insert into retl.xdual values(1,now()) on duplicate key update x=now()";


    public static Canal buildCanal(CustomCanalParameter customCanalParameter){
        Canal canal = new Canal();
        canal.setId(customCanalParameter.getCanalId());
        canal.setName(customCanalParameter.getCanalName());
        canal.setDesc(customCanalParameter.getCanalDesc());

        CanalParameter parameter = new CanalParameter();

        parameter.setMetaMode(MetaMode.MEMORY);
        parameter.setDataDir("./conf");
        parameter.setMetaFileFlushPeriod(1000);
        parameter.setHaMode(HAMode.HEARTBEAT);
        parameter.setIndexMode(IndexMode.MEMORY_META_FAILBACK);

        parameter.setStorageMode(StorageMode.MEMORY);
        parameter.setMemoryStorageBufferSize(32 * 1024);

        parameter.setSourcingType(SourcingType.MYSQL);
        parameter.setDbAddresses(Arrays.asList(
                new InetSocketAddress(customCanalParameter.getDbMasterAddress(), customCanalParameter.getDbMasterPort()),
                new InetSocketAddress(customCanalParameter.getDbStandbyAddress(), customCanalParameter.getDbStandbyPort())
        ));
        parameter.setDbUsername(customCanalParameter.getDbUserName());
        parameter.setDbPassword(customCanalParameter.getDbPassWord());

/*
        parameter.setPositions(Arrays.asList(
                "{\"journalName\":\"mysql-bin.000001\",\"position\":332L,\"timestamp\":\"1505998863000\"}",
                "{\"journalName\":\"mysql-bin.000001\",\"position\":332L,\"timestamp\":\"1505998863000\"}"
        ));
*/

        parameter.setSlaveId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        parameter.setDefaultConnectionTimeoutInSeconds(30);
        parameter.setConnectionCharset("UTF-8");
        parameter.setConnectionCharsetNumber((byte) 33);
        parameter.setReceiveBufferSize(8 * 1024);
        parameter.setSendBufferSize(8 * 1024);

        parameter.setDetectingEnable(false);
        //parameter.setDetectingIntervalInSeconds(10);
        //parameter.setDetectingRetryTimes(3);
        //parameter.setDetectingSQL(DETECTING_SQL);

        canal.setCanalParameter(parameter);
        return canal;
    }
}
