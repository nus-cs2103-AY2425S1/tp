package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.TypicalPersons;

public class DeleteAppointmentCommandTest {
    private Model model;
    private List<Person> personList;

    @BeforeEach
    public void setUp() throws IOException {
        // Set up the storage manager
        Path tempFolder = Paths.get(System.getProperty("java.io.tmpdir"));
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(tempFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(tempFolder.resolve("userPrefs.json"));
        StorageManager storage =
                new StorageManager(addressBookStorage, userPrefsStorage);

        // Initialize the model with typical data
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs(), storage);

        Appointment testAppointmentOne = new Appointment("01-01-2024 12:30");

        Person newPerson = new Person(ALICE.getName(),
                ALICE.getAge(),
                ALICE.getGender(),
                ALICE.getNric(),
                ALICE.getPhone(),
                ALICE.getEmail(),
                ALICE.getAddress(),
                testAppointmentOne,
                ALICE.getTags());
        model.setPerson(ALICE, newPerson);



        // Initialize the personList for further tests
        personList = model.getFilteredPersonList(); // Retrieve the list of persons
    }

    @Test
    public void execute_deleteAppointmentByNric_success() throws CommandException {
        // Arrange
        Nric targetNric = new Nric("s1234567z");
        Appointment appointmentToDelete = new Appointment("01-01-2024 12:30");
        DeleteAppointmentCommand deleteCommand = new DeleteAppointmentCommand(targetNric, appointmentToDelete);

        // Act
        CommandResult result = deleteCommand.execute(model);

        // Assert
        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS, result.getFeedbackToUser());
        assertFalse(personList.get(0).getAppointment().equals(appointmentToDelete)); // Ensure appointment is deleted
    }

    @Test
    public void execute_deleteAppointmentByIndex_success() throws CommandException {

        Appointment appointmentToDelete = new Appointment("01-01-2024 12:30");
        DeleteAppointmentCommand deleteCommand = new DeleteAppointmentCommand(INDEX_FIRST_PERSON, appointmentToDelete);

        CommandResult result = deleteCommand.execute(model);

        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS, result.getFeedbackToUser());
        assertFalse(personList.get(0).getAppointment().equals(appointmentToDelete));
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        // Arrange
        Nric targetNric = new Nric("S0000000A");
        Appointment appointmentToDelete = new Appointment("01-01-2024 12:30");
        DeleteAppointmentCommand deleteCommand = new DeleteAppointmentCommand(targetNric, appointmentToDelete);

        // Act & Assert
        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_appointmentNotFound_throwsCommandException() {
        // Arrange
        Nric targetNric = new Nric("s1234567z");
        Appointment nonExistentAppointment = new Appointment("03-01-2024 12:30");
        DeleteAppointmentCommand deleteCommand = new DeleteAppointmentCommand(targetNric, nonExistentAppointment);

        // Act & Assert
        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }
}
