package com.homemylove.chino.dao;

import com.homemylove.chino.pojo.Echo;

import java.util.List;

public interface EchoDAO {

    void addEcho(Echo echo);

    // 根据问题获取回答
    // 返回列表
    List<Echo> getEcho(String groupId,String question);

    void updateEcho(Echo echo);

    // echo 是否属于用户
    boolean hasEcho(Integer id,String groupId,String userId);

    // 用户是否已经拥有 question
    // 有则返回 id
    // 没有返回 -1
    Integer hasQuestion(String groupId,String userId,String question);

    List<Echo> getEchoList(String groupId,String userId);

    void deleteEcho(Integer id);

    Echo getEchoById(Integer id);

    /**
     * 根据关键词查询 echo
     * @param groupId 群号
     * @param keyword 关键词
     * @return echo 列表
     */
    List<Echo> getEchoListByKeyword(String groupId,String keyword);

}
