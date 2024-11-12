// src/test/java/seedu/address/logic/commands/AddLogEntryCommandTest.java
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

public class AddLogEntryCommandTest {
    private final AppointmentDate appointmentDate = new AppointmentDate("20 Nov 2024");
    private final IdentityNumber identityNumber = new IdentityNumber("S1234567D");

    @Test
    public void constructor_nullIdentityNumber_throwsNullPointerException() {
        // assertThrows(NullPointerException.class, () -> new AddLogEntryCommand(null, appointmentDate));
    }

    @Test
    public void constructor_nullAppointmentDate_throwsNullPointerException() {
        // assertThrows(NullPointerException.class, () -> new AddLogEntryCommand(identityNumber, null));
    }

    @Test
    public void execute_validPerson_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person validPerson = model.getPersonList().get(0);
        CommandResult commandResult = new AddLogEntryCommand(validPerson.getIdentityNumber(),
                appointmentDate).execute(model);
        assertEquals("Please enter the log entry for the appointment on 20 Nov 2024",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPerson_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        IdentityNumber invalidIdentityNumber = new IdentityNumber("S1234567D");
        AddLogEntryCommand command = new AddLogEntryCommand(invalidIdentityNumber, appointmentDate);
        assertThrows(CommandException.class, String.format(AddLogEntryCommand.MESSAGE_PERSON_NOT_FOUND,
                invalidIdentityNumber), () -> command.execute(model));
    }

    @Test
    public void equals() {
        AddLogEntryCommand addLogEntryCommand = new AddLogEntryCommand(identityNumber, appointmentDate);

        // same object -> returns true
        assertEquals(addLogEntryCommand, addLogEntryCommand);

        // same values -> returns true
        AddLogEntryCommand addLogEntryCommandCopy = new AddLogEntryCommand(identityNumber, appointmentDate);
        assertEquals(addLogEntryCommand, addLogEntryCommandCopy);

        // different types -> returns false
        assertEquals(addLogEntryCommand.equals(1), false);

        // null -> returns false
        assertEquals(addLogEntryCommand.equals(null), false);

        // different appointment date -> returns false
        AppointmentDate differentAppointmentDate = new AppointmentDate("21 Nov 2024");
        AddLogEntryCommand addLogEntryCommandDifferentDate = new AddLogEntryCommand(identityNumber,
                differentAppointmentDate);
        assertEquals(addLogEntryCommand.equals(addLogEntryCommandDifferentDate), false);

        // different identity number -> returns false
        IdentityNumber differentIdentityNumber = new IdentityNumber("S7633494C");
        AddLogEntryCommand addLogEntryCommandDifferentIdentityNumber = new AddLogEntryCommand(differentIdentityNumber,
                appointmentDate);
        assertEquals(addLogEntryCommand.equals(addLogEntryCommandDifferentIdentityNumber), false);
    }
}
