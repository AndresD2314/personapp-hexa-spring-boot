package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;

@Mapper
public class TelefonoMapperCli {
    public TelefonoModelCli fromDomainToAdapterCli(Phone phone) {
        TelefonoModelCli telefonoModelCli = new TelefonoModelCli();
        telefonoModelCli.setNumber(phone.getNumber());
        telefonoModelCli.setCompany(phone.getCompany());
        // Handle null owner
        telefonoModelCli.setPerson(phone.getOwner() != null ? phone.getOwner().getIdentification() : null);
        return telefonoModelCli;
    }
}
