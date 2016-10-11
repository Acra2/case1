package app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Sander on 10-10-2016.
 */
public class Business extends Student {
    private List<Student> students;

    public Business(Integer id, String name,String address, String bankAccountNr){
        super(id,name,address, bankAccountNr, null);
    }
}
