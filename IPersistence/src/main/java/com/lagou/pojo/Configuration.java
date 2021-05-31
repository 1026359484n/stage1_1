package com.lagou.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private DataSource dataSource;

    /**
     * key: statementId
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<String, MappedStatement>();
}
