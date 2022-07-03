package com.homemylove.chino.pojo;

public class Echo {
    private Integer id;
    private String groupId;
    private String userId;
    private String question;
    private String answer;

    public Echo() {
    }

    public Echo(Integer id) {
        this.id = id;
    }

    public Echo(Integer id, String groupId, String userId, String question, String answer) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.question = question;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
