package controllers;

import app.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Sander on 11-10-2016.
 */
public class InvoiceController {

    private static InvoiceController invoiceController;

    private InvoiceController() {
    }

    private CourseController courseController = CourseController.getInstance();
    private StudentController studentController = StudentController.getInstance();
    private SubscribeController subscribeController = SubscribeController.getInstance();

    public static InvoiceController getInstance() {
        if (invoiceController == null)
            invoiceController = new InvoiceController();
        return invoiceController;
    }

    public Invoice getInvoice(Integer weekNr, Integer year) {
        Invoice invoice = new Invoice(weekNr);
        List<Subscription> subscriptions = subscribeController.getSubscriptions();

        List<Subscription> AllSubscriptions = subscriptions
                .stream()
                .filter(Subscription -> weekNr.equals(getWeekNr(Subscription.getCourse().getStartDate())))
                .filter(Subscription -> year.equals(Subscription.getCourse().getStartDate().getYear()))
                .collect(Collectors.toList());

        List<Student> students = AllSubscriptions
                .stream()
                .map(Subscription -> Subscription.getStudent())
                .filter(Student -> (Student instanceof SingleStudent &&
                        Student.getBusinessId().equals(0)))
                .collect(Collectors.toList());

        List<Student> businesses =AllSubscriptions
                .stream()
                .map(Subscription -> Subscription.getStudent())
                .filter(Student -> (Student instanceof SingleStudent &&
                        !Student.getBusinessId().equals(0)))
                .map(Subscription -> studentController.getStudent(Subscription.getBusinessId()))
                .filter(distinctByKey(Student -> Student.getId()))
                .collect(Collectors.toList());

        students.addAll(businesses);
        for (Student student : students) {
//            if (student instanceof SingleStudent && student.getBusinessId() == null) {
                student.setCourseList(AllSubscriptions
                        .stream()
                        .filter(Subscription -> Subscription.getStudent().getId().equals(student.getId())||
                                Subscription.getStudent().getBusinessId().equals(student.getId()))
                        .map(Subscription -> Subscription.getCourse())
                        .collect(Collectors.toList()));
//            } else {
//                student.setCourseList(AllSubscriptions
//                       .stream()
//                        .filter(Subscription -> Subscription.getStudent().getBusinessId()!= null)
//                        .filter(Subscription -> Subscription.getStudent().getBusinessId().equals(student.getId()))
//                        .map(Subscription -> Subscription.getCourse())
//                        .collect(Collectors.toList()));
//            }
        }

        invoice.setStudents(students.stream()
                .filter(Student-> Student.getBusinessId().equals(0))
                .filter(Student ->Student.getCourseList().size()>0)
                .collect(Collectors.toList()));
        return invoice;
    }

    private Integer getWeekNr(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weeknr = date.get(weekFields.weekOfWeekBasedYear());
        return weeknr;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
