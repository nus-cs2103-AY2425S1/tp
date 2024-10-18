package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.FilterClientCommand.MESSAGE_FAILURE;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FilterClientCommand.
 */

public class FilterClientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalPropertyBook(),
            getTypicalClientBook());
    @Test
    public void execute() {
        assertCommandFailure(new FilterClientCommand(new Name(VALID_NAME_AMY)), model,
                String.format(MESSAGE_FAILURE));
    }

    @Test
    public void equals() {
        final FilterClientCommand standardCommand = new FilterClientCommand(new Name(VALID_NAME_AMY));
        // same values -> returns true
        FilterClientCommand commandWithSameValues = new FilterClientCommand(new Name(VALID_NAME_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different name -> returns false
        assertFalse(standardCommand.equals(new FilterClientCommand(new Name((VALID_NAME_BOB)))));
    }
}
