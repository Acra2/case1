package repositories;

import java.sql.*;

/**
 * Created by Sander on 5-10-2016.
 */
public class JdbcConnection {

    private static JdbcConnection jdbcConnection;
    private Connection connection;

    private JdbcConnection(){

    }

    public static JdbcConnection getInstance() {
        if (jdbcConnection == null) {
            jdbcConnection = new JdbcConnection();
        }
        return jdbcConnection;
    }

    public ResultSet getResultSet(String query) throws SQLException {
        ResultSet resultSet = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "case1", "case1");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public void closeConnection() throws SQLException {
        connection.close();
    }
}
