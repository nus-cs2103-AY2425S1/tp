package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ListPatientCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ListPatientCommand}.
 */
public class ListPatientCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests whether the {@code ListPatientCommand} equals method works as expected.
     * Two commands should be equal if they are the same object or if they are both instances of
     * {@code ListPatientCommand}.
     */
    @Test
    public void equals() {
        ListPatientCommand listPatientCommand = new ListPatientCommand();
        ListPatientCommand listPatientCommandCopy = new ListPatientCommand();

        // same values -> returns true
        assertEquals(listPatientCommand, listPatientCommandCopy);
        assertNotEquals(listPatientCommand, null); // Null check -> returns false
    }

    /**
     * Tests that {@code ListPatientCommand} execution fails with a {@code CommandException}
     * and the message "list-patient command not implemented yet".
     */
    @Test
    public void execute() {
        assertCommandFailure(new ListPatientCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
