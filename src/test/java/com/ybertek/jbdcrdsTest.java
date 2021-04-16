package com.ybertek;

import org.junit.*;

import java.sql.*;

public class jbdcrdsTest {
    //host = room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com
    //database = hr
    //user = hr
    //password = hr
    //port = 5432 (default for postgres)
//	String connectionString = "jdbc:postgresql://host:port/database";
//	String username = "hr";
//	String password = "hr";



   //String url = "jdbc:postgresql://mussie.cksjftanzyt7.us-east-1.rds.amazonaws.com:5432/hr";
    //String username = "sdetuser";
    //String password = "sdetuser12345";
   String url = "jdbc:postgresql://myrdspostgresql.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "sdetuser";
    String password = "sdetuser12345";
    //String url = "jdbc:postgresql://room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    //String username = "hr";
    //String password = "hr";
    Connection connection;
    Statement statement;

    @Before
    public void setup() throws SQLException {
        //****It provides methods to query and update data in a database,

        //Now we are connecting to the data base from eclispe/intellij with java code and JDBC Api
        connection = DriverManager.getConnection(url, username, password);
        //We need to create a statement
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    @Test
    public void jdbcTest1() throws SQLException {
        //Now we gonna run a query, so for that we need to create a result set
        ResultSet resultSet = statement.executeQuery("SELECT * FROM locations;");
        //next() method returns boolean, so we can get value based on the column name
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("city"));
        }
        //go to first row
        resultSet.first();
        //resultSet.last();
        System.out.println("--------------");
        System.out.println(resultSet.getRow());
    }

    @Test
    public void jdbctest2() throws SQLException {
        //Now we gonna run a query, so for that we need to create a result set
        ResultSet resultSet = statement.executeQuery("SELECT * FROM locations;");
        //we need to skip first record, because it start from 0
        resultSet.next();
        //we are getting first record based on the column name
        String value = resultSet.getString("city");
        //just to output result into terminal
        System.out.println("value = "+ value);
        //if we are calling this method, it will move out of the list of records. Once we will try to get a data, we will get an exception
        //resultSet.afterLast();
        //last method moves to the end and we can get data of last record
        resultSet.last();
        String value2 = resultSet.getString("city");
        //just to output result into terminal
        System.out.println(value2);
        //this method moves to the specific row
        resultSet.absolute(20);
        String actualCity = resultSet.getString("city");
        //just to output result into terminal
        System.out.println(actualCity);
        Assert.assertEquals("Geneva", actualCity);
    }

    @Test
    public void jdbctest3() throws SQLException {
        //we want to verify that there are 107 employees
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
        //we need to go to the last row
        resultSet.last();

        int actualAmountOfRows = resultSet.getRow();
        int expectedAmountOfRows = 107;
        Assert.assertEquals(expectedAmountOfRows, actualAmountOfRows);
    }

    //Metadata about data base
    @Test
    public void jdbctest4() throws SQLException {
        //So we want to get metadata about DB
        DatabaseMetaData dbmetadata = connection.getMetaData();
        System.out.println("Username: " + dbmetadata.getUserName());
        String expcetedDBType = "PostgreSQL";
        String actual = dbmetadata.getDatabaseProductName();
        Assert.assertEquals(expcetedDBType, actual);
        System.out.println(actual + " : " + dbmetadata.getDatabaseProductVersion());
    }

    //Metadata about resultset, means metadata about or query
    @Test
    public void jdbctest5() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
        ResultSetMetaData resultsetmetadata = resultSet.getMetaData();
        System.out.println(resultsetmetadata.getColumnCount());
        System.out.println(resultsetmetadata.getColumnName(2));
        System.out.println(resultsetmetadata.getColumnDisplaySize(1));
        System.out.println(resultsetmetadata.getColumnType(1));

    }

    //print all column names for this query: SELECT * FROM employees
    @Test
    public void jdbctest6() throws SQLException {
        // get all columns names
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
        ResultSetMetaData resultsetmetadata = resultSet.getMetaData();
        for (int i = 1; i <= resultsetmetadata.getColumnCount(); i++) {
            System.out.println("Name of a specific column with index:" + resultsetmetadata.getColumnName(i));
        }
    }
        //to output data types and column names of employees table
        @Test
        public void jdbctest7() throws SQLException {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int numOfColumn = resultSetMetaData.getColumnCount();
            for(int i = 1 ; i <= numOfColumn ; i ++) {
                System.out.println("column type with index: " + i + " ==> " + resultSetMetaData.getColumnTypeName(i)+" : "+resultSetMetaData.getColumnName(i));
            }

    }

    @After
    public void teardown() throws SQLException {
        //TO close stream of data (connection)
        connection.close();


    }
}