package com.sauzny.canal;

public class CustomCanalParameter {

    private short clientId;

    private long canalId;

    private String canalName;

    private String canalDesc;

    private String dbMasterAddress;

    private String dbStandbyAddress;

    private Integer dbMasterPort;

    private Integer dbStandbyPort;

    private String dbUserName;

    private String dbPassWord;

    private String filter;

    public CustomCanalParameter(short clientId, long canalId, String canalName, String canalDesc, String dbMasterAddress, String dbStandbyAddress, Integer dbMasterPort, Integer dbStandbyPort, String dbUserName, String dbPassWord, String filter) {
        this.clientId = clientId;
        this.canalId = canalId;
        this.canalName = canalName;
        this.canalDesc = canalDesc;
        this.dbMasterAddress = dbMasterAddress;
        this.dbStandbyAddress = dbStandbyAddress;
        this.dbMasterPort = dbMasterPort;
        this.dbStandbyPort = dbStandbyPort;
        this.dbUserName = dbUserName;
        this.dbPassWord = dbPassWord;
        this.filter = filter;
    }

    public short getClientId() {
        return clientId;
    }

    public void setClientId(short clientId) {
        this.clientId = clientId;
    }

    public long getCanalId() {
        return canalId;
    }

    public void setCanalId(long canalId) {
        this.canalId = canalId;
    }

    public String getCanalName() {
        return canalName;
    }

    public void setCanalName(String canalName) {
        this.canalName = canalName;
    }

    public String getCanalDesc() {
        return canalDesc;
    }

    public void setCanalDesc(String canalDesc) {
        this.canalDesc = canalDesc;
    }

    public String getDbMasterAddress() {
        return dbMasterAddress;
    }

    public void setDbMasterAddress(String dbMasterAddress) {
        this.dbMasterAddress = dbMasterAddress;
    }

    public String getDbStandbyAddress() {
        return dbStandbyAddress;
    }

    public void setDbStandbyAddress(String dbStandbyAddress) {
        this.dbStandbyAddress = dbStandbyAddress;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public Integer getDbMasterPort() {
        return dbMasterPort;
    }

    public void setDbMasterPort(Integer dbMasterPort) {
        this.dbMasterPort = dbMasterPort;
    }

    public Integer getDbStandbyPort() {
        return dbStandbyPort;
    }

    public void setDbStandbyPort(Integer dbStandbyPort) {
        this.dbStandbyPort = dbStandbyPort;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
