package controllers;

import app.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        List<Subscription> weekSubscriptions = subscribeController.getSubscriptionsByWeek(weekNr, year);

        List<Student> students = weekSubscriptions
                .stream()
                .map(Subscription -> Subscription.getStudent())
                .filter(Student -> (Student instanceof SingleStudent &&
                        Student.getBusinessId().equals(0)))
                .filter(distinctByKey(Student -> Student.getId()))
                .collect(Collectors.toList());

        List<Student> businesses = weekSubscriptions
                .stream()
                .map(Subscription -> Subscription.getStudent())
                .filter(Student -> (Student instanceof SingleStudent &&
                        !Student.getBusinessId().equals(0)))
                .map(Subscription -> studentController.getStudent(Subscription.getBusinessId()))
                .filter(distinctByKey(Student -> Student.getId()))
                .collect(Collectors.toList());

        students.addAll(businesses);
        for (Student student : students) {
            student.setCourseList(weekSubscriptions
                    .stream()
                    .filter(Subscription -> Subscription.getStudent().getId().equals(student.getId()) ||
                            Subscription.getStudent().getBusinessId().equals(student.getId()))
                    .map(Subscription -> Subscription.getCourse())
                    .collect(Collectors.toList()));
        }

        invoice.setStudents(students.stream()
                .filter(Student -> Student.getBusinessId().equals(0))
                .filter(Student -> Student.getCourseList().size() > 0)
                .collect(Collectors.toList()));
        return invoice;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
