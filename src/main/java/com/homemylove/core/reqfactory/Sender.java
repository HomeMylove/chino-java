package com.homemylove.core.reqfactory;

public class Sender {
    private Integer age;
    private String area;
    private String card;
    private String level;
    private String nickname;
    private String role;
    private String unknown;
    private String title;
    private String userId;

    public Sender() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUnknown() {
        return unknown;
    }

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "age=" + age +
                ", area='" + area + '\'' +
                ", card='" + card + '\'' +
                ", level='" + level + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", unknown='" + unknown + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
