package com.homemylove.core.load;

public class Message {
    private String groupId;
    private String msg;
    private String img;
    private String record;

    private String atId;

    public Message(String groupId) {
        this.groupId = groupId;
    }

    public Message(String groupId, String msg) {
        this.groupId = groupId;
        this.msg = msg;
    }

    public Message(String groupId, String msg, String atId) {
        this.groupId = groupId;
        this.msg = msg;
        this.atId = atId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAtId() {
        return atId;
    }

    public void setAtId(String atId) {
        this.atId = atId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Message{" +
                "groupId='" + groupId + '\'' +
                ", msg='" + msg + '\'' +
                ", img='" + img + '\'' +
                ", record='" + record + '\'' +
                ", atId='" + atId + '\'' +
                '}';
    }
}