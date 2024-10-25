package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL2;
import static seedu.address.testutil.TypicalTutorials.getTypicalTutorialList;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

public class DeleteTutorialCommandTest {

    private final AddressBook addressBook = new AddressBook();

    private Model model = new ModelManager(addressBook, new UserPrefs(),
            getTypicalAssignmentList(), getTypicalTutorialList());


    @Test
    public void execute_validTutorialId_success() {
        Tutorial tutorial = model.getTutorialList().getTutorials().get(0);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(tutorial);
        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS,
                tutorial.getTutorialId());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                getTypicalAssignmentList(), getTypicalTutorialList());
        expectedModel.deleteTutorial(tutorial);

        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidTutId_success() {
        Tutorial tutorial = Tutorial.of(new TutName("tut"), TutorialId.of("T5000"));
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(tutorial);
        assertCommandFailure(deleteTutorialCommand, model, DeleteTutorialCommand.MESSAGE_TUTORIAL_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteTutorialCommand deleteFirstCommand = new DeleteTutorialCommand(TUTORIAL1);
        DeleteTutorialCommand deleteSecondCommand = new DeleteTutorialCommand(TUTORIAL2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTutorialCommand deleteFirstCommandCopy = new DeleteTutorialCommand(TUTORIAL1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(TUTORIAL1);
        String expected = DeleteTutorialCommand.class.getCanonicalName() + "{tutorial=" + TUTORIAL1 + "}";
        assertEquals(expected, deleteTutorialCommand.toString());
    }
}
