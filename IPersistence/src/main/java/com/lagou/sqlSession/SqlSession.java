package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {

    //查询所有
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //根据调解查询单个
    <T> T selectOne(String statementId, Object... params) throws Exception;
}
