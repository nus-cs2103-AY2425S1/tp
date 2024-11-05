package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.hireme.testutil.TypicalInternshipApplications.getClonedAddressBook;

import org.junit.jupiter.api.Test;

import seedu.hireme.commons.core.index.Index;
import seedu.hireme.logic.Messages;
import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Status;

public class StatusCommandTest {

    private final Model model = new ModelManager(getClonedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Model clonedModel = new ModelManager(getClonedAddressBook(), new UserPrefs());
        InternshipApplication internshipApplicationToUpdate = clonedModel
                .getFilteredList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);
        String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_CHANGE_SUCCESS,
                Messages.format(internshipApplicationToUpdate), Status.ACCEPTED);

        Status previousStatus = internshipApplicationToUpdate.getStatus();

        ModelManager expectedModel = new ModelManager(getClonedAddressBook(), new UserPrefs());
        InternshipApplication updatedApplication = new InternshipApplication(
                internshipApplicationToUpdate.getCompany(),
                internshipApplicationToUpdate.getDateOfApplication(),
                internshipApplicationToUpdate.getRole(),
                Status.ACCEPTED
        );
        expectedModel.setItem(internshipApplicationToUpdate, updatedApplication);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false,
                false, false, expectedModel.getChartData());

        assertCommandSuccess(statusCommand, clonedModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredList().size() + 1);
        StatusCommand statusCommand = new StatusCommand(outOfBoundIndex, Status.ACCEPTED);

        assertCommandFailure(statusCommand, model,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getList().size());

        StatusCommand statusCommand = new StatusCommand(outOfBoundIndex, Status.PENDING);

        assertCommandFailure(statusCommand, model,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model emptyModel = new ModelManager(); // An empty model with no internship applications
        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);

        assertCommandFailure(statusCommand, emptyModel,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_lastIndex_success() {
        Index lastIndex = Index.fromOneBased(model.getFilteredList().size());
        Model clonedModel = new ModelManager(getClonedAddressBook(), new UserPrefs());
        InternshipApplication internshipApplicationToUpdate =
                clonedModel.getFilteredList().get(lastIndex.getZeroBased());

        StatusCommand statusCommand = new StatusCommand(lastIndex, Status.ACCEPTED);
        String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_CHANGE_SUCCESS,
                Messages.format(internshipApplicationToUpdate), Status.ACCEPTED);

        InternshipApplication updatedApplication = new InternshipApplication(
                internshipApplicationToUpdate.getCompany(),
                internshipApplicationToUpdate.getDateOfApplication(),
                internshipApplicationToUpdate.getRole(),
                Status.ACCEPTED
        );
        ModelManager expectedModel = new ModelManager(getClonedAddressBook(), new UserPrefs());
        expectedModel.setItem(internshipApplicationToUpdate, updatedApplication);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false,
                false, false, expectedModel.getChartData());

        assertCommandSuccess(statusCommand, clonedModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        // Create two StatusCommand objects with the same index and status
        StatusCommand statusFirstCommand = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);
        StatusCommand statusFirstCommandCopy = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);

        // Test: Same object reference should be equal
        assertTrue(statusFirstCommand.equals(statusFirstCommand), "Same object reference should be equal");

        // Test: Different objects with the same values should be equal
        assertTrue(statusFirstCommand.equals(statusFirstCommandCopy),
                "Objects with the same index and status should be equal");

        // Test: Different types should not be equal
        assertFalse(statusFirstCommand.equals(1), "Objects of different types should not be equal");

        // Test: Null should not be equal
        assertFalse(statusFirstCommand.equals(null), "Comparison with null should return false");

        // Test: Objects with different indices should not be equal
        StatusCommand statusSecondCommand =
                new StatusCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, Status.ACCEPTED);
        assertFalse(statusFirstCommand.equals(statusSecondCommand),
                "Objects with different indices should not be equal");

        // Test: Objects with the same index but different statuses should not be equal
        StatusCommand statusDifferentStatusCommand =
                new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.PENDING);
        assertFalse(statusFirstCommand.equals(statusDifferentStatusCommand),
                "Objects with the same index but different statuses should not be equal");

        // Test: Objects with different indices and different statuses should not be equal
        StatusCommand completelyDifferentCommand =
                new StatusCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, Status.REJECTED);
        assertFalse(statusFirstCommand.equals(completelyDifferentCommand),
                "Objects with different indices and statuses should not be equal");

        // Test: Ensure reflexive, symmetric, and transitive properties
        assertTrue(statusFirstCommand.equals(statusFirstCommandCopy)
                        && statusFirstCommandCopy.equals(statusFirstCommand),
                "Equality should be symmetric");
        assertTrue(statusFirstCommand.equals(statusFirstCommandCopy), "Equality should be reflexive");
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        StatusCommand statusCommand = new StatusCommand(targetIndex, Status.ACCEPTED);
        String expected = StatusCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", newStatus=" + Status.ACCEPTED + "}";
        assertEquals(expected, statusCommand.toString());
    }

    private void showNoInternshipApplication(Model model) {
        model.updateFilteredList(p -> false);
        assertTrue(model.getFilteredList().isEmpty());
    }
}
