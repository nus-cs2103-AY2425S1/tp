package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandPromptsSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ViewRentalCommand}.
 */
public class ViewRentalCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // TODO: Implement
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        // TODO: Implement
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // TODO: Implement
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // TODO: Implement
    }

    @Test
    public void equals() {
        // TODO: Implement
    }

    @Test
    public void toStringMethod() {
        // TODO: Implement
    }
}