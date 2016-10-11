package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Date.valueOf;

/**
 * Created by Sander on 11-10-2016.
 */
public class SubscribeRepo implements ISubscribeRepo {

    JdbcConnection jdbcConnection = JdbcConnection.getInstance();

    public void subscribe(Integer courseId, Integer studentId) {
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO COURSEREGISTRATION VALUES (?,?)", new String[] {"ID"});
            stmt.setInt(1,courseId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
