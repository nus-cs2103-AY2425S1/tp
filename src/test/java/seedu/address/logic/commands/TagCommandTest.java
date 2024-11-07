package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S4_NT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
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

public class TagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null, null));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student taggedStudent = model.getAddressBook().getStudentList().get(0);
        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder(taggedStudent).build();

        TagCommand tagCommand = new TagCommand(taggedStudent.getName(), descriptor);

        String expectedMessage =
                String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(taggedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Optional<Student> studentToTag = model.getAddressBook()
                .getStudentList()
                .stream()
                .filter(student -> student.getName().equals(taggedStudent.getName()))
                .findFirst();

        if (studentToTag.isPresent()) {
            expectedModel.setStudent(studentToTag.get(), taggedStudent);
        } else {
            throw new IllegalStateException("Student to tag not found");
        }
        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecificUnfilteredList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student taggedStudent = studentInList
                .withName(VALID_NAME_BOB)
                .withSubjects(VALID_SUBJECT_ENGLISH)
                .withLevel(VALID_LEVEL_S4_NT)
                .build();

        UpdateStudentDescriptor descriptor = new UpdateStudentDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withSubjects(VALID_SUBJECT_ENGLISH)
                .withLevel(VALID_LEVEL_S4_NT)
                .build();

        TagCommand tagCommand = new TagCommand(lastStudent.getName(), descriptor);

        String expectedMessage =
                String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(taggedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(lastStudent, taggedStudent);

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Student taggedStudent = model.getFilteredStudentList().get(0);
        TagCommand tagCommand = new TagCommand(taggedStudent.getName(), new UpdateStudentDescriptor());

        String expectedMessage =
                String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(taggedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student taggedStudent = new StudentBuilder(studentInFilteredList).withName(VALID_NAME_BOB).build();
        TagCommand tagCommand = new TagCommand(studentInFilteredList.getName(),
                new UpdateStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS,
                Messages.format(taggedStudent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), taggedStudent);

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Name invalidName = new Name("Skibidi");
        UpdateStudentDescriptor descriptor =
                new UpdateStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        TagCommand tagCommand = new TagCommand(invalidName, descriptor);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_STUDENT_NOT_FOUND);
    }

    @Test
    public void execute_tagStudentWithNoSubjectsWithLevel_success() {
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
        TagCommand tagCommand = new TagCommand(studentName, descriptor);
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS,
                Messages.format(finalStudent));

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, model);
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
                        .build();

        String expectedMessage = "Subject is not valid for given level. "
                + "Valid subjects for S3 EXPRESS: [A_MATH, E_MATH, PHYSICS, CHEMISTRY, "
                + "BIOLOGY, COMBINED_SCIENCE, ACCOUNTING, LITERATURE, HISTORY, GEOGRAPHY, "
                + "SOCIAL_STUDIES, MUSIC, ART, ENGLISH, CHINESE, HIGHER_CHINESE, MALAY, "
                + "HIGHER_MALAY, TAMIL, HIGHER_TAMIL, HINDI]";

        TagCommand tagCommand = new TagCommand(replacement.getName(), descriptor);
        assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_clearSubjectsWhenOnlyLevelNoneNoneProvided() {
        Student studentInList = model.getAddressBook()
                .getStudentList()
                .get(INDEX_SECOND_STUDENT
                        .getZeroBased());

        //Ensures Student first has a Valid Level and Subject before clearing it
        Student replacement = new StudentBuilder(studentInList).withLevel("S2 NA").withSubjects("Math").build();
        model.setStudent(studentInList, replacement);

        UpdateStudentDescriptor descriptor =
                new UpdateStudentDescriptorBuilder()
                        .withLevel("NONE NONE")
                        .build();
        TagCommand tagCommand = new TagCommand(studentInList.getName(), descriptor);
        Student finalStudent = new StudentBuilder(studentInList).withLevel("NONE NONE").withSubjects().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_STUDENT_SUCCESS,
                Messages.format(finalStudent));

        assertCommandSuccess(tagCommand, model, expectedMessage, UiState.DETAILS, model);
    }

    @Test
    public void execute_doesNotClearSubjectsWhenLevelNoneNoneAndSubjectsProvided() {
        Student studentInList = model.getAddressBook()
                .getStudentList()
                .get(INDEX_SECOND_STUDENT
                        .getZeroBased());

        //Ensures Student first has a Valid Level and Subject before clearing it
        Student replacement = new StudentBuilder(studentInList).withLevel("S2 NA").withSubjects("Math").build();
        model.setStudent(studentInList, replacement);

        UpdateStudentDescriptor descriptor =
                new UpdateStudentDescriptorBuilder()
                        .withLevel("NONE NONE")
                        .withSubjects("MATH")
                        .build();
        TagCommand tagCommand = new TagCommand(studentInList.getName(), descriptor);

        assertCommandFailure(tagCommand, model, "Tag a student with a level first or in the same command");
    }

    @Test
    public void equals() {
        TagCommand tagCommand = new TagCommand(new Name(VALID_NAME_BOB), DESC_BOB);

        UpdateStudentDescriptor copyDescriptor = new UpdateStudentDescriptor(DESC_BOB);
        TagCommand tagCommandWithSameValues = new TagCommand(new Name(VALID_NAME_BOB), copyDescriptor);
        assertEquals(tagCommand, tagCommandWithSameValues);

        assertEquals(tagCommand, tagCommand);

        assertNotEquals(tagCommand, null);

        assertNotEquals(tagCommand, new TagCommand(new Name(VALID_NAME_AMY), DESC_AMY));

        assertNotEquals(tagCommand, new TagCommand(new Name(VALID_NAME_BOB), DESC_AMY));
    }

    @Test
    public void toString_success() {
        Name name = new Name(VALID_NAME_BOB);
        UpdateStudentDescriptor updateStudentDescriptor = new UpdateStudentDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH)
                .withLevel(VALID_LEVEL_S4_NT)
                .build();

        TagCommand tagCommand = new TagCommand(name, updateStudentDescriptor);

        String expected =
                String.format("%s{name=%s, level=Optional[%s], subjects=Optional[[[%s]]]}",
                        TagCommand.class.getCanonicalName(), name, VALID_LEVEL_S4_NT, VALID_SUBJECT_MATH);

        assertEquals(expected, tagCommand.toString());
    }
}
