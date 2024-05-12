package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.StudyId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class StudyUseCase implements StudyInputPort {

    private StudyOutputPort studyPersistence;

    public StudyUseCase(@Qualifier("studyOutputAdapterMaria") StudyOutputPort studyPersistence) {
        this.studyPersistence = studyPersistence;
    }

    @Override
    public void setPersistence(StudyOutputPort studyPersistence) {
        this.studyPersistence = studyPersistence;
    }

    @Override
    public Study create(Study study) {
        log.debug("Creating study");
        return studyPersistence.save(study);
    }

   @Override
public Study edit(StudyId studyId, Study study) throws NoExistException {
    log.debug("Editing study with ID: {}", studyId);
    Study existingStudy = studyPersistence.findById(studyId);

    if (existingStudy == null) {
        log.warn("No study found with ID: {}, cannot proceed with editing", studyId);
        throw new NoExistException("Study does not exist, cannot be edited");
    }

    // Log the details of the study retrieved for debugging
    log.debug("Retrieved study details: {}", existingStudy);

    // Update the study details from the provided study object
    // Assume that the study object has all the necessary details provided
    // You may need to ensure that only specific fields are updated if not all fields are supposed to change
    if (study.getUniversityName() != null) {
        existingStudy.setUniversityName(study.getUniversityName());
    }
    if (study.getGraduationDate() != null) {
        existingStudy.setGraduationDate(study.getGraduationDate());
    }
    if (study.getPerson() != null) {
        existingStudy.setPerson(study.getPerson());
    }
    if (study.getProfession() != null) {
        existingStudy.setProfession(study.getProfession());
    }

    // Additional business logic can be handled here
    // For example, validating changes, applying business rules, etc.

    // Save the updated study
    Study updatedStudy = studyPersistence.save(existingStudy);
    log.debug("Study updated successfully with ID: {}", studyId);

    return updatedStudy;
}

    @Override
    public Boolean drop(StudyId studyId) throws NoExistException {
        Study oldStudy = studyPersistence.findById(studyId);
        if (oldStudy != null)
            return studyPersistence.delete(studyId);
        throw new NoExistException("The study with id " + studyId + " does not exist in db, cannot be dropped");
    }

    @Override
    public List<Study> findAll() {
        return studyPersistence.find();
    }

    public List<Study> findAllStudies() {
        List<Study> studies = studyPersistence.find();
        for (Study study : studies) {
            log.debug("Study loaded: {}, Person: {}, Profession: {}", study, study.getPerson(), study.getProfession());
        }
        return studies;
    }

    @Override
    public Study findOne(StudyId studyId) throws NoExistException {
        log.debug("Finding study with ID: {}", studyId);
        Study existingStudy = studyPersistence.findById(studyId);
        if (existingStudy != null) {
            return existingStudy;
        }
        throw new NoExistException("Study does not exist, cannot be found");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }

 

  
}
