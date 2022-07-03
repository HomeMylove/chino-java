package com.homemylove.chino.dao;

import com.homemylove.chino.pojo.Echo;

import java.util.List;

public interface EchoDAO {

    void addEcho(Echo echo);

    // 根据问题获取回答
    Echo getEcho(String groupId,String question);

    void updateEcho(Echo echo);

    // echo 是否属于用户
    boolean hasEcho(Integer id,String groupId,String userId);

    List<Echo> getEchoList(String groupId,String userId);

    void deleteEcho(Integer id);

    Echo getEchoById(Integer id);

    List<Echo> getEchoListByKeyword(String groupId,String keyword);

}
