package com.duanzi.he.duanzi.model;

import java.util.List;

/**
 * Created by he on 2017/11/17.
 */

public class KuaidiBean {

    /**
     * message : ok
     * nu : 1143412762013
     * ischeck : 0
     * condition : 00
     * com : ems
     * status : 200
     * state : 0
     * data : [{"time":"2017-11-17 03:59:20","ftime":"2017-11-17 03:59:20","context":"离开郑州市 发往周口市","location":"郑州市"},{"time":"2017-11-16 18:25:06","ftime":"2017-11-16 18:25:06","context":"河南省邮政速递物流有限公司郑州市产业园揽投部已收件（揽投员姓名：李辉,联系电话:15890682631）","location":"郑州市"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2017-11-17 03:59:20
         * ftime : 2017-11-17 03:59:20
         * context : 离开郑州市 发往周口市
         * location : 郑州市
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
