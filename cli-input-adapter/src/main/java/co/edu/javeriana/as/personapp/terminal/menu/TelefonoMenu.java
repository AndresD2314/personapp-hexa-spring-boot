package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelefonoMenu {

    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR_TELEFONO = 2;
    private static final int OPCION_ELIMINAR_TELEFONO = 3;
    private static final int OPCION_EDITAR_TELEFONO = 4;

    public void iniciarMenu(TelefonoInputAdapterCli phoneInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuMotorPersistencia();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case PERSISTENCIA_MARIADB:
                        phoneInputAdapterCli.setPhoneOutputPortInjection("MARIA");
                        menuOpciones(phoneInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        phoneInputAdapterCli.setPhoneOutputPortInjection("MONGO");
                        menuOpciones(phoneInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(TelefonoInputAdapterCli phoneInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuOpciones();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case OPCION_VER_TODO:
                        phoneInputAdapterCli.listPhones();
                        break;
                    case OPCION_CREAR_TELEFONO:
                        crearTelefono(phoneInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR_TELEFONO:
                        eliminarTelefono(phoneInputAdapterCli, keyboard);
                        break;
                    case OPCION_EDITAR_TELEFONO:
                        editarTelefono(phoneInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException | NoExistException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_TODO + " para ver todos los teléfonos");
        System.out.println(OPCION_CREAR_TELEFONO + " para crear un nuevo teléfono");
        System.out.println(OPCION_ELIMINAR_TELEFONO + " para eliminar un teléfono");
        System.out.println(OPCION_EDITAR_TELEFONO + " para editar un teléfono");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuMotorPersistencia() {
        System.out.println("----------------------");
        System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
        System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
        System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
    }

    private int leerOpcion(Scanner keyboard) {
        try {
            System.out.print("Ingrese una opción: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            keyboard.next(); // Clear buffer
            log.warn("Solo se permiten números.");
            return leerOpcion(keyboard);
        }
    }

    public void crearTelefono(TelefonoInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        System.out.print("Ingrese una opción: ");
        int option = keyboard.nextInt();  // User inputs the integer and hits enter
        keyboard.nextLine();  // Consume the leftover newline
    
        if (option == 2) {  // Assuming '2' is the option to create a new phone
            System.out.print("Ingrese el número del teléfono: ");
            String number = keyboard.nextLine();  // Now this should work properly
    
            System.out.print("Ingrese el operador del teléfono: ");
            String operator = keyboard.nextLine();
    
            System.out.print("Ingrese el ID del dueño del teléfono: ");
            int ownerId = keyboard.nextInt();
            keyboard.nextLine();  // Consume the newline after reading an int
    
            // Proceed with creating the phone using the input details
            // Check the results and respond appropriately
            boolean created = phoneInputAdapterCli.createPhone(number, operator, ownerId);
            if (created) {
                System.out.println("SE CREÓ EL NUEVO TELÉFONO DE MANERA CORRECTA!");
            } else {
                System.out.println("NO SE PUDO CREAR EL TELÉFONO");
            }
        }
    }
    
    

    private void eliminarTelefono(TelefonoInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        log.info("Eliminación de un teléfono");
        System.out.print("Ingrese el número del teléfono que desea eliminar: ");
        String phoneNumber = keyboard.next();

        if (phoneInputAdapterCli.drop(phoneNumber))
            System.out.println("SE ELIMINÓ DE MANERA CORRECTA EL TELÉFONO!");
        else
            throw new NoExistException("El teléfono con el número proporcionado no existe en la base de datos, no se pudo eliminar");
    }

    private void editarTelefono(TelefonoInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        log.info("Edición de un teléfono");
        System.out.print("Ingrese el número del teléfono que desea editar: ");
        String phoneNumber = keyboard.next();
        keyboard.nextLine(); // Clear buffer
        System.out.print("Ingrese el nuevo operador del teléfono: ");
        String newCompany = keyboard.nextLine();

        Phone phone = new Phone();
        phone.setNumber(phoneNumber);
        phone.setCompany(newCompany);
        // Preserving existing owner ID for simplicity
        phone.setOwner(new Person(phoneInputAdapterCli.findPhoneOwner(phoneNumber), null, null, null, null, null, null));

        if (phoneInputAdapterCli.edit(phoneNumber, phone))
            System.out.println("Se pudo editar el teléfono");
        else
            System.out.println("No se pudo editar el teléfono");
    }
}
