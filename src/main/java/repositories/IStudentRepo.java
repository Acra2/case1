package repositories;

import app.SingleStudent;
import app.Student;

import java.util.List;

/**
 * Created by Sander on 10-10-2016.
 */
public interface IStudentRepo {
    public List<Student> getAllStudents();
    public Student getStudent(Integer id);
    public Integer addStudent(Student student);
}
