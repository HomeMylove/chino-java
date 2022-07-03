package com.homemylove.chino.service;

import com.homemylove.chino.pojo.Echo;

import java.util.List;

public interface EchoService {

    void addEcho(Echo echo);

    Echo getEcho(String groupId,String question);

    Echo getEchoById(Integer id);

    void updateEcho(Echo echo);

    boolean isFull(String groupId,String userId);

    Echo getMinEcho(String groupId,String userId);

    boolean hasEcho(Integer id,String groupId,String userId);

    void deleteEcho(Integer id);

    List<Echo> getEchoList(String groupId,String userId);

    List<Echo> getEchoListByKeyword(String groupId,String keyword);
}
