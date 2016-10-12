package repositories;

import app.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sander on 11-10-2016.
 */
public interface ISubscribeRepo {

    void subscribe(Integer courseId, Integer studentId) throws Exception;
    ArrayList<ArrayList<Integer>> getAllSubscriptions();
}
