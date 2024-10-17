package careconnect.logic.commands;

import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static careconnect.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static careconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import careconnect.commons.core.index.Index;
import careconnect.logic.Messages;
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ViewCommand command = new ViewCommand(INDEX_FIRST_PERSON);
        CommandResult expectedResult = new CommandResult(
                ViewCommand.MESSAGE_SUCCESS,
                false,
                false,
                INDEX_FIRST_PERSON.getZeroBased()
        );

        CommandTestUtil.assertCommandSuccess(command, model, expectedResult, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand command = new ViewCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);

        ViewCommand command = new ViewCommand(INDEX_FIRST_PERSON);
        CommandResult expectedResult = new CommandResult(
                ViewCommand.MESSAGE_SUCCESS,
                false,
                false,
                INDEX_FIRST_PERSON.getZeroBased()
        );

        CommandTestUtil.assertCommandSuccess(command, model, expectedResult, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ViewCommand command = new ViewCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand command = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand secondCommand = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        ViewCommand otherCommand = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(command.equals(otherCommand));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different index -> returns false
        assertFalse(command.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand command = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
