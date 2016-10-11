package repositories;

import app.Course;

import java.util.List;

/**
 * Created by Sander on 10-10-2016.
 */
public interface ICourseRepo {
    public List<Course> getCourses();
    public Course getCourse(Integer id);
    public Integer addCourse(Course course);
}
