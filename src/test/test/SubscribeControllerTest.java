import app.Course;
import app.SingleStudent;
import app.Student;
import controllers.CourseController;
import controllers.StudentController;
import controllers.SubscribeController;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repositories.CourseRepo;
import repositories.StudentRepo;
import repositories.SubscribeRepo;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sander on 11-10-2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class SubscribeControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    SubscribeRepo subscribeRepo = new SubscribeRepo();

    @Mock
    CourseRepo courseRepo = new CourseRepo();

    @Mock
    StudentRepo studentRepo = new StudentRepo();

    @InjectMocks
    CourseController courseController = CourseController.getInstance();

    @InjectMocks
    SubscribeController subscribeController = SubscribeController.getInstance();

    @InjectMocks
    StudentController studentController = StudentController.getInstance();

    Integer courseId = 1;
    Course course = TestBuilder.course().id(courseId).build();
    Integer studentId = 2;
    Student student = new SingleStudent(studentId,"sander", "schijfstraat 26","235145",null);

    @Test
    public void subscribeSucces() throws Exception {
        when(courseRepo.getCourse(courseId)).thenReturn(course);
        when(studentRepo.getStudent(studentId)).thenReturn(student);

        subscribeController.subscribe(courseId, studentId);

        verify(subscribeRepo,times(1)).subscribe(courseId, studentId);
    }

    @Test
    public void subscribeWrongCourseID() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course doesn't exist");

        when(courseRepo.getCourse(courseId)).thenReturn(null);
        when(studentRepo.getStudent(studentId)).thenReturn(student);

        subscribeController.subscribe(courseId, studentId);

        verify(subscribeRepo,times(0)).subscribe(courseId, studentId);
    }

    @Test
    public void subscribeWrongStudentId() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Student doesn't exist");

        when(courseRepo.getCourse(courseId)).thenReturn(course);
        when(studentRepo.getStudent(studentId)).thenReturn(null);

        subscribeController.subscribe(courseId, studentId);

        verify(subscribeRepo,times(0)).subscribe(courseId,studentId);
    }
    @Test
    public void subscribeWrongStudentIdWrongCourseId() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course doesn't exist");

        when(courseRepo.getCourse(courseId)).thenReturn(null);
        when(studentRepo.getStudent(studentId)).thenReturn(null);

        subscribeController.subscribe(courseId, studentId);

        verify(subscribeRepo,times(0)).subscribe(courseId, studentId);
    }
}
