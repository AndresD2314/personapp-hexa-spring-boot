package co.edu.javeriana.as.personapp.terminal.mapper;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;

@Mapper
public class EstudioMapperCli {
    private static final Logger log = LogManager.getLogger(EstudioMapperCli.class);


    public EstudioModelCli fromDomainToAdapterCli(Study study) {
        EstudioModelCli studyModelCli = new EstudioModelCli();
        
        log.debug("Mapping Study: {}", study);
        //if (study.getPerson() == null) log.warn("Person is null in Study");
       // if (study.getProfession() == null) log.warn("Profession is null in Study");

       studyModelCli.setPerson(study.getPerson() != null ? study.getPerson().getIdentification() : null);
       studyModelCli.setProfession(study.getProfession() != null ? study.getProfession().getId() : null);
        studyModelCli.setGraduationDate(study.getGraduationDate() != null 
            ? study.getGraduationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) 
            : "N/A");
        
        studyModelCli.setUniversityName(study.getUniversityName() != null ? study.getUniversityName() : "N/A");
        return studyModelCli;
    }
}
