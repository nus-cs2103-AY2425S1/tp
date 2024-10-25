package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteLessonCommand.
 */
public class DeleteLessonCommandTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private final Subject subject = new Subject("Math");

    @Test
    public void execute_outOfBoundsTutorIndexUnfilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(Index.fromZeroBased(Integer.MAX_VALUE),
                Index.fromZeroBased(0), subject);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(deleteLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_outOfBoundsTuteeIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(Integer.MAX_VALUE), subject);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(deleteLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validParamsUnfilteredList_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Tutor tutor = (Tutor) getTypicalPersons().get(0);
        Tutee tutee = (Tutee) getTypicalPersons().get(3);
        Lesson lesson = new Lesson(tutor, tutee, subject);
        model.addLesson(lesson);
        model.commitAddressBook();
        expectedModel.addLesson(lesson);
        expectedModel.commitAddressBook();

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3), subject);
        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS, Messages.format(lesson));

        expectedModel.deleteLesson(lesson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteLessonCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTutorIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateFilteredPersonList(person -> person instanceof Tutee);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3), subject);
        String expectedMessage = DeleteLessonCommand.MESSAGE_INVALID_TUTOR_INDEX;
        assertCommandFailure(deleteLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidTuteeIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateFilteredPersonList(person -> person instanceof Tutor);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(2), subject);
        String expectedMessage = DeleteLessonCommand.MESSAGE_INVALID_TUTEE_INDEX;
        assertCommandFailure(deleteLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteFirstCommand = new DeleteLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(1), subject);
        DeleteLessonCommand deleteSecondCommand = new DeleteLessonCommand(Index.fromZeroBased(1),
                Index.fromZeroBased(0), subject);

        // same object -> returns true
        assert(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLessonCommand deleteFirstCommandCopy = new DeleteLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(1), subject);
        assert(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assert(!deleteFirstCommand.equals(1));

        // null -> returns false
        assert(!deleteFirstCommand.equals(null));

        // different tutor index -> returns false
        assert(!deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index1 = Index.fromZeroBased(0);
        Index index2 = Index.fromZeroBased(1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(index1, index2, subject);
        String expectedString = DeleteLessonCommand.class.getCanonicalName() + "{tutorIndex=" + index1 + ", "
                + "tuteeIndex=" + index2 + "}";
        assert(deleteLessonCommand.toString().equals(expectedString));
    }

}
