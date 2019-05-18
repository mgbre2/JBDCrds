package com.ybertek;

import org.junit.Test;

import java.sql.*;
import java.util.List;

public class newparactice {

    String url = "jdbc:postgresql://myrdspostgresql.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "sdetuser";
    String password = "sdetuser12345";
    @Test
    public void dbc() throws Exception{

        Connection connection = DriverManager.getConnection(url,username,password);

        //Statement statement= connection.createStatement();
        Statement statement= connection.createStatement
                (ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet =statement.executeQuery("select * from employees");
        resultSet.next();

        System.out.println(resultSet.getString("employee_id"));

        System.out.println(resultSet.getString("first_name"));
        System.out.println(resultSet.getRow());
//resultSet.beforeFirst();
        //while (resultSet.next()) {
        // System.out.println(resultSet.getRow() + "-" + resultSet.getString("first_name") + "-" +
        //resultSet.getString(1));


        //}

resultSet.first();
        System.out.println(resultSet.getRow());
resultSet.last();
        System.out.println(resultSet.getRow());
        resultSet.absolute(3);
        System.out.println(resultSet.getString("first_name"));
        resultSet.close();
        statement.close();
        connection.close();

    }
    @Test
    public void metabeta()throws Exception{

        Connection connection = DriverManager.getConnection(url,username,password);

        //Statement statement= connection.createStatement();
        Statement statement= connection.createStatement
                (ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        String sql="select employee_id,first_name,job_id,salary from employees";

        ResultSet resultSet =statement.executeQuery(sql);
        ResultSetMetaData metaData=resultSet.getMetaData();
        System.out.println(metaData.getColumnCount());
        System.out.println(metaData);
        for (int i=1;i<=metaData.getColumnCount();i++){
            System.out.println(i+"-->"+metaData.getColumnName(i));
        }
    }
}
