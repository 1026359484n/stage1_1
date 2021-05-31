package com.lagou.utils;

import com.lagou.pojo.Configuration;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParameterMappingTokenHandler implements TokenHandler {
    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    private List<ParameterMapping> parameterMappings = new ArrayList();

    public String handleToken(String content) {
        this.parameterMappings.add(this.buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }
}
