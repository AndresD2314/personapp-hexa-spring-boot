package co.edu.javeriana.as.personapp.mariadb.adapter;

import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.TelefonoMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.TelefonoRepositoryMaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Adapter("phoneOutputAdapterMaria")
@Transactional
public class PhoneOutputAdapterMaria implements PhoneOutputPort {

    @Autowired
    private TelefonoRepositoryMaria telefonoRepositoryMaria;
    @Autowired
    private TelefonoMapperMaria telefonoMapperMaria;

    @Override
    public Phone save(Phone phone) {
        TelefonoEntity persistedTelefono = telefonoRepositoryMaria.save(telefonoMapperMaria.fromDomainToAdapter(phone, false));  // Assume we do not need to load owner
        return telefonoMapperMaria.fromAdapterToDomain(persistedTelefono, false);  // Avoiding deep loading of relationships
    }

    @Override
    public Boolean delete(String phoneNumber) {
        telefonoRepositoryMaria.deleteById(phoneNumber);
        return telefonoRepositoryMaria.findById(phoneNumber).isEmpty();
    }

    @Override
    public List<Phone> find() {
        return telefonoRepositoryMaria.findAll().stream()
                .map(telefonoEntity -> telefonoMapperMaria.fromAdapterToDomain(telefonoEntity, true))  // Control deep loading
                .collect(Collectors.toList());
    }

    @Override
    public Phone findById(String phoneNumber) {
        return telefonoRepositoryMaria.findById(phoneNumber)
                .map(telefonoEntity -> telefonoMapperMaria.fromAdapterToDomain(telefonoEntity, true))  // Control deep loading
                .orElse(null);
    }
}

