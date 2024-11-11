// src/test/java/seedu/address/logic/commands/CancelCommandTest.java
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IdentityNumber;

public class CancelCommandTest {

    @Test
    public void execute_noSavedCommand_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CancelCommand cancelCommand = new CancelCommand();

        assertThrows(CommandException.class, "No operation to cancel.", () -> cancelCommand.execute(model));
    }

    @Test
    public void execute_hasSavedCommand_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setSavedCommand(new DeleteCommand(new IdentityNumber("S1234567D")));
        CancelCommand cancelCommand = new CancelCommand();

        CommandResult commandResult = cancelCommand.execute(model);
        assertEquals("Cancelled operation: Delete person with NRIC: S1234567D", commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        CancelCommand cancelCommand = new CancelCommand();

        // same object -> returns true
        assertEquals(cancelCommand, cancelCommand);

        // different types -> returns false
        assertEquals(cancelCommand.equals(1), false);

        // null -> returns false
        assertEquals(cancelCommand.equals(null), false);
    }
}
