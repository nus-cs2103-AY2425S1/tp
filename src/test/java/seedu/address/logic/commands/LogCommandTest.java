package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Log;

public class LogCommandTest {

    @Test
    public void execute_validIndexAndLog_addsLogSuccessfully() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Index validIndex = Index.fromOneBased(1);
        LogCommand logCommand = new LogCommand(validIndex, validLog);

        CommandResult commandResult = logCommand.execute(model);

        assertEquals("Log added to " + model.getFilteredPersonList()
                .get(validIndex.getZeroBased()).getName(), commandResult.getFeedbackToUser());
        assertTrue(model.getFilteredPersonList().get(validIndex.getZeroBased())
                .getLogEntries().getLogs().contains(validLog));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LogCommand logCommand = new LogCommand(invalidIndex, validLog);

        assertThrows(CommandException.class, () -> logCommand.execute(model));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Index validIndex = Index.fromOneBased(1);
        LogCommand logCommand = new LogCommand(validIndex, validLog);

        assertTrue(logCommand.equals(logCommand));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Index validIndex = Index.fromOneBased(1);
        LogCommand logCommand = new LogCommand(validIndex, validLog);

        assertFalse(logCommand.equals(null));
        assertFalse(logCommand.equals(new ClearCommand()));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Log validLog = new Log("25-12-2024 14:30 Attended appointment");
        Index validIndex = Index.fromOneBased(1);
        LogCommand logCommand1 = new LogCommand(validIndex, validLog);
        LogCommand logCommand2 = new LogCommand(validIndex, validLog);

        assertTrue(logCommand1.equals(logCommand2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Log validLog1 = new Log("25-12-2024 14:30 Attended appointment");
        Log validLog2 = new Log("26-12-2024 15:30 Attended appointment");
        Index validIndex1 = Index.fromOneBased(1);
        Index validIndex2 = Index.fromOneBased(2);
        LogCommand logCommand1 = new LogCommand(validIndex1, validLog1);
        LogCommand logCommand2 = new LogCommand(validIndex2, validLog2);

        assertFalse(logCommand1.equals(logCommand2));
    }

}
