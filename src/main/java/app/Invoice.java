package app;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Sander on 11-10-2016.
 */
@Getter
public class Invoice {


    private Integer weekNr;
    @Setter
    private List<Student> students;

    public Invoice(Integer weekNr){
        this.weekNr = weekNr;
    }

}
