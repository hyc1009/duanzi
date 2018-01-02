package com.duanzi.he.duanzi.model;

import com.duanzi.he.duanzi.base.BaseResult;

import java.util.List;

/**
 * Created by he on 2017/10/31.
 */

public class UserLabelBean extends BaseResult{
    /**
     * status_code : 200
     * message : success
     * data : [{"tag_id":6,"tag_name":"商务办公","parent_id":2,"is_selected":true,"order_id":0},{"tag_id":7,"tag_name":"游戏资讯","parent_id":4,"is_selected":true,"order_id":5},{"tag_id":9,"tag_name":"智能家居","parent_id":1,"is_selected":true,"order_id":0},{"tag_id":10,"tag_name":"智能设备","parent_id":1,"is_selected":true,"order_id":0},{"tag_id":14,"tag_name":"玩机达人","parent_id":3,"is_selected":true,"order_id":5},{"tag_id":15,"tag_name":"产品赏析","parent_id":3,"is_selected":true,"order_id":5},{"tag_id":16,"tag_name":"娱乐影音","parent_id":3,"is_selected":false},{"tag_id":17,"tag_name":"科技动态","parent_id":2,"is_selected":false},{"tag_id":19,"tag_name":"外设数码","parent_id":2,"is_selected":false},{"tag_id":20,"tag_name":"游戏设备","parent_id":4,"is_selected":false},{"tag_id":26,"tag_name":"网络应用","parent_id":1,"is_selected":false}]
     */


    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tag_id : 6
         * tag_name : 商务办公
         * parent_id : 2
         * is_selected : true
         * order_id : 0
         */

        private int tag_id;
        private String tag_name;
        private int parent_id;
        private boolean is_selected;
        private int order_id;

        public int getTag_id() {
            return tag_id;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public boolean isIs_selected() {
            return is_selected;
        }

        public void setIs_selected(boolean is_selected) {
            this.is_selected = is_selected;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }
    }
    }

