package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NoteCommand.
 */
public class NoteCommandTest {
    private static final String NOTE_STUB = "Some note";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNote_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withNote(NOTE_STUB).build();
        NoteCommand noteCommand = new NoteCommand(firstStudent.getName(), new Note(editedStudent.getNote().value));
        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, Messages.format(editedStudent));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(firstStudent, editedStudent);
        assertCommandSuccess(noteCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_deleteNote_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withNote("").build();
        NoteCommand noteCommand = new NoteCommand(firstStudent.getName(),
                new Note(editedStudent.getNote().toString()));
        String expectedMessage = String.format(NoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, Messages.format(editedStudent));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(firstStudent, editedStudent);
        assertCommandSuccess(noteCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_invalidStudentName_failure() {
        Name invalidName = new Name("FancyPants");
        NoteCommand noteCommand = new NoteCommand(invalidName, new Note(VALID_NOTE_BOB));
        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        final NoteCommand standardCommand = new NoteCommand(AMY.getName(), new Note(VALID_NOTE_AMY));

        // same values -> returns true
        NoteCommand commandWithSameValues = new NoteCommand(AMY.getName(), new Note(VALID_NOTE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(BOB.getName(), new Note(VALID_NOTE_AMY))));

        // different note -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(AMY.getName(), new Note(VALID_NOTE_BOB))));
    }
}
