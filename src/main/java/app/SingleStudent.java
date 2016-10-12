package app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Created by Sander on 10-10-2016.
 */
@NoArgsConstructor
public class SingleStudent extends Student {

    public SingleStudent(Integer id, String name,String address, String bankAccountNr, Integer businessID){
        super(id,name,address, bankAccountNr, businessID);
    }
}
