package com.homemylove.core.reqfactory;

public class RequestBody {
    private String postType;
    private String messageType;
    private Integer time;
    private String selfId;
    private String subType;
    private Object anonymous;
    private String groupId;
    private String userId;
    private String messageId;
    private Integer font;
    private String message;
    private Integer messageSeq;
    private String rawMessage;

    private Sender sender;

    public RequestBody() {
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Object getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Object object) {
        this.anonymous = object;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getFont() {
        return font;
    }

    public void setFont(Integer font) {
        this.font = font;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageSeq() {
        return messageSeq;
    }

    public void setMessageSeq(Integer messageSeq) {
        this.messageSeq = messageSeq;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "postType='" + postType + '\'' +
                ", messageType='" + messageType + '\'' +
                ", time=" + time +
                ", selfId='" + selfId + '\'' +
                ", subType='" + subType + '\'' +
                ", anonymous='" + anonymous + '\'' +
                ", groupId='" + groupId + '\'' +
                ", userId='" + userId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", font=" + font +
                ", message='" + message + '\'' +
                ", messageSeq=" + messageSeq +
                ", rawMessage='" + rawMessage + '\'' +
                ", sender=" + sender +
                '}';
    }
}

