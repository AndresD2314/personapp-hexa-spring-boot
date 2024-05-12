package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PhoneUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.mapper.TelefonoMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
@Component
public class TelefonoInputAdapterCli {

    @Autowired
    @Qualifier("phoneOutputAdapterMaria")
    private PhoneOutputPort phoneOutputPortMaria;

    @Autowired
    @Qualifier("phoneOutputAdapterMongo")
    private PhoneOutputPort phoneOutputPortMongo;

    private final PhoneOutputPort phoneOutputPort;
    @Autowired
    public TelefonoInputAdapterCli(@Qualifier("phoneOutputAdapterMaria") PhoneOutputPort phoneOutputPort) {
        this.phoneOutputPort = phoneOutputPort;
    }
    @Autowired
    private TelefonoMapperCli telefonoMapperCli;
    @Autowired
    private PersonInputPort personInputPort;


    PhoneInputPort phoneInputPort;

    public void setPhoneOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void listPhones() {
        log.info("Listing all phones in Input Adapter");
        List<TelefonoModelCli> phones = phoneInputPort.findAll().stream()
                .map(telefonoMapperCli::fromDomainToAdapterCli)
                .collect(Collectors.toList());
        phones.forEach(p -> System.out.println(p.toString()));
    }

    public boolean create(Phone phone) {
        log.info("Creating a new phone record");
        Phone newPhone = phoneInputPort.create(phone);

        return newPhone != null;
    }

    public boolean edit(String phoneNumber, Phone phone) throws NoExistException {
        log.info("Editing a phone record");
        Phone editedPhone = phoneInputPort.edit(phoneNumber, phone);

        return editedPhone != null;
    }

    public boolean drop(String phoneNumber) throws NoExistException {
        log.info("Deleting a phone record");
        return phoneInputPort.drop(phoneNumber);
    }

    public Integer findPhoneOwner(String phoneNumber) throws NoExistException {
        Phone phone = phoneInputPort.findOne(phoneNumber);
        if (phone != null && phone.getOwner() != null) {
            return phone.getOwner().getIdentification();
        }
        throw new NoExistException("Phone not found or owner information is unavailable.");
    }

    public Person findPersonById(int id) {
    try {
        return personInputPort.findOne(id);
    } catch (NoExistException e) {
        log.error("No person found with id: " + id, e);
        return null;
    }
}

public boolean createPhone(String number, String operator, Integer ownerId) throws NoExistException {
    Person owner = personInputPort.findOne(ownerId);
    if (owner == null) {
        log.error("Owner with ID " + ownerId + " not found.");
        return false;
    }

    Phone phone = new Phone();
    phone.setNumber(number);
    phone.setCompany(operator);
    phone.setOwner(owner);

    phoneOutputPort.save(phone); // Assuming save method in the port takes care of converting and saving `TelefonoEntity`
    return true;
}


    
}
