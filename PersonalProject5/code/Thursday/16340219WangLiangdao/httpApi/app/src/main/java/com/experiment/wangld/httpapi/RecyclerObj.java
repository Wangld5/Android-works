package com.experiment.wangld.httpapi;

import com.google.gson.annotations.SerializedName;

public class RecyclerObj {
    @SerializedName("status")
    private Boolean status;
    @SerializedName("data")
    private data idata;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public data getIdata() {
        return idata;
    }

    public void setIdata(data idata) {
        this.idata = idata;
    }


    public static class data{
        @SerializedName("aid")
        private int aid;
        @SerializedName("state")
        private int state;
        @SerializedName("cover")
        private String cover;
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;
        @SerializedName("play")
        private int play;
        @SerializedName("duration")
        private String duration;
        @SerializedName("video_review")
        private int video_review;
        @SerializedName("create")
        private String create;
        @SerializedName("rec")
        private String rec;
        @SerializedName("count")
        private int count;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPlay() {
            return play;
        }

        public void setPlay(int play) {
            this.play = play;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getVideo_review() {
            return video_review;
        }

        public void setVideo_review(int video_review) {
            this.video_review = video_review;
        }

        public String getCreate() {
            return create;
        }

        public void setCreate(String create) {
            this.create = create;
        }

        public String getRec() {
            return rec;
        }

        public void setRec(String rec) {
            this.rec = rec;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }




    }
}
