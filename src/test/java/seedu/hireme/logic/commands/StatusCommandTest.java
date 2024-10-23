package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private Model<InternshipApplication> model = new ModelManager<>(getClonedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Model<InternshipApplication> clonedModel = new ModelManager<>(getClonedAddressBook(), new UserPrefs());

        InternshipApplication internshipApplicationToUpdate = clonedModel
                .getFilteredList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);

        String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_CHANGE_SUCCESS,
                Messages.format(internshipApplicationToUpdate), Status.ACCEPTED.getValue());

        ModelManager<InternshipApplication> expectedModel = new ModelManager<>(getClonedAddressBook(), new UserPrefs());

        InternshipApplication updatedApplication = new InternshipApplication(
                internshipApplicationToUpdate.getCompany(),
                internshipApplicationToUpdate.getDateOfApplication(),
                internshipApplicationToUpdate.getRole(),
                Status.ACCEPTED
        );

        expectedModel.setItem(internshipApplicationToUpdate, updatedApplication);

        assertCommandSuccess(statusCommand, clonedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredList().size() + 1);
        StatusCommand statusCommand = new StatusCommand(outOfBoundIndex, Status.ACCEPTED);

        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getList().size());

        StatusCommand statusCommand = new StatusCommand(outOfBoundIndex, Status.PENDING);

        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        StatusCommand statusFirstCommand = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);
        StatusCommand statusSecondCommand = new StatusCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, Status.PENDING);

        assertTrue(statusFirstCommand.equals(statusFirstCommand));

        StatusCommand statusFirstCommandCopy = new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED);
        assertTrue(statusFirstCommand.equals(statusFirstCommandCopy));

        assertFalse(statusFirstCommand.equals(1));
        assertFalse(statusFirstCommand.equals(null));
        assertFalse(statusFirstCommand.equals(statusSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        StatusCommand statusCommand = new StatusCommand(targetIndex, Status.ACCEPTED);
        String expected = StatusCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", newStatus=" + Status.ACCEPTED + "}";
        assertEquals(expected, statusCommand.toString());
    }

    private void showNoInternshipApplication(Model<InternshipApplication> model) {
        model.updateFilteredList(p -> false);
        assertTrue(model.getFilteredList().isEmpty());
    }
}
