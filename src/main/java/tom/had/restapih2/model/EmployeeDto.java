package tom.had.restapih2.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmployeeDto {
    private String firstname;
    private String lastname;
    private boolean active;
    private LocalDate created;
}
