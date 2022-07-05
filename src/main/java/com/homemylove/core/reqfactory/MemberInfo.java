package com.homemylove.core.reqfactory;

public class MemberInfo {
    private Integer age;
    private String area;
    private String card;
    private String groupId;
    private Integer joinTime;
    private Integer lastSentTime;
    private String level;
    private String nickname;
    private String role;
    private String sex;
    private String title;
    private String userId;

    public MemberInfo() {
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Integer joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getLastSentTime() {
        return lastSentTime;
    }

    public void setLastSentTime(Integer lastSentTime) {
        this.lastSentTime = lastSentTime;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
        return "MemberInfo{" +
                "age=" + age +
                ", area='" + area + '\'' +
                ", card='" + card + '\'' +
                ", groupId='" + groupId + '\'' +
                ", joinTime=" + joinTime +
                ", lastSentTime=" + lastSentTime +
                ", level='" + level + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", sex='" + sex + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
