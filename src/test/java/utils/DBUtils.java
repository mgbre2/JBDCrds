package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    // to connect to db via jdbc api
    private static Connection connection;
    // to execute query
    private static Statement statement;
    //to get actual data from the database
    private static ResultSet resultSet;

    public static void createConnection(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void destroy(){
        try {
            if(connection!=null) {
                connection.close();
            }
            if(statement!=null) {
                statement.close();
            }
            if(resultSet!=null) {
                resultSet.close();
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void executeQuery(String query) {
        try {
            //to create statement and allow scroll back and forward (TYPE_SCROLL_INSENSITIVE)
            // then from statement we will create result set that will receive data based on query
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //we are running query (command)
            resultSet = statement.executeQuery(query);
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getRowCount() {
        int amountOfRows = 0;
        try {
            //shift to the last row
            resultSet.last();
            //then this step will return amount of rows
            amountOfRows = resultSet.getRow();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return amountOfRows;
    }

    public static List<String> getColumnNames(String query){
        //create a list which will hold column names
        List<String> columns = new ArrayList<>();
        try {
            //to send a command to the data base
            executeQuery(query);
            //to get meta data of our query
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //we are getting amount of columns
            int colimnCount = rsmd.getColumnCount();
            //to loop through columns
            for(int i =1; i<=colimnCount;i++) {
                //we are adding columns one my one based on the index of column, starting from 1
                columns.add(rsmd.getColumnName(i));
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return columns;
    }

    public static List<String> executeQueryAndGetColumnValue(String query, String coulmnName) {
        executeQuery(query);
        List<String> values = new ArrayList<>();
        try {
            //get meta data
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //we want to go through all rows one by one and get value of certain column
            while(resultSet.next()) {
                values.add(resultSet.getString(coulmnName));
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return values;
    }

    public static boolean verifyEmployeeExists(String firstName, String lastName) {
        boolean exists = false;
        //COUNT(*) -- returns count of records, if record doesn't exist it will return 0
        String query = "SELECT COUNT(*) as count \n" +
                "FROM employees\n" +
                "WHERE first_name = '%s' and  last_name='%s';";
        query = String.format(query, firstName, lastName);
        executeQuery(query);
        try {
            resultSet.next();
            exists = resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     *
     * @param query
     * @return returns query result in a list of maps where the list represents
     *         collection of rows and a map represents represent a single row with
     *         key being the column name
     */
    public static List<Map<String, Object>> getQueryResultMap(String query) {
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rowList;
    }


}