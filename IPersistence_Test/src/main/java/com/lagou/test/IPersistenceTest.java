package com.lagou.test;

import com.lagou.io.Resources;

import java.io.InputStream;

public class IPersistenceTest {
    public void test(){
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    }
}
