package com.homemylove.chino.dao.impl;

import com.homemylove.chino.dao.EchoDAO;
import com.homemylove.chino.pojo.Echo;
import com.homemylove.core.basedao.BaseDAO;

import java.util.List;

public class EchoDAOImpl extends BaseDAO<Echo> implements EchoDAO {
    @Override
    public void addEcho(Echo echo) {
        executeUpdate("insert into echo values (0,?,?,?,?)",echo.getGroupId(),echo.getUserId(),echo.getQuestion(),echo.getAnswer());
    }

    @Override
    public Echo getEcho(String groupId, String question) {
        return load("select * from echo where groupId = ? and question = ?",groupId,question);
    }


    @Override
    public void updateEcho(Echo echo) {
        executeUpdate("update echo set question = ?, answer = ? where id = ?",echo.getQuestion(),echo.getAnswer(),echo.getId());
    }

    @Override
    public boolean hasEcho(Integer id, String groupId, String userId) {
        Echo echo = load("select * from echo where id = ? and groupId = ? and userId = ?", id, groupId, userId);
        return echo != null;
    }

    @Override
    public List<Echo> getEchoList(String groupId, String userId) {
        return executeQuery("select * from echo where groupId = ? and userId = ?",groupId,userId);
    }

    @Override
    public void deleteEcho(Integer id) {
        executeUpdate("delete from echo where id = ?",id);
    }

    @Override
    public Echo getEchoById(Integer id) {
        return load("select * from echo where id = ?",id);
    }

    @Override
    public List<Echo> getEchoListByKeyword(String groupId, String keyword) {
        return executeQuery("select * from echo where groupId = ? and question like ?",groupId,"%"+keyword+"%");
    }


}
