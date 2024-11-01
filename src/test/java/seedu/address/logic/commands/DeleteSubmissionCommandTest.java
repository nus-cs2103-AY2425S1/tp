package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_TUTORIAL_1;
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
import seedu.address.model.person.Person;
import seedu.address.model.submission.Submission;
import seedu.address.testutil.PersonBuilder;

public class DeleteSubmissionCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteSubmissionForAllStudents_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person).withSubmissions().build());
        }
        Submission submission = new Submission(VALID_SUBMISSION_ASSIGNMENT_1);
        DeleteSubmissionCommand deleteSubmissionCommand = new DeleteSubmissionCommand(submission);
        String expectedMessage = String.format(DeleteSubmissionCommand.MESSAGE_DELETESUBMISSION_SUCCESS, submission);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        addressBook.setPerson(firstPerson, new PersonBuilder(firstPerson)
                .withSubmissions(VALID_SUBMISSION_TUTORIAL_1).build());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(deleteSubmissionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteSubmissionForSomeStudents_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person).withSubmissions(VALID_SUBMISSION_ASSIGNMENT_1).build());
        }
        Submission submission = new Submission(VALID_SUBMISSION_TUTORIAL_1);
        DeleteSubmissionCommand deleteSubmissionCommand = new DeleteSubmissionCommand(submission);
        String expectedMessage = String.format(DeleteSubmissionCommand.MESSAGE_DELETESUBMISSION_SUCCESS, submission);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(deleteSubmissionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_submissionNotFound_throwsCommandException() {
        DeleteSubmissionCommand deleteSubmissionCommand =
                new DeleteSubmissionCommand(new Submission(VALID_SUBMISSION_ASSIGNMENT_2));
        assertThrows(CommandException.class, DeleteSubmissionCommand.MESSAGE_SUBMISSION_NOT_FOUND, (
                ) -> deleteSubmissionCommand.execute(model));
    }

    @Test
    public void equals() {
        final DeleteSubmissionCommand standardCommand =
                new DeleteSubmissionCommand(new Submission(VALID_SUBMISSION_ASSIGNMENT_1));

        // same values -> returns true
        assertTrue(standardCommand.equals(new DeleteSubmissionCommand(new Submission(VALID_SUBMISSION_ASSIGNMENT_1))));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different submission -> returns false
        assertFalse(standardCommand.equals(new DeleteSubmissionCommand(new Submission(VALID_SUBMISSION_TUTORIAL_1))));
    }
}
