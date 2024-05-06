package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import lombok.NonNull;

@Mapper
public class TelefonoMapperMaria {

    @Autowired
    private PersonaMapperMaria personaMapperMaria;

    public TelefonoEntity fromDomainToAdapter(Phone phone, boolean loadOwner) {
        TelefonoEntity telefonoEntity = new TelefonoEntity();
        telefonoEntity.setNum(phone.getNumber());
        telefonoEntity.setOper(phone.getCompany());
        if (loadOwner) {
            telefonoEntity.setDuenio(personaMapperMaria.fromDomainToAdapter(phone.getOwner(), true));
        }
        return telefonoEntity;
    }

    public Phone fromAdapterToDomain(TelefonoEntity telefonoEntity, boolean loadOwner) {
        Phone phone = new Phone();
        phone.setNumber(telefonoEntity.getNum());
        phone.setCompany(telefonoEntity.getOper());
        if (loadOwner) {
            phone.setOwner(personaMapperMaria.fromAdapterToDomain(telefonoEntity.getDuenio(), true));
        }
        return phone;
    }

	public List<TelefonoEntity> validateTelefonos(List<Phone> phones) {
        return phones.stream()
                     .map(phone -> this.fromDomainToAdapter(phone, false))
                     .collect(Collectors.toList());
    }

	public List<Phone> validatePhones(List<TelefonoEntity> telefonoEntities) {
    if (telefonoEntities == null) return new ArrayList<>();
    return telefonoEntities.stream()
                           .map(telefonoEntity -> this.fromAdapterToDomain(telefonoEntity, false))
                           .collect(Collectors.toList());
}


    private PersonaEntity validateDuenio(@NonNull Person owner, boolean loadDeep) {
        return owner != null ? personaMapperMaria.fromDomainToAdapter(owner, loadDeep) : new PersonaEntity();
    }

    private @NonNull Person validateOwner(PersonaEntity duenio, boolean loadDeep) {
        return duenio != null ? personaMapperMaria.fromAdapterToDomain(duenio, loadDeep) : new Person();
    }
}
