import app.Course;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Sander on 10-10-2016.
 */
public class TestBuilder {



    public static Course.CourseBuilder course(){
        return Course.builder().title("title").id(1).code("123").startDate(LocalDate.now()).days(2);
    }

    public static ArrayList<ArrayList<Integer>> subscriptionIds(){
        ArrayList<ArrayList<Integer>> subscriptionIds = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> list1 =  new ArrayList<>();
        list1.add(1);
        list1.add(1);
        subscriptionIds.add(list1);
        ArrayList<Integer> list2 =  new ArrayList<>();
        list2.add(2);
        list2.add(1);
        subscriptionIds.add(list2);
        ArrayList<Integer> list3 =  new ArrayList<>();
        list3.add(1);
        list3.add(2);
        subscriptionIds.add(list3);
        ArrayList<Integer> list4 =  new ArrayList<>();
        list4.add(2);
        list4.add(2);
        subscriptionIds.add(list4);
        return subscriptionIds;
    }
    public static ArrayList<ArrayList<Integer>>  subscriptionIdsMultipleBusiness(){
        ArrayList<ArrayList<Integer>> subscriptionIds = subscriptionIds();
        ArrayList<Integer> list1 =  new ArrayList<>();
        list1.add(1);
        list1.add(4);
        subscriptionIds.add(list1);
        ArrayList<Integer> list2 =  new ArrayList<>();
        list2.add(2);
        list2.add(4);
        subscriptionIds.add(list2);
        ArrayList<Integer> list3 =  new ArrayList<>();
        list3.add(1);
        list3.add(5);
        subscriptionIds.add(list3);
        ArrayList<Integer> list4 =  new ArrayList<>();
        list4.add(2);
        list4.add(5);
        subscriptionIds.add(list4);
        return subscriptionIds;
    }
}
