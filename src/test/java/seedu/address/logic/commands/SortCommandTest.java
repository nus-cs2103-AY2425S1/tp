package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private static final String VALID_ATTRIBUTE = "address";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullAttribute_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null, false));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        SortCommand sortCommand = new SortCommand("", false);
        assertThrows(NullPointerException.class, () -> sortCommand.execute(null));
    }

    @Test
    public void execute_inMainWindow_returnsCommandResult() throws CommandException {
        SortCommand sortCommand = new SortCommand(VALID_ATTRIBUTE, false);
        assertEquals(new CommandResult(SortCommand.MESSAGE_FAILURE), sortCommand.execute(model));
    }

}
