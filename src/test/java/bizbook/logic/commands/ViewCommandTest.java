package bizbook.logic.commands;

import static bizbook.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static bizbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.logic.commands.ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static bizbook.testutil.TypicalPersons.ALICE;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_view_success() {
        expectedModel.getFocusedPerson().set(ALICE);

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS,
                INDEX_FIRST_PERSON.getOneBased()), false, false);
        assertCommandSuccess(new ViewCommand(INDEX_FIRST_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_view_failure() {
        assertCommandFailure(new ViewCommand(INDEX_OUTOFBOUND_PERSON), model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand firstViewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand secondViewCommand = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstViewCommand.equals(firstViewCommand));

        // same values -> returns true
        ViewCommand firstViewCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(firstViewCommand.equals(firstViewCommandCopy));

        // different types -> returns false
        assertFalse(firstViewCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewCommand.equals(null));

        // different person -> returns false
        assertFalse(firstViewCommand.equals(secondViewCommand));
    }

    @Test
    public void toStringMethod() {
        ViewCommand firstViewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        String firstViewExpected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(firstViewExpected, firstViewCommand.toString());
    }
}
