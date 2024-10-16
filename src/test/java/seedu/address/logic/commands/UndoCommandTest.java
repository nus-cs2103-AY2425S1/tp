package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UndoCommand.
 */
public class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoAvailable_success() {
        // Simulate a state that can be undone by adding a person
        model.addPerson(getTypicalAddressBook().getPersonList().get(0));
        model.saveAddressBook();

        expectedModel.addPerson(getTypicalAddressBook().getPersonList().get(0));
        expectedModel.saveAddressBook();

        // Execute undo command
        model.undoAddressBook();
        expectedModel.undoAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noUndoableState_failure() {
        // Try to undo when there is no available state to undo
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }
}
