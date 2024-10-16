package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;
import seedu.address.model.person.Person;

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
        // Use a unique person to avoid DuplicatePersonException
        Person uniquePerson = new PersonBuilder().build(); // Create a unique person

        // Simulate adding a person to the model (which can then be undone)
        model.addPerson(uniquePerson);
        model.saveAddressBook();

        // Do the same for expectedModel
        expectedModel.addPerson(uniquePerson);
        expectedModel.saveAddressBook();

        // Perform undo in both the model and expectedModel
        model.undoAddressBook();
        expectedModel.undoAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noUndoableState_failure() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }
}
