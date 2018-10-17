package com.example.levi.wanandroidapp.data.collection;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 17:33
 */
public class CollectionListBean {

    /**
     * curPage : 1
     * datas : [{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":28151,"link":"https://mp.weixin.qq.com/s/SSTHwTOvkiGmc-Bjb4URXg","niceDate":"刚刚","origin":"","originId":5857,"publishTime":1539768725000,"title":"玩 Android 又更新了一个很赞的功能","userId":11703,"visible":0,"zan":0},{"author":"谷歌开发者","chapterId":415,"chapterName":"谷歌开发者","courseId":13,"desc":"","envelopePic":"","id":28150,"link":"http://mp.weixin.qq.com/s?__biz=MzAwODY4OTk2Mg==&mid=2652047274&idx=1&sn=eca6ac24aeb0c65e2e24ada53e2015eb&chksm=808ca7efb7fb2ef9d955c0ee0c64ce08e7d618381d8f82bff81b7244ef53f0e570b2e211c908&scene=38#wechat_redirect","niceDate":"刚刚","origin":"","originId":6727,"publishTime":1539768677000,"title":"Android Studio 3.2 都有哪些更新？这些关键点不要错过","userId":11703,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 2
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * author : 鸿洋
         * chapterId : 408
         * chapterName : 鸿洋
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 28151
         * link : https://mp.weixin.qq.com/s/SSTHwTOvkiGmc-Bjb4URXg
         * niceDate : 刚刚
         * origin :
         * originId : 5857
         * publishTime : 1539768725000
         * title : 玩 Android 又更新了一个很赞的功能
         * userId : 11703
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
