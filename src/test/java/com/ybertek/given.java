package com.ybertek;

import org.junit.Before;
import org.junit.Test;
import utils.DBUtils;

public class given {
    String url = "jdbc:postgresql://myrdspostgresql.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "sdetuser";
    String password = "sdetuser12345";
    String query="select * from employees";

    @Before
    public void first(){

        DBUtils.createConnection(url,username,password);
    }
    @Test
    public void test(){
         DBUtils.executeQuery(query);
         DBUtils.getRowCount();
         System.out.println(DBUtils.getRowCount());
         DBUtils.getColumnNames(query);
         System.out.println(DBUtils.getColumnNames(query));
         DBUtils.executeQueryAndGetColumnValue(query,username);
         System.out.println(DBUtils.executeQueryAndGetColumnValue(query,username));
         DBUtils.getQueryResultMap(query);
         System.out.println(DBUtils.getQueryResultMap(query));
    }
}
