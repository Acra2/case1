package repositories;

import app.Course;
import app.SingleStudent;
import app.Student;
import app.StudentFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 10-10-2016.
 */
public class StudentRepo implements IStudentRepo {
    JdbcConnection jdbcConnection = JdbcConnection.getInstance();
    private StudentFactory studentFactory = new StudentFactory();

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<Student>();
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from STUDENT");
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                students.add(resultSetToStudent(resultSet));
            }
            jdbcConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student getStudent(Integer id) {
        Student student = null;
        try {
            Connection connection = jdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("select * from STUDENT Where id = " + id.toString());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                student =  resultSetToStudent(resultSet);
            }
            jdbcConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void addStudent(Student student) {

    }

    private Student resultSetToStudent(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String bankAccountNr = resultSet.getString("bankAccountNr");
            String address = resultSet.getString("address");
            Boolean business = resultSet.getString("business").toUpperCase() == "Y";
            String businessid = resultSet.getString("Studentid");
            return studentFactory.createStudent(id, name, address, bankAccountNr,businessid, business);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
