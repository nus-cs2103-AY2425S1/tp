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
 * Contains integration tests (interaction with the Model) and unit tests for AddLessonCommand.
 */
public class AddLessonCommandTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private final Subject subject = new Subject("Math");

    @Test
    public void execute_outOfBoundsTuteeIndexUnfilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(Integer.MAX_VALUE), subject);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(addLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_outOfBoundsTutorIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(Integer.MAX_VALUE),
                Index.fromZeroBased(0), subject);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(addLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validParamsUnfilteredList_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Tutor tutor = (Tutor) getTypicalPersons().get(0);
        Tutee tutee = (Tutee) getTypicalPersons().get(3);
        tutor.setSubject(subject);
        tutee.setSubject(subject);
        Lesson lesson = new Lesson(tutor, tutee, subject);

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3), subject);
        String expectedMessage = String.format(AddLessonCommand.MESSAGE_ADD_LESSON_SUCCESS, Messages.format(lesson));

        expectedModel.addLesson(lesson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addLessonCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTutorIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateFilteredPersonList(person -> person instanceof Tutee);
        model.getFilteredPersonList().forEach(person -> ((Tutee) person).setSubject(subject));

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
               Index.fromZeroBased(0), subject);
        String expectedMessage = AddLessonCommand.MESSAGE_INVALID_TUTOR_INDEX;

        assertCommandFailure(addLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidTuteeIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateFilteredPersonList(person -> person instanceof Tutor);
        model.getFilteredPersonList().forEach(person -> ((Tutor) person).setSubject(subject));

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
               Index.fromZeroBased(0), subject);
        String expectedMessage = AddLessonCommand.MESSAGE_INVALID_TUTEE_INDEX;

        assertCommandFailure(addLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_duplicateLessonUnfilteredList_throwsCommandException() {
        Tutor tutor = (Tutor) getTypicalPersons().get(0);
        Tutee tutee = (Tutee) getTypicalPersons().get(3);
        tutor.setSubject(subject);
        tutee.setSubject(subject);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Lesson lesson = new Lesson(tutor, tutee, subject);
        model.addLesson(lesson);
        model.commitAddressBook();

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3), subject);
        String expectedMessage = AddLessonCommand.MESSAGE_DUPLICATE_LESSON;

        assertCommandFailure(addLessonCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3), subject);
        AddLessonCommand addLessonCommandCopy = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(3), subject);
        AddLessonCommand addLessonCommandDifferentTutee = new AddLessonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(4), subject);
        AddLessonCommand addLessonCommandDifferentTutor = new AddLessonCommand(Index.fromZeroBased(1),
                Index.fromZeroBased(3), subject);

        // same object -> returns true
        assert(addLessonCommand.equals(addLessonCommand));

        // same values -> returns true
        assert(addLessonCommand.equals(addLessonCommandCopy));

        // different types -> returns false
        assert(!addLessonCommand.equals(1));

        // null -> returns false
        assert(!addLessonCommand.equals(null));

        // different tutee -> returns false
        assert(!addLessonCommand.equals(addLessonCommandDifferentTutee));

        // different tutor -> returns false
        assert(!addLessonCommand.equals(addLessonCommandDifferentTutor));
    }

    @Test
    public void toStringTest() {
        Index index1 = Index.fromZeroBased(0);
        Index index2 = Index.fromZeroBased(3);
        AddLessonCommand addLessonCommand = new AddLessonCommand(index1, index2, subject);
        String expected = AddLessonCommand.class.getCanonicalName() + "{tutorIndex=" + index1 + ", "
                + "tuteeIndex=" + index2 + "}";
        assert(addLessonCommand.toString().equals(expected));
    }
}

