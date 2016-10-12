package app;

import lombok.Builder;

/**
 * Created by Sander on 10-10-2016.
 */
public class SingleStudent extends Student {

    public SingleStudent(Integer id, String name,String address, String bankAccountNr, Integer businessID){
        super(id,name,address, bankAccountNr, businessID);
    }
}
