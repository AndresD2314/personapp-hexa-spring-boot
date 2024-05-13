package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.model.request.TelefonoRequest;
import co.edu.javeriana.as.personapp.model.response.TelefonoResponse;

@Mapper
public class TelefonoMapperRest {

    public TelefonoResponse fromDomainToAdapterRest(TelefonoResponse telefono) {
        return fromDomainToAdapterRest(telefono, "MariaDB");
    }

    public TelefonoResponse fromDomainToAdapterRestMongo(TelefonoResponse telefono) {
        return fromDomainToAdapterRest(telefono, "MongoDB");
    }

    public TelefonoResponse fromDomainToAdapterRest(TelefonoResponse telefono, String database) {
        return new TelefonoResponse(
                telefono.getNumber(),
                telefono.getCompany(),
                telefono.getOwnerId(),
                database,
                "OK");
    }

    public Phone fromAdapterToDomain(TelefonoRequest request, Person person) {
        return new Phone(request.getNumber(), request.getCompany(), person);
    }
}
