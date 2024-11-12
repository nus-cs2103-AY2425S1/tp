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
import seedu.address.model.log.Log;
import seedu.address.model.log.LogEntry;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

public class AddLogCommandTest {
    private AppointmentDate appointmentDate = new AppointmentDate("20 Oct 2024");
    private LogEntry logEntry = new LogEntry("First visit to the clinic");
    private Log log = new Log(appointmentDate, logEntry);
    private IdentityNumber identityNumber = new IdentityNumber("S0211145D");


    @Test
    public void constructor_nullIdentityNumber_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> new AddLogCommand(null, log));
    }

    @Test
    public void constructor_nullLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLogCommand(identityNumber, null));
    }

    @Test
    public void execute_logAcceptedByModel_addlogSuccessful() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person validPerson = model.getPersonList().get(0);
        CommandResult commandResult = new AddLogCommand(validPerson.getIdentityNumber(), log).execute(model);
        assertEquals(String.format(AddLogCommand.MESSAGE_ADD_LOG_SUCCESS, validPerson.getName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateLog_throwsCommandException() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person validPerson = model.getPersonList().get(0);
        AddLogCommand addLogCommand = new AddLogCommand(validPerson.getIdentityNumber(), log);
        model.addLog(validPerson, log);


        assertThrows(CommandException.class, AddLogCommand.MESSAGE_DUPLICATE_LOG, () -> addLogCommand.execute(model));
    }

    @Test
    public void equals() {
        AddLogCommand addLogCommand = new AddLogCommand(identityNumber, log);

        // same object -> returns true
        assertEquals(addLogCommand, addLogCommand);

        // same values -> returns true
        AddLogCommand addLogCommandCopy = new AddLogCommand(identityNumber, log);
        assertEquals(addLogCommand, addLogCommandCopy);

        // different types -> returns false
        assertEquals(addLogCommand.equals(1), false);

        // null -> returns false
        assertEquals(addLogCommand.equals(null), false);

        // different log -> returns false
        Log differentLog = new Log(new AppointmentDate("20 Oct 2024"), new LogEntry("Second visit to the clinic"));
        AddLogCommand addLogCommandDifferentLog = new AddLogCommand(identityNumber, differentLog);
        assertEquals(addLogCommand.equals(addLogCommandDifferentLog), false);

        // different identity number -> returns false
        IdentityNumber differentIdentityNumber = new IdentityNumber("S1234567D");
        AddLogCommand addLogCommandDifferentIdentityNumber = new AddLogCommand(differentIdentityNumber, log);
        assertEquals(addLogCommand.equals(addLogCommandDifferentIdentityNumber), false);
    }
}

