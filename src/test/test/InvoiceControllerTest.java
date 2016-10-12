import app.*;
import controllers.CourseController;
import controllers.InvoiceController;
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

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by Sander on 11-10-2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class InvoiceControllerTest {

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

    @InjectMocks
    InvoiceController invoiceController = InvoiceController.getInstance();

    @Test
    public void invoiceCheckPrivateStudentsList(){

        when(courseRepo.getCourse(1)).thenReturn(TestBuilder.course().startDate(LocalDate.now()).build());
        when(courseRepo.getCourse(2)).thenReturn(TestBuilder.course().startDate(LocalDate.now().plusDays(7)).build());
        when(studentRepo.getStudent(1)).thenReturn(new SingleStudent(1,"","","",0));
        when(studentRepo.getStudent(2)).thenReturn(new SingleStudent(2,"","","",0));
        when(studentRepo.getStudent(3)).thenReturn(new SingleStudent(3,"","","",0));
        when(subscribeRepo.getAllSubscriptions()).thenReturn(TestBuilder.subscriptionIds());

        Invoice invoice = invoiceController.getInvoice(getWeekNr(LocalDate.now()), LocalDate.now().getYear());
        assertThat(invoice.getStudents().size(), is(2));
        invoice = invoiceController.getInvoice(getWeekNr(LocalDate.now())+1, LocalDate.now().getYear());
        assertThat(invoice.getStudents().size(), is(2));
    }

    @Test
    public void invoiceOneStudentkOneBusinessSubscriptionsList(){

        when(courseRepo.getCourse(1)).thenReturn(TestBuilder.course().startDate(LocalDate.now()).build());
        when(courseRepo.getCourse(2)).thenReturn(TestBuilder.course().startDate(LocalDate.now().plusDays(7)).build());
        when(studentRepo.getStudent(1)).thenReturn(new SingleStudent(1,"privateStudent","","",0));
        when(studentRepo.getStudent(2)).thenReturn(new SingleStudent(2,"businessStudent","","",4));
        when(studentRepo.getStudent(3)).thenReturn(new SingleStudent(3,"businessStudent","","",4));
        when(studentRepo.getStudent(4)).thenReturn(new Business(4,"business","",""));
        when(subscribeRepo.getAllSubscriptions()).thenReturn(TestBuilder.subscriptionIds());

        Invoice invoice = invoiceController.getInvoice(getWeekNr(LocalDate.now()), LocalDate.now().getYear());
        assertThat(invoice.getStudents().size(), is(2));
        assertThat(invoice.getStudents().get(0).getId() ,is(1));
        assertThat(invoice.getStudents().get(1).getId() ,is(4));
        assertThat(invoice.getStudents().get(1).getCourseList().size(), is(1));
        assertThat(invoice.getStudents().get(1).getCourseList().get(0).getId(), is(1));
    }

    @Test
    public void invoiceTwoBusiness(){

        when(courseRepo.getCourse(1)).thenReturn(TestBuilder.course().startDate(LocalDate.now()).build());
        when(courseRepo.getCourse(2)).thenReturn(TestBuilder.course().startDate(LocalDate.now().plusDays(7)).build());
        when(studentRepo.getStudent(1)).thenReturn(new SingleStudent(1,"businessStudent1-1","","",3));
        when(studentRepo.getStudent(2)).thenReturn(new SingleStudent(2,"businessStudent1-2","","",3));
        when(studentRepo.getStudent(3)).thenReturn(new Business(3,"business1","",""));
        when(studentRepo.getStudent(4)).thenReturn(new SingleStudent(4,"businessStudent2-1","","",6));
        when(studentRepo.getStudent(5)).thenReturn(new SingleStudent(5,"businessStudent2-2","","",6));
        when(studentRepo.getStudent(6)).thenReturn(new Business(6,"business2","",""));
        when(subscribeRepo.getAllSubscriptions()).thenReturn(TestBuilder.subscriptionIdsMultipleBusiness());

        Invoice invoice = invoiceController.getInvoice(getWeekNr(LocalDate.now()), LocalDate.now().getYear());
        assertThat(invoice.getStudents().size(), is(2));
        assertThat(invoice.getStudents().get(0).getId() ,is(3));
        assertThat(invoice.getStudents().get(0).getCourseList().size(), is(2));
        assertThat(invoice.getStudents().get(0).getCourseList().get(0).getId(), is(1));
        assertThat(invoice.getStudents().get(0).getCourseList().get(1).getId(), is(1));
    }

    private Integer getWeekNr(LocalDate date){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }
}
