import app.Course;
import controllers.CourseController;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import repositories.CourseRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sander on 10-10-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CourseCountrollerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    CourseRepo courseRepo = new CourseRepo();

    @InjectMocks
    CourseController courseController = CourseController.getInstance();

    @Test
    public void emptyCourseList() {
        courseController.getCourses();
        assertThat(courseController.getCourses().size(), is(0));
    }

    @Test
    public void addToEmptyCourseList() throws Exception {
        courseController.getCourses();
        assertThat(courseController.getCourses().size(), is(0));
        courseController.addCourse(TestBuilder.course().build());
        verify(courseRepo).addCourse(any());
    }

    @Test
    public void addToCourseListWithCourseSameDate() throws Exception {
        thrown.expect(java.lang.Exception.class);

        List<Course> courseList = new ArrayList<>();
        when(courseRepo.getCourses()).thenReturn(courseList);

        assertThat(courseController.getCourses().size(), is(0));
        courseList.add(TestBuilder.course().build());
        assertThat(courseController.getCourses().size(), is(1));
        courseController.addCourse(TestBuilder.course().startDate(LocalDate.now().plusDays(1)).build());
        assertThat(courseController.getCourses().size(), is(1));
    }

    @Test
    public void addToCourseListWithCourseBeforeDate() throws Exception {
        thrown.expect(java.lang.Exception.class);

        List<Course> courseList = new ArrayList<>();
        when(courseRepo.getCourses()).thenReturn(courseList);

        assertThat(courseController.getCourses().size(), is(0));
        courseList.add(TestBuilder.course().build());
        assertThat(courseController.getCourses().size(), is(1));
        courseController.addCourse(TestBuilder.course().startDate(LocalDate.now().minusDays(1)).build());
        assertThat(courseController.getCourses().size(), is(1));
    }

    @Test
    public void addToCourseListWithCourseAfterDate() throws Exception {
        thrown.expect(java.lang.Exception.class);

        List<Course> courseList = new ArrayList<>();
        when(courseRepo.getCourses()).thenReturn(courseList);

        assertThat(courseController.getCourses().size(), is(0));
        courseList.add(TestBuilder.course().build());
        assertThat(courseController.getCourses().size(), is(1));
        courseController.addCourse(TestBuilder.course().build());
        assertThat(courseController.getCourses().size(), is(1));
    }

    @Test
    public void inportOneCourseCorrect() throws Exception {
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 14/10/2013";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }

    @Test
    public void inportMultipleCourseCorrect() throws Exception {
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 14/10/2013\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n" +
                "Titel: Advanced C#\r\n" +
                "Cursuscode: ADCSB\r\n" +
                "Duur: 2 dagen\r\n" +
                "Startdatum: 21/10/2013";
        courseController.addCourses(text);
        verify(courseRepo, times(3)).addCourse(any());
    }

    @Test
    public void inportCoursesCodeWrongSequence() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course doesn't have 'Cursuscode' field as second line");
        String text = "Titel: C# Programmeren\r\n" +
                "Duur: 5 dagen\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Startdatum: 14/10/2013\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }

    @Test
    public void inportCoursesTitelWrongSequence() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course doesn't have 'Titel' field as first line");
        String text = "Cursuscode: CNETIN\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 14/10/2013\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }

    @Test
    public void inportCoursesDaysWrongSequence() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course doesn't have 'Duur' field as third line");
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Startdatum: 14/10/2013\r\n" +
                "Duur: 5 dagen\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }

    @Test
    public void inportCoursesStartdateWrongName() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course doesn't have 'Startdatum' field as fourth line");
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Start: 14/10/2013\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }
    @Test
    public void inportCoursesMissingField() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course must have 4 lines");
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Startdatum: 14/10/2013\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }

    @Test
    public void inportCoursesWrongDaysSyntax() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("'Duur' field is missing value 'dagen'");
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5\r\n" +
                "Startdatum: 14/10/2013\r\n\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013\r\n\r\n";
        courseController.addCourses(text);
        verify(courseRepo, times(1)).addCourse(any());
    }

    @Test
    public void inportCoursesMissingWhiteLine() throws Exception {
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Course must have 4 lines");
        String text = "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 14/10/2013\r\n" +
                "Titel: C# Programmeren\r\n" +
                "Cursuscode: CNETIN\r\n" +
                "Duur: 5 dagen\r\n" +
                "Startdatum: 21/10/2013";
        courseController.addCourses(text);
        verify(courseRepo, times(0)).addCourse(any());
    }
}
