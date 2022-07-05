package com.homemylove.chino.service.impl;

import com.homemylove.chino.dao.EchoDAO;
import com.homemylove.chino.pojo.Echo;
import com.homemylove.chino.service.EchoService;

import java.util.List;

public class EchoServiceImpl implements EchoService {

    private final EchoDAO echoDAO = null;

    @Override
    public void addEcho(Echo echo) {
        Integer id = echoDAO.hasQuestion(echo.getGroupId(), echo.getUserId(), echo.getQuestion());
        if(id != -1){
            echo.setId(id);
            echoDAO.updateEcho(echo);
        }else {
            echoDAO.addEcho(echo);
        }

    }

    @Override
    public Echo getEcho(String groupId, String question) {
        List<Echo> echoList = echoDAO.getEcho(groupId, question);
        int size = echoList.size();
        if(size==0){
            return null;
        }
        return echoList.get((int) (Math.random() * size));
    }

    @Override
    public Echo getEchoById(Integer id) {
        return echoDAO.getEchoById(id);
    }

    @Override
    public void updateEcho(Echo echo) {
        echoDAO.updateEcho(echo);
    }

    @Override
    public boolean isFull(String groupId, String userId) {
        List<Echo> echoList = echoDAO.getEchoList(groupId, userId);
        return echoList.size() >= 10;
    }


    @Override
    public Echo getMinEcho(String groupId,String userId) {
        List<Echo> echoList = echoDAO.getEchoList(groupId,userId);
       return echoList.get(0);
    }

    @Override
    public boolean hasEcho(Integer id, String groupId, String userId) {
        return echoDAO.hasEcho(id,groupId,userId);
    }

    @Override
    public void deleteEcho(Integer id) {
        echoDAO.deleteEcho(id);
    }

    @Override
    public List<Echo> getEchoList(String groupId, String userId) {
       return echoDAO.getEchoList(groupId,userId);
    }

    @Override
    public List<Echo> getEchoListByKeyword(String groupId, String keyword) {
        return echoDAO.getEchoListByKeyword(groupId,keyword);
    }
}
