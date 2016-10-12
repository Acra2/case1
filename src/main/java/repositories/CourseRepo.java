package repositories;

import app.Course;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Date.valueOf;

/**
 * Created by Sander on 10-10-2016.
 */
public class CourseRepo implements ICourseRepo {
    JdbcConnection jdbcConnection = JdbcConnection.getInstance();

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<Course>();
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from COURSE");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                courses.add(resultSetToCourse(resultSet));
            }
            jdbcConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourse(Integer id) {
        Course course = null;
        try {
            Connection connection = jdbcConnection.getConnection();

            PreparedStatement stmt = connection.prepareStatement("select * from COURSE Where id = "+id.toString());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                course = resultSetToCourse(resultSet);
            }
            jdbcConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return course;
    }

    public Integer addCourse(Course course) {
        Integer retValue = 0;

        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO COURSE (title, code, startdate, days) VALUES (?,?,?,?)", new String[] {"ID"});
            stmt.setString(1,course.getTitle());
            stmt.setString(2, course.getCode());
            stmt.setDate(3, valueOf(course.getStartDate()));
            stmt.setInt(4, course.getDays());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next() ) {
                retValue = rs.getInt(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retValue;
    }
    public Course resultSetToCourse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String title = resultSet.getString("TITLE");
        String code = resultSet.getString("CODE");
        LocalDate date = resultSet.getDate("STARTDATE").toLocalDate();
        Integer days = resultSet.getInt("DAYS");
        return new Course(id,title,code,date,days);
    }
}
