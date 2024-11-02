package careconnect.logic.commands;
import static careconnect.testutil.Assert.assertThrows;
import static careconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

import careconnect.commons.core.index.Index;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.UserPrefs;
import careconnect.model.log.Log;
import careconnect.model.person.Person;

public class DeleteLogCommandTest {

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new DeleteLogCommand(null, Index.fromZeroBased(0))
                        .execute(model));
        assertThrows(NullPointerException.class,
                () -> new DeleteLogCommand(Index.fromZeroBased(0), null)
                        .execute(model));
    }

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndex_logDeleteFailed() {
        assertThrows(CommandException.class, () -> new DeleteLogCommand(Index.fromZeroBased(0),
                        Index.fromZeroBased(1000)).execute(model));
        assertThrows(CommandException.class, () -> new DeleteLogCommand(Index.fromZeroBased(1000),
                Index.fromZeroBased(0)).execute(model));
    }

    @Test
    public void execute_validIndex_logDeleteRequiresConfirmation() throws Exception {
        Person validPerson = model.getFilteredPersonList().get(0);
        Log logToDelete = validPerson.getLogs().get(0);
        CommandResult commandResult =
                new DeleteLogCommand(Index.fromZeroBased(0),
                        Index.fromZeroBased(0)).execute(model);

        assertEquals(Command.CONFIRMATION_MESSAGE, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validIndexWithConfirmation_logDeleteSuccess() throws Exception {
        Person validPerson = model.getFilteredPersonList().get(0);
        Log logToDelete = validPerson.getLogs().get(0);

        DeleteLogCommand deleteLogCommand = new DeleteLogCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(0));
        deleteLogCommand.execute(model);
        CommandResult commandResult = new ConfirmationYesCommand().execute(model);
        assertEquals(String.format(DeleteLogCommand.MESSAGE_DELETE_LOG_SUCCESS,
                        logToDelete.getRemark(),
                        validPerson.getName()),
                commandResult.getFeedbackToUser());
    }


    @Test
    public void equals() throws CommandException {
        int person1 = 2;
        int person2 = 3;
        int logIndex1 = 0;
        int logIndex2 = 1;
        DeleteLogCommand deleteLogCommand1 = new DeleteLogCommand(Index.fromZeroBased(person1),
                Index.fromZeroBased(logIndex1));
        DeleteLogCommand deleteLogCommand2 = new DeleteLogCommand(Index.fromZeroBased(person1),
                Index.fromZeroBased(logIndex1));
        DeleteLogCommand deleteLogCommand3 = new DeleteLogCommand(Index.fromZeroBased(person2),
                Index.fromZeroBased(logIndex2));
        DeleteLogCommand deleteLogCommand4 = new DeleteLogCommand(Index.fromZeroBased(person2),
                Index.fromZeroBased(logIndex1));

        // same object -> returns true
        assertTrue(deleteLogCommand1.equals(deleteLogCommand1));

        // same values -> returns true
        assertTrue(deleteLogCommand1.equals(deleteLogCommand2));

        // different types -> returns false
        assertFalse(deleteLogCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteLogCommand1.equals(null));

        // different person same log index -> returns false
        assertFalse(deleteLogCommand4.equals(deleteLogCommand1));

        // different log same person -> returns false
        assertFalse(deleteLogCommand3.equals(deleteLogCommand4));
    }
}
