package app;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Sander on 10-10-2016.
 */
@Getter
@Setter
public abstract class Student {
    protected Integer id;
    protected String name;
    private String bankAccountNr;
    private String address;
    private Integer businessId;
    private List<Course> courseList;

    public Student(Integer id, String name, String address, String bankAccountNr, Integer businessID) {
        setId(id);
        setName(name);
        setAddress(address);
        setBankAccountNr(bankAccountNr);
        setBusinessId(businessID);
    }
}
