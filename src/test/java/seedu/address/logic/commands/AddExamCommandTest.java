package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_FINAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_PRACTICAL;
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

public class AddExamCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNewExam_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person).withExams(VALID_EXAM_MIDTERM, VALID_EXAM_PRACTICAL).build());
        }
        Exam exam = new Exam(VALID_EXAM_PRACTICAL);
        AddExamCommand addExamCommand = new AddExamCommand(exam);
        String expectedMessage = String.format(AddExamCommand.MESSAGE_ADDEXAM_SUCCESS, exam);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        addressBook.setPerson(firstPerson, new PersonBuilder(firstPerson).withExams(VALID_EXAM_MIDTERM,
                VALID_EXAM_FINAL, VALID_EXAM_PRACTICAL).build());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(addExamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addExistingExamForNewStudents_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person).withExams(VALID_EXAM_MIDTERM, VALID_EXAM_FINAL).build());
        }
        Exam exam = new Exam(VALID_EXAM_FINAL);
        AddExamCommand addExamCommand = new AddExamCommand(new Exam(VALID_EXAM_FINAL));
        String expectedMessage = String.format(AddExamCommand.MESSAGE_UPDATE_EXAM, exam);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(addExamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateExam_throwsCommandException() {
        AddExamCommand addExamCommand = new AddExamCommand(new Exam(VALID_EXAM_MIDTERM));
        assertThrows(CommandException.class, AddExamCommand.MESSAGE_DUPLICATE_EXAM, () -> addExamCommand
                .execute(model));
    }

    @Test
    public void equals() {
        final AddExamCommand standardCommand = new AddExamCommand(new Exam(VALID_EXAM_MIDTERM));

        // same values -> returns true
        assertTrue(standardCommand.equals(new AddExamCommand(new Exam(VALID_EXAM_MIDTERM))));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different exam -> returns false
        assertFalse(standardCommand.equals(new AddExamCommand(new Exam(VALID_EXAM_FINAL))));
    }
}
