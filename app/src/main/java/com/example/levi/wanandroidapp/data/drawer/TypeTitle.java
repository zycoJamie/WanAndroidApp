package com.example.levi.wanandroidapp.data.drawer;


import java.util.List;

public class TypeTitle {

    /**
     * cname : 热门竞技
     * ename : jingji
     * img :
     * ext : 40
     * status : 1
     * extra :
     * isolation : 1
     * child_data : [{"cname":"英雄联盟","ename":"lol","img":"http://i8.pdim.gs/959fe4ce3455d0e376c9df23e5147dae.png","ext":"400","status":"1","isolation":"1","forbid":0},{"cname":"游戏宝贝","ename":"gbaby","img":"http://i6.pdim.gs/2d91fdffbdbd5dc1c054a61df9885a76.png","ext":"1000","status":"1","isolation":"0","forbid":0},{"cname":"堡垒之夜","ename":"fortnite","img":"http://i8.pdim.gs/4f6a43e10c1526b0020a267e9332ee21.png","ext":"1200","status":"1","isolation":"1","forbid":0},{"cname":"炉石传说","ename":"hearthstone","img":"http://i5.pdim.gs/9fcdc7d605f03970a66fc3bdb1e10eaf.png","ext":"1500","status":"1","isolation":"1","forbid":0},{"cname":"守望先锋","ename":"overwatch","img":"http://i7.pdim.gs/ac9218e97e84bd438125f98c5530b998.png","ext":"1600","status":"1","isolation":"1","forbid":0},{"cname":"DOTA1","ename":"dota1","img":"http://i7.pdim.gs/b7885f4021d7089a321d86935af3a252.png","ext":"1700","status":"1","isolation":"1","forbid":0},{"cname":"DOTA2","ename":"dota2","img":"http://i5.pdim.gs/8d0b3a1b80d7d21badf1afdf18224374.png","ext":"1800","status":"1","isolation":"1","forbid":0},{"cname":"无限法则","ename":"roe","img":"http://i9.pdim.gs/2933756d8820fbb0da1ef056a8990d9d.png","ext":"1820","status":"1","isolation":"0","forbid":0},{"cname":"魔兽争霸","ename":"war3","img":"http://i5.pdim.gs/3d940eed9eb6b3ba7f892b38f231ca2a.png","ext":"1850","status":"1","isolation":"1","forbid":0},{"cname":"穿越火线","ename":"cf","img":"http://i7.pdim.gs/abab4b11583206eebf3ad83bfef22e09.png","ext":"2100","status":"1","isolation":"1","forbid":0},{"cname":"CS:GO","ename":"csgo","img":"http://i6.pdim.gs/0ff3b4e946fe633193248ede0b641146.png","ext":"3300","status":"1","isolation":"1","forbid":0},{"cname":"使命召唤15","ename":"cod15","img":"http://i8.pdim.gs/3a4b55139d9c6fb01ed38732a7a33dea.png","ext":"3400","status":"1","isolation":"0","forbid":0},{"cname":"风暴英雄","ename":"heroes","img":"http://i8.pdim.gs/2b7e8ef7fa327b30934ea9cd186d48b7.png","ext":"3500","status":"1","isolation":"1","forbid":0},{"cname":"星际争霸2","ename":"starcraft","img":"http://i7.pdim.gs/d34f3355f2656e7d7c79be20b3391b1a.png","ext":"3600","status":"1","isolation":"1","forbid":0},{"cname":"桌游","ename":"boardgames","img":"http://i8.pdim.gs/aa14c3f572e66ac620ed10b159b41c7e.png","ext":"3650","status":"1","isolation":"1","forbid":0},{"cname":"Artifact","ename":"artifact","img":"http://i9.pdim.gs/26868926c1f7d7396e110a230b3db7ad.png","ext":"3700","status":"1","isolation":"0","forbid":0}]
     * forbid : 0
     */

    private String cname;
    private String ename;
    private String img;
    private String ext;
    private String status;
    private String extra;
    private String isolation;
    private int forbid;
    private List<ChildDataBean> child_data;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getIsolation() {
        return isolation;
    }

    public void setIsolation(String isolation) {
        this.isolation = isolation;
    }

    public int getForbid() {
        return forbid;
    }

    public void setForbid(int forbid) {
        this.forbid = forbid;
    }

    public List<ChildDataBean> getChild_data() {
        return child_data;
    }

    public void setChild_data(List<ChildDataBean> child_data) {
        this.child_data = child_data;
    }

    public static class ChildDataBean {
        /**
         * cname : 英雄联盟
         * ename : lol
         * img : http://i8.pdim.gs/959fe4ce3455d0e376c9df23e5147dae.png
         * ext : 400
         * status : 1
         * isolation : 1
         * forbid : 0
         */

        private String cname;
        private String ename;
        private String img;
        private String ext;
        private String status;
        private String isolation;
        private int forbid;

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsolation() {
            return isolation;
        }

        public void setIsolation(String isolation) {
            this.isolation = isolation;
        }

        public int getForbid() {
            return forbid;
        }

        public void setForbid(int forbid) {
            this.forbid = forbid;
        }
    }
}
