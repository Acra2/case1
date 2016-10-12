package controllers;

import app.Course;
import repositories.CourseRepo;
import repositories.ICourseRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 10-10-2016.
 */
public class CourseController {
    private ICourseRepo courseRepo;

    private static CourseController courseController;

    private CourseController() {
        courseRepo = new CourseRepo();
    }

    public static CourseController getInstance() {
        if (courseController == null)
            courseController = new CourseController();
        return courseController;
    }

    public List<Course> getCourses() {
        return courseRepo.getCourses();
    }

    public Course getCourse(Integer id) {
        return courseRepo.getCourse(id);
    }

    public Integer addCourse(Course course) throws Exception {
        if (validateCreatingCourse(course))
            return courseRepo.addCourse(course);
        throw new Exception("Course date not valid: " + course.getStartDate());
    }

    public void addCourses(String text) throws Exception {
        ArrayList<Course> courses = new ArrayList<>();
        String[] parts = text.split(System.lineSeparator() + System.lineSeparator());
        for (String courseText : parts) {
            String[] courseSpecifications = courseText.split(System.lineSeparator());
            if (validateCourseText(courseSpecifications)) {
                Course.CourseBuilder courseBuilder = Course.builder();

                courseBuilder.title(getValueCourseSpecification(courseSpecifications, 0));
                courseBuilder.code(getValueCourseSpecification(courseSpecifications, 1));
                courseBuilder.days(new Integer(getValueCourseSpecification(courseSpecifications, 2).substring(0, 1)));
                courseBuilder.startDate(LocalDate.parse(getValueCourseSpecification(courseSpecifications, 3), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                courses.add(courseBuilder.build());
            }
        }
        for (Course course : courses) {
            if (validateCreatingCourse(course))
                addCourse(course);
        }
    }

    private String getValueCourseSpecification(String[] courseSpecifications, int i) {
        return courseSpecifications[i].substring(courseSpecifications[i].indexOf(": ") + 2);
    }

    private boolean validateCourseText(String[] courseSpecifications) throws Exception {
        if (courseSpecifications.length != 4)
            throw new Exception("Course must have 4 lines");
        if (!courseSpecifications[0].contains("Titel: "))
            throw new Exception("Course doesn't have 'Titel' field as first line");
        if (!courseSpecifications[1].contains("Cursuscode: "))
            throw new Exception("Course doesn't have 'Cursuscode' field as second line");
        if (!courseSpecifications[2].contains("Duur: "))
            throw new Exception("Course doesn't have 'Duur' field as third line");
        if (!courseSpecifications[2].contains(" dagen"))
            throw new Exception("'Duur' field is missing value 'dagen'");
        if (!courseSpecifications[3].contains("Startdatum: "))
            throw new Exception("Course doesn't have 'Startdatum' field as fourth line");
        if (!getValueCourseSpecification(courseSpecifications, 3).matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
            throw new Exception("'Startdatum isn't at the correct format'");
        return true;
    }

    private Boolean validateCreatingCourse(Course course) {
        List<Course> courseList = getCourses()
                .stream()
                .filter(Course -> Course.getCode().equals(course.getCode()))
                .filter(Course -> (Course.getStartDate().minusDays(1).isBefore(course.getStartDate()) &&
                        Course.getStartDate().plusDays(Course.getDays() + 1).isAfter(course.getStartDate())) ||
                        (Course.getStartDate().plusDays(1).isAfter(course.getStartDate()) &&
                                Course.getStartDate().isBefore(course.getStartDate().plusDays(course.getDays() + 1))))
                .collect(Collectors.toList());
        return courseList.size() == 0;
    }
}
