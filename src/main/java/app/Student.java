package app;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Sander on 10-10-2016.
 */
@Getter
@Setter
public abstract class Student {
    protected int id;
    protected String name;
    private String bankAccountNr;
}
