import app.Course;

import java.time.LocalDate;

/**
 * Created by Sander on 10-10-2016.
 */
public class TestBuilder {

    public static Course.CourseBuilder course(){
        return Course.builder().title("title").id(1).code("code").startDate(LocalDate.now()).days(2);
    }
}
