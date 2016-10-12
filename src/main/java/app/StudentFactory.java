package app;

/**
 * Created by Sander on 10-10-2016.
 */
public class StudentFactory {
    public Student createStudent(Integer id, String name,String address, String bankAccountNr, Integer businessID, Boolean business){
        if(business){
            return new Business(id, name,address, bankAccountNr);
        }else{
            return new SingleStudent(id, name,address, bankAccountNr, businessID);
        }
    }
}
