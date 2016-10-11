package controllers;

import repositories.*;

/**
 * Created by Sander on 11-10-2016.
 */
public class SubscribeController {

    private ISubscribeRepo subscribeRepo;
    private static SubscribeController subscribeController;
    private CourseController courseController = CourseController.getInstance();
    private StudentController studentController = StudentController.getInstance();

    private SubscribeController() {
        subscribeRepo = new SubscribeRepo();
    }

    public static SubscribeController getInstance() {
        if (subscribeController == null)
            subscribeController = new SubscribeController();
        return subscribeController;
    }

    public void subscribe(Integer courseId, Integer studentId) throws Exception {
        if (courseController.getCourse(courseId) == null)
            throw new Exception("Course doesn't exist");
        if (studentController.getStudent(studentId) == null)
            throw new Exception("Student doesn't exist");
        subscribeRepo.subscribe(courseId, studentId);
    }
}
