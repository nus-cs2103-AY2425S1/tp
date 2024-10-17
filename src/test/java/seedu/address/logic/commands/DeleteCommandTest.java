package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest extends ApplicationTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Override
    public void start(Stage stage) {
        // This method is required by ApplicationTest but can be left empty
    }

    /*@Test
    public void equals_sameTargetName_returnsTrue() {
        Name targetName = new Name("John Doe");
        DeleteCommand deleteCommand1 = new DeleteCommand(targetName);
        DeleteCommand deleteCommand2 = new DeleteCommand(targetName);

        assertTrue(deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void equals_differentTargetName_returnsFalse() {
        Name targetName1 = new Name("John Doe");
        Name targetName2 = new Name("Jane Doe");
        DeleteCommand deleteCommand1 = new DeleteCommand(targetName1);
        DeleteCommand deleteCommand2 = new DeleteCommand(targetName2);

        assertFalse(deleteCommand1.equals(deleteCommand2));
    }

    /*@Test
    public void equals_nullTargetName_returnsFalse() {
        Name targetName = new Name("John Doe");
        DeleteCommand deleteCommand1 = new DeleteCommand(targetName);
        DeleteCommand deleteCommand2 = new DeleteCommand((Name) null);

        assertFalse(deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(new Name("Invalid Name"));
        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        // Test with index
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommandWithIndex = new DeleteCommand(targetIndex);
        String expectedIndexString = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expectedIndexString, deleteCommandWithIndex.toString());

        // Test with name
        Name targetName = new Name("John Doe");
        DeleteCommand deleteCommandWithName = new DeleteCommand(targetName);
        String expectedNameString = DeleteCommand.class.getCanonicalName() + "{targetName=" + targetName + "}";
        assertEquals(expectedNameString, deleteCommandWithName.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
