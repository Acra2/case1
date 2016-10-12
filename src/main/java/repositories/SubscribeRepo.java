package repositories;

import app.Course;
import app.Subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

import static java.sql.Date.valueOf;

/**
 * Created by Sander on 11-10-2016.
 */
public class SubscribeRepo implements ISubscribeRepo {

    JdbcConnection jdbcConnection = JdbcConnection.getInstance();

    public void subscribe(Integer courseId, Integer studentId) throws Exception {
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO COURSEREGISTRATION VALUES (?,?)");
            stmt.setInt(1,courseId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("User already subscribed to course");
        }
    }

    public ArrayList<ArrayList<Integer>> getAllSubscriptions() {
        ArrayList<ArrayList<Integer>> subscriptions = new ArrayList<ArrayList<Integer>>();
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM COURSEREGISTRATION");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                subscriptions.add(resultSetToSubscription(resultSet));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }
    private ArrayList<Integer> resultSetToSubscription(ResultSet resultSet) throws SQLException {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(resultSet.getInt("COURSEID"));
        integers.add(resultSet.getInt("STUDENTID"));
        return integers;
    }
}
