// src/test/java/seedu/address/logic/commands/ConfirmCommandTest.java
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

public class ConfirmCommandTest {

    @Test
    public void execute_noSavedCommand_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ConfirmCommand confirmCommand = new ConfirmCommand();

        assertThrows(CommandException.class, "No command to confirm.", () -> confirmCommand.execute(model));
    }

    @Test
    public void execute_hasSavedCommand_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setSavedCommand(new DeleteCommand(new IdentityNumber("S0211145D")));
        ConfirmCommand confirmCommand = new ConfirmCommand();

        CommandResult commandResult = confirmCommand.execute(model);
        assertEquals("Deleted Person: Alice Pauline; NRIC: S0211145D; Phone: 94351253; "
                + "Email: alice@example.com; Address: 123, Jurong West Ave 6, #08-111; Status: HIGH",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ConfirmCommand confirmCommand = new ConfirmCommand();

        // same object -> returns true
        assertEquals(confirmCommand, confirmCommand);

        // different types -> returns false
        assertEquals(confirmCommand.equals(1), false);

        // null -> returns false
        assertEquals(confirmCommand.equals(null), false);
    }
}
