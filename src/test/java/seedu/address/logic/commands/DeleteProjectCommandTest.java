package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteProjectCommand}.
 */
public class DeleteProjectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project projectToDelete = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(INDEX_FIRST_PROJECT);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(projectToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteProject(projectToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project projectToDelete = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(INDEX_FIRST_PROJECT);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(projectToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteProject(projectToDelete);
        showNoProject(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Index outOfBoundIndex = INDEX_SECOND_PROJECT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getProjectList().size());

        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteProjectCommand deleteFirstCommand = new DeleteProjectCommand(INDEX_FIRST_PROJECT);
        DeleteProjectCommand deleteSecondCommand = new DeleteProjectCommand(INDEX_SECOND_PROJECT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteProjectCommand deleteFirstCommandCopy = new DeleteProjectCommand(INDEX_FIRST_PROJECT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(targetIndex);
        String expected = DeleteProjectCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteProjectCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoProject(Model model) {
        model.updateFilteredProjectList(p -> false);

        assertTrue(model.getFilteredProjectList().isEmpty());
    }
}
