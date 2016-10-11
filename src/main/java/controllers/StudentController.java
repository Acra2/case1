package controllers;

import app.SingleStudent;
import app.Student;
import repositories.IStudentRepo;
import repositories.StudentRepo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 10-10-2016.
 */
public class StudentController {
    private IStudentRepo studentRepo;
    private static StudentController studentController;

    public static StudentController getInstance() {
        if (studentController == null)
            studentController = new StudentController();
        return studentController;
    }

    private StudentController() {
        studentRepo = new StudentRepo();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAllStudents();
    }

    public List<SingleStudent> getPrivateStudents() {
        return getAllStudents().stream().filter(student -> student instanceof SingleStudent).map(student -> (SingleStudent) student).collect(Collectors.toList());
    }

    public List<SingleStudent> getBusinessStudents() {
        return getAllStudents().stream().filter(student -> student.getBusinessId() != null).map(student -> (SingleStudent) student).collect(Collectors.toList());
    }

    public Student getStudent(Integer id) {
        return studentRepo.getStudent(id);
    }
}
