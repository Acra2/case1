package app;

import java.util.List;

/**
 * Created by Sander on 10-10-2016.
 */
public class Business extends Student {
    private List<Student> students;

    public Business(Integer id, String name,String address, String bankAccountNr){
        super(id,name,address, bankAccountNr, 0);
    }
}
