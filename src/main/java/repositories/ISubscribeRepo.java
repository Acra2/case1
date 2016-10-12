package repositories;

import java.util.ArrayList;

/**
 * Created by Sander on 11-10-2016.
 */
public interface ISubscribeRepo {

    void subscribe(Integer courseId, Integer studentId) throws Exception;
    ArrayList<ArrayList<Integer>> getAllSubscriptions();
    ArrayList<ArrayList<Integer>> getSubscriptionsByWeek(Integer week, Integer year);
}
