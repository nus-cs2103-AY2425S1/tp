package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.NoWindowException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


class CloseCommandTest {
    public static final Index VIEW_STUB = Index.fromOneBased(1);
    private static CloseCommand closeCommand = new CloseCommand();
    private ViewCommand viewCommandModel = new ViewCommand(VIEW_STUB);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void closeCurrentWindow_noWindowOpen_throwsNoWindowException() {
        assertThrows(NoWindowException.class, ViewCommand::closeCurrentWindow, ViewCommand.NO_WINDOWS_OPEN);
    }


    @Test
    void testEquals() {
        //same object -> true

        assertTrue(closeCommand.equals(new CloseCommand()));
        assertTrue(closeCommand.equals(closeCommand));
        // null -> return false
        assertFalse(closeCommand.equals(null));
        // different command type -> return false
        assertFalse(closeCommand.equals(new ClearCommand()));
    }
}
