package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_FINAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteExamCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteExamForAllStudents_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person).withExams().build());
        }
        Exam exam = new Exam(VALID_EXAM_MIDTERM);
        DeleteExamCommand deleteExamCommand = new DeleteExamCommand(exam);
        String expectedMessage = String.format(DeleteExamCommand.MESSAGE_DELETEEXAM_SUCCESS, exam);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        addressBook.setPerson(firstPerson, new PersonBuilder(firstPerson).withExams(VALID_EXAM_FINAL).build());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(deleteExamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteExamForSomeStudents_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person).withExams(VALID_EXAM_MIDTERM).build());
        }
        Exam exam = new Exam(VALID_EXAM_FINAL);
        DeleteExamCommand deleteExamCommand = new DeleteExamCommand(exam);
        String expectedMessage = String.format(DeleteExamCommand.MESSAGE_DELETEEXAM_SUCCESS, exam);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(deleteExamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_examNotFound_throwsCommandException() {
        DeleteExamCommand deleteExamCommand = new DeleteExamCommand(new Exam(VALID_EXAM_QUIZ));
        assertThrows(CommandException.class, DeleteExamCommand.MESSAGE_EXAM_NOT_FOUND, () -> deleteExamCommand
                .execute(model));
    }

    @Test
    public void equals() {
        final DeleteExamCommand standardCommand = new DeleteExamCommand(new Exam(VALID_EXAM_MIDTERM));

        // same values -> returns true
        assertTrue(standardCommand.equals(new DeleteExamCommand(new Exam(VALID_EXAM_MIDTERM))));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different exam -> returns false
        assertFalse(standardCommand.equals(new DeleteExamCommand(new Exam(VALID_EXAM_FINAL))));
    }
}
