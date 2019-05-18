package com.ybertek;

import org.junit.Before;
import org.junit.Test;
import utils.DBUtils;

import java.util.List;
import java.util.Map;

public class Assertdata {
    String name = "SdetUserAuto";
    String lastName = "AutoLastName";

    @Before
    public void setup() {
        //When we are working with more then one data base in our automation framework, we need to specify driver type before connection
        //To make sure that we are using correct driver
        //make sure your url is valid. It means you have a jdbc atrinbutes, not only host name
        String url = "jdbc:postgresql://myrdspostgresql.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
        String user = "sdetuser";
        String password = "sdetuser12345";
        DBUtils.createConnection(url, user, password);
    }
    @Test
    public void test(){
       // List<Map<String,Object>> emp=DBUtils.getQueryResultMa()
         //       ("select last_name,first_name from employeees where employee_id='105'");
        //assert.
    }
}