package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Grade;
import seedu.address.testutil.StudentBuilder;

public class GradeCommandTest {
    private static final String GRADE_STUB = "1";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addGradeUnfilteredList_success() {
        Student firstStudent = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withGrade(GRADE_STUB).build();

        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(editedStudent.getGrade().gradeIndex));

        String expectedMessage = String.format(GradeCommand.MESSAGE_ADD_GRADE_SUCCESS, editedStudent.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstStudent, editedStudent);

        assertCommandSuccess(gradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteGradeUnfilteredList_success() {
        Student firstStudent = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withGrade("0").build();

        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(editedStudent.getGrade().toString()));

        String expectedMessage = String.format(GradeCommand.MESSAGE_DELETE_GRADE_SUCCESS, editedStudent.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstStudent, editedStudent);

        assertCommandSuccess(gradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Student firstStudent = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent)
                .withGrade(GRADE_STUB).build();

        GradeCommand gradeCommand = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(editedStudent.getGrade().gradeIndex));

        String expectedMessage = String.format(GradeCommand.MESSAGE_ADD_GRADE_SUCCESS, editedStudent.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstStudent, editedStudent);

        assertCommandSuccess(gradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GradeCommand gradeCommand = new GradeCommand(outOfBoundIndex, new Grade(VALID_GRADE_BOB));

        assertCommandFailure(gradeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GradeCommand gradeCommand = new GradeCommand(outOfBoundIndex, new Grade(VALID_GRADE_BOB));

        assertCommandFailure(gradeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final GradeCommand standardCommand = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(VALID_GRADE_AMY));

        // same values -> returns true
        GradeCommand commandWithSameValues = new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(VALID_GRADE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GradeCommand(INDEX_SECOND_PERSON,
                new Grade(VALID_GRADE_AMY))));

        // different Grade -> returns false
        assertFalse(standardCommand.equals(new GradeCommand(INDEX_FIRST_PERSON,
                new Grade(VALID_GRADE_BOB))));
    }
}
