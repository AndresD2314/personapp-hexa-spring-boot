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
        if (existingStudy != null) {
            // Aquí podemos asumir que el estudio a editar ya tiene las referencias correctas de persona y profesión.
            // Si es necesario, asegúrate de actualizar estos campos o manejar la lógica de negocio específica aquí.
            return studyPersistence.save(study);
        }
        throw new NoExistException("Study does not exist, cannot be edited");
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
