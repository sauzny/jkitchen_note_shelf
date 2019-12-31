package com.sauzny;

import java.util.ArrayList;
import java.util.List;

public class PolicyConvertor {

    public List<XDMOper> convert(Data data) {
        List<XDMOper> list = new ArrayList<XDMOper>();
        for(String v : data.getValue().split(",")){
            XDMOper oper = new XDMOper();
            oper.setDc(data.getDc());
            oper.setValue(v);
            list.add(oper);
        }
        return list;
    }

    class XDMOper{

        private String dc;

        private String value;

        public String getDc() {
            return dc;
        }

        public void setDc(String dc) {
            this.dc = dc;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
