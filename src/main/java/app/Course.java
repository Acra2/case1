package app;

import lombok.*;

import java.time.LocalDate;

/**
 * Created by Sander on 10-10-2016.
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Course {
    private Integer id;
    private String title;
    private String code;
    private LocalDate startDate;
    private int days;
}
