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
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.UserPrefs;
import careconnect.model.log.Log;
import careconnect.model.person.Person;

public class AddLogCommandTest {

    @Test
    public void constructor_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new AddLogCommand(null, new Log("empty index")));
        assertThrows(NullPointerException.class,
                () -> new AddLogCommand(Index.fromZeroBased(0), null));
    }

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Date date = Date.from(
            LocalDateTime.of(2020, 12, 31, 12, 0)
                    .atZone(ZoneId.systemDefault()).toInstant());
    private Log log1 = new Log(date, "test1");
    private Log log2 = new Log(date, "test2");

    @Test
    public void execute_logAcceptedByModel_addSuccessful() throws Exception {
        Person validPerson = model.getFilteredPersonList().get(0);
        CommandResult commandResult =
                new AddLogCommand(Index.fromZeroBased(0), log1).execute(model);

        assertEquals(String.format(AddLogCommand.MESSAGE_SUCCESS, log1.getRemark(),
                        validPerson.getName()),
                commandResult.getFeedbackToUser());
    }


    @Test
    public void equals() {
        int index1 = 1;
        int index2 = 2;
        AddLogCommand addLogCommand1 = new AddLogCommand(Index.fromZeroBased(index1), log1);
        AddLogCommand addLogCommand2 = new AddLogCommand(Index.fromZeroBased(index2), log1);
        AddLogCommand addLogCommand3 = new AddLogCommand(Index.fromZeroBased(index2), log2);
        AddLogCommand addLogCommand4 = new AddLogCommand(Index.fromZeroBased(index1), log1);

        // same object -> returns true
        assertTrue(addLogCommand1.equals(addLogCommand1));

        // same values -> returns true
        assertTrue(addLogCommand1.equals(addLogCommand4));

        // different types -> returns false
        assertFalse(addLogCommand1.equals(1));

        // null -> returns false
        assertFalse(addLogCommand1.equals(null));

        // different person same log -> returns false
        assertFalse(addLogCommand1.equals(addLogCommand2));

        // different log same person -> returns false
        assertFalse(addLogCommand2.equals(addLogCommand3));
    }

    @Test
    public void toStringMethod() {
        AddLogCommand addLogCommand = new AddLogCommand(Index.fromZeroBased(0), log1);
        String expected = AddLogCommand.class.getCanonicalName() + "{toAddLog=" + log1 + "}";
        assertEquals(expected, addLogCommand.toString());
    }
}
