package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAssignments.getTypicalAssignmentList;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.Tut;


public class DeleteTutorialCommandTest {
    private static final Tut TUTORIAL_1 =
            new Tut("TutorialThree", new TutorialClass("1000"));

    private static final Tut TUTORIAL_2 =
            new Tut("TutorialTwo", new TutorialClass("1001"));

    private static final Tut TUTORIAL_3 =
            new Tut("TutorialOne", new TutorialClass("1002"));

    private static final TutorialClass TUTORIAL_CLASS = new TutorialClass("1000");

    private final AddressBook addressBook = new AddressBook();

    private Model model = new ModelManager(addressBook, new UserPrefs(), getTypicalAssignmentList(), new ArrayList<>());


    @Test
    public void execute_validTutorialId_success() {

        addressBook.addTutorial(TUTORIAL_1);
        addressBook.addTutorial(TUTORIAL_2);
        addressBook.addTutorial(TUTORIAL_3);

        assertTrue(addressBook.hasTutorial(TUTORIAL_1));
        addressBook.deleteTutorial(TUTORIAL_CLASS);
        assertFalse(addressBook.hasTutorial(TUTORIAL_1));
        assertTrue(addressBook.hasTutorial(TUTORIAL_2));
        assertTrue(addressBook.hasTutorial(TUTORIAL_3));

    }

    @Test
    public void execute_invalidTutId_success() {
        TutorialClass tutorialClass = new TutorialClass("1000");
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(tutorialClass);
        assertCommandFailure(deleteTutorialCommand, model, DeleteTutorialCommand.MESSAGE_TUTORIAL_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteTutorialCommand deleteFirstCommand = new DeleteTutorialCommand(TUTORIAL_CLASS);
        DeleteTutorialCommand deleteSecondCommand = new DeleteTutorialCommand(new TutorialClass("2000"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTutorialCommand deleteFirstCommandCopy = new DeleteTutorialCommand(TUTORIAL_CLASS);
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
        TutorialClass tutorialClass = TUTORIAL_CLASS;
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(tutorialClass);
        String expected = DeleteTutorialCommand.class.getCanonicalName() + "{tutorial=" + tutorialClass + "}";
        assertEquals(expected, deleteTutorialCommand.toString());
    }
}
