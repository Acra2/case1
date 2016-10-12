package repositories;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import static java.sql.Date.valueOf;

/**
 * Created by Sander on 11-10-2016.
 */
public class SubscribeRepo implements ISubscribeRepo {

    JdbcConnection jdbcConnection = JdbcConnection.getInstance();

    WeekFields weekFields = WeekFields.of(Locale.GERMANY);
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

    public ArrayList<ArrayList<Integer>> getSubscriptionsByWeek(Integer week, Integer year) {
        ArrayList<ArrayList<Integer>> subscriptions = new ArrayList<ArrayList<Integer>>();
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT r.COURSEID, r.STUDENTID FROM COURSEREGISTRATION r, Course c WHERE c.id = r.COURSEID AND c.STARTDATE BETWEEN ? AND ?");
            stmt.setDate(1, valueOf(getStartDateOfWeek(week,year)));
            stmt.setDate(2, valueOf(getEndDateOfWeek(week,year)));
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

    private LocalDate getStartDateOfWeek(Integer week, Integer year){
        return LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);
    }

    private LocalDate getEndDateOfWeek(Integer week, Integer year){
        return LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 7);
    }

    private ArrayList<Integer> resultSetToSubscription(ResultSet resultSet) throws SQLException {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(resultSet.getInt("COURSEID"));
        integers.add(resultSet.getInt("STUDENTID"));
        return integers;
    }
}
