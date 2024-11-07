package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.UpdateStudentDescriptorBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateCommand.
 */
public class UpdateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student updatedStudent = model.getAddressBook().getStudentList().get(0);
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder(updatedStudent).build();
        UpdateCommand updateCommand = new UpdateCommand(updatedStudent.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Optional<Student> studentToUpdate = model.getAddressBook()
                .getStudentList()
                .stream()
                .filter(student -> student.getName().equals(updatedStudent.getName()))
                .findFirst();

        if (studentToUpdate.isPresent()) {
            expectedModel.setStudent(studentToUpdate.get(), updatedStudent);
        } else {
            throw new IllegalStateException("Student to update not found");
        }

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student updatedStudent = studentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .withSubjects(VALID_SUBJECT_MATH).build();

        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withLevel(VALID_LEVEL_S1_EXPRESS).withSubjects(VALID_SUBJECT_MATH).build();
        UpdateCommand updateCommand = new UpdateCommand(lastStudent.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(lastStudent, updatedStudent);

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Student updatedStudent = model.getFilteredStudentList().get(0);
        UpdateCommand updateCommand =
                new UpdateCommand(updatedStudent.getName(), new UpdateStudentDescriptor());

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student updatedStudent = studentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .withSubjects(VALID_SUBJECT_MATH).build();

        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withLevel(VALID_LEVEL_S1_EXPRESS).withSubjects(VALID_SUBJECT_MATH).build();
        UpdateCommand updateCommand = new UpdateCommand(lastStudent.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(lastStudent, updatedStudent);

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_levelNoneNoneNoSubjectsClearsSubjects_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student updatedStudent = studentInList.withLevel("NONE NONE").withSubjects().build();

        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder().withLevel("NONE NONE").build();
        UpdateCommand updateCommand = new UpdateCommand(lastStudent.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(lastStudent, updatedStudent);

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_levelNoneNoneWithSubjectsClearsSubjects_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student updatedStudent = studentInList.withLevel("NONE NONE").withSubjects().build();

        UpdateStudentDescriptor descriptor =
                new UpdateStudentDescriptorBuilder().withLevel("NONE NONE").withSubjects("A_MATH", "PHYSICS").build();
        UpdateCommand updateCommand = new UpdateCommand(lastStudent.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS, Messages.format(updatedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(lastStudent, updatedStudent);

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder(firstStudent).build();
        UpdateCommand updateCommand = new UpdateCommand(model.getFilteredStudentList().get(1).getName(), descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_duplicateStudentFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        // update student in filtered list into a duplicate in address book
        Student studentInList = model.getAddressBook().getStudentList().get(INDEX_SECOND_STUDENT.getZeroBased());
        UpdateCommand updateCommand = new UpdateCommand(model.getFilteredStudentList().get(0).getName(),
                new UpdateStudentDescriptorBuilder(studentInList).build());

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Name invalidName = new Name("Skibidi");
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(invalidName, descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_STUDENT_UPDATE);
    }

    /**
     * Update filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Name invalidName = new Name("Skibidi");

        UpdateCommand updateCommand = new UpdateCommand(invalidName,
                new UpdateStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_STUDENT_UPDATE);
    }

    @Test
    public void execute_updateStudentWithNoSubjectsWithLevel_success() {
        //Remove subjects of student in address book
        Student studentInList = model.getAddressBook()
                .getStudentList()
                .get(INDEX_SECOND_STUDENT
                        .getZeroBased());
        Student replaceStudent = new StudentBuilder(studentInList).withSubjects().build();
        model.setStudent(studentInList, replaceStudent);

        //Get new student for expected message
        Student newStudent = model.getAddressBook()
                .getStudentList()
                .get(INDEX_SECOND_STUDENT
                        .getZeroBased());

        Name studentName = newStudent.getName();

        //Student after being updated with new Level
        Student finalStudent = new StudentBuilder(newStudent).withLevel("S3 NA").build();

        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder().withLevel("S3 NA").build();
        UpdateCommand updateCommand = new UpdateCommand(studentName, descriptor);
        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_STUDENT_SUCCESS,
                Messages.format(finalStudent));

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, model);
    }

    @Test
    public void execute_invalidLevelForStudentSubjects_failure() {
        Student studentInList = model.getAddressBook()
                .getStudentList()
                .get(INDEX_SECOND_STUDENT
                        .getZeroBased());

        //Ensures Student first has a Valid Level and Subject before making it invalid
        Student replacement = new StudentBuilder(studentInList).withLevel("S2 NA").withSubjects("Math").build();
        model.setStudent(studentInList, replacement);

        UpdateStudentDescriptor descriptor =
                new UpdateStudentDescriptorBuilder()
                        .withLevel("S3 Express")
                        .withSubjects("SCIENCE")
                        .build();

        String expectedMessage = "Subject is not valid for given level. "
                + "Valid subjects for S3 EXPRESS: [MATH, A_MATH, E_MATH, PHYSICS, CHEMISTRY, "
                + "BIOLOGY, COMBINED_SCIENCE, ACCOUNTING, LITERATURE, HISTORY, GEOGRAPHY, "
                + "SOCIAL_STUDIES, MUSIC, ART, ENGLISH, CHINESE, HIGHER_CHINESE, MALAY, "
                + "HIGHER_MALAY, TAMIL, HIGHER_TAMIL, HINDI]";

        UpdateCommand updateCommand = new UpdateCommand(replacement.getName(), descriptor);
        assertCommandFailure(updateCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(new Name(VALID_NAME_AMY), DESC_AMY);

        // same values -> returns true
        UpdateStudentDescriptor copyDescriptor = new UpdateStudentDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues = new UpdateCommand(new Name(VALID_NAME_AMY), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different names -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(new Name(VALID_NAME_BOB), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(new Name(VALID_NAME_AMY), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("Test name");
        UpdateStudentDescriptor updateStudentDescriptor = new UpdateStudentDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(name, updateStudentDescriptor);
        String expected = UpdateCommand.class.getCanonicalName() + "{name=" + name + ", updateStudentDescriptor="
                + updateStudentDescriptor + "}";
        assertEquals(expected, updateCommand.toString());
    }

}
