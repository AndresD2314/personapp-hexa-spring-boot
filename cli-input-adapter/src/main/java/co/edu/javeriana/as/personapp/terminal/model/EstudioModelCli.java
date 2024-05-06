package co.edu.javeriana.as.personapp.terminal.model;

import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profesion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioModelCli {
    private Integer person;
    private Integer profession;
    private String graduationDate;
    private String universityName;
}
