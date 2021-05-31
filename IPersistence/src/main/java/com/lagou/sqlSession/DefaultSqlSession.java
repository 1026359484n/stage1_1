package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size()==1){
            return (T) objects.get(0);
        }
        throw new RuntimeException("查询结果为空或者返回结果过多");
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用jdk动态代理来为Dao接口生成代理对象，并返回
        Object newProxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, (InvocationHandler) (proxy, method, args) -> {
            //底层还是执行jdbc代码 //根据不同情况调用selectList或者selectOne
            //准备参数 1.statementId: sql语句的唯一标识 namespace.id=接口全限定名.方法名
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String statementId = className+"."+methodName;
            //准备参数 2.params:args
            //获取被调用方法的返回值类型
            Type genericReturnType = method.getGenericReturnType();
            //判断是否进行了泛型类型参数化
            if (genericReturnType instanceof ParameterizedType) return selectList(statementId,args);
            return selectOne(statementId,args);
        });
        return null;
    }
}
