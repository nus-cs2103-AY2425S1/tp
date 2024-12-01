package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Log;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

public class LogCommandTest {

    @Test
    public void execute_validNricAndLog_addsLogSuccessfully() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Nric validNric = new Nric("S1234567A");
        LogCommand logCommand = new LogCommand(validNric, validLog);

        CommandResult commandResult = logCommand.execute(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        Optional<Person> personWithMatchingNric = lastShownList.stream()
                .filter(person -> validNric.equals(person.getNric()))
                .findFirst();

        Person personWithLogAdded;
        if (personWithMatchingNric.isPresent()) {
            personWithLogAdded = personWithMatchingNric.get();
        } else {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_FOUND);
        }

        assertEquals("Log added to " + personWithLogAdded.getName(), commandResult.getFeedbackToUser());
        assertTrue(personWithLogAdded.getLogEntries().getLogs().contains(validLog));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Nric invalidNric = new Nric("A0000000A");
        LogCommand logCommand = new LogCommand(invalidNric, validLog);

        assertThrows(CommandException.class, () -> logCommand.execute(model));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Nric validNric = new Nric("S1234567A");
        LogCommand logCommand = new LogCommand(validNric, validLog);

        assertTrue(logCommand.equals(logCommand));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Nric validNric = new Nric("S1234567A");
        LogCommand logCommand = new LogCommand(validNric, validLog);

        assertFalse(logCommand.equals(null));
        assertFalse(logCommand.equals(new ClearCommand()));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Nric validNric = new Nric("S1234567A");
        LogCommand logCommand1 = new LogCommand(validNric, validLog);
        LogCommand logCommand2 = new LogCommand(validNric, validLog);

        assertTrue(logCommand1.equals(logCommand2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Log validLog1 = new Log("25-12-2024 14:30 Attended appointment");
        Log validLog2 = new Log("26-12-2024 15:30 Attended appointment");
        Nric validNric1 = new Nric("S1234567A");
        Nric validNric2 = new Nric("S1234567B");
        LogCommand logCommand1 = new LogCommand(validNric1, validLog1);
        LogCommand logCommand2 = new LogCommand(validNric2, validLog2);

        assertFalse(logCommand1.equals(logCommand2));
    }

}
