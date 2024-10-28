package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
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
    private Model model = new ModelManager(new UserPrefs(), getTypicalPropertyBook(),
            getTypicalClientBook(), getTypicalMeetingBook());

    @Test
    public void execute_matchingName_filterSuccessful() {
        // Test case where a client's name matches the given prefix (valid match)
        String namePrefix = "Bob"; // Matches "Bob"
        FilterClientCommand command = new FilterClientCommand(new Name(namePrefix));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredClientList(client ->
                client.getName().toString().matches("(?i)^" + namePrefix + ".*"));

        assertCommandSuccess(command,
                model, String.format("Listed all clients with name starting with: %s", namePrefix), expectedModel);
    }

    @Test
    public void execute_caseInsensitiveMatching_filterSuccessful() {
        // Test case where the filter should work regardless of case (case insensitive)
        String namePrefix = "bob"; // Should match "Bob"
        FilterClientCommand command = new FilterClientCommand(new Name(namePrefix));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredClientList(client ->
                client.getName().toString().matches("(?i)^" + namePrefix + ".*"));

        assertCommandSuccess(command,
                model, String.format("Listed all clients with name starting with: %s", namePrefix), expectedModel);
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
        assertFalse(standardCommand.equals(new ExitCommand()));
        // different name -> returns false
        assertFalse(standardCommand.equals(new FilterClientCommand(new Name((VALID_NAME_BOB)))));
    }
}
