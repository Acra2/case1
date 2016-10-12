package app;

import controllers.StudentController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Sander on 10-10-2016.
 */
@AllArgsConstructor
@Getter
@Setter
public class Subscription {
    private Course course;
    private Student student;
}
