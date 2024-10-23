package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_TUTORIAL_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_TUTORIAL_2;
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

public class AddSubmissionCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNewSubmission_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person)
                    .withSubmissions(VALID_SUBMISSION_ASSIGNMENT_1, VALID_SUBMISSION_TUTORIAL_2).build());
        }
        Submission submission = new Submission(VALID_SUBMISSION_TUTORIAL_2);
        AddSubmissionCommand addSubmissionCommand = new AddSubmissionCommand(submission);
        String expectedMessage = String.format(AddSubmissionCommand.MESSAGE_ADDSUBMISSION_SUCCESS, submission);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        addressBook.setPerson(firstPerson, new PersonBuilder(firstPerson).withSubmissions(VALID_SUBMISSION_ASSIGNMENT_1,
                VALID_SUBMISSION_TUTORIAL_1, VALID_SUBMISSION_TUTORIAL_2).build());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(addSubmissionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addExistingSubmissionForNewStudents_success() {
        List<Person> personList = model.getFilteredPersonList();
        List<Person> editedPersonList = new ArrayList<Person>();
        for (Person person : personList) {
            editedPersonList.add(new PersonBuilder(person)
                    .withSubmissions(VALID_SUBMISSION_ASSIGNMENT_1, VALID_SUBMISSION_TUTORIAL_1).build());
        }
        Submission submission = new Submission(VALID_SUBMISSION_TUTORIAL_1);
        AddSubmissionCommand addSubmissionCommand =
                new AddSubmissionCommand(new Submission(VALID_SUBMISSION_TUTORIAL_1));
        String expectedMessage = String.format(AddSubmissionCommand.MESSAGE_UPDATE_SUBMISSION, submission);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(editedPersonList);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(addressBook);
        assertCommandSuccess(addSubmissionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSubmission_throwsCommandException() {
        AddSubmissionCommand addSubmissionCommand =
                new AddSubmissionCommand(new Submission(VALID_SUBMISSION_ASSIGNMENT_1));
        assertThrows(CommandException.class, AddSubmissionCommand.MESSAGE_DUPLICATE_SUBMISSION, (
                ) -> addSubmissionCommand.execute(model));
    }

    @Test
    public void equals() {
        final AddSubmissionCommand standardCommand = new AddSubmissionCommand(
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1));

        // same values -> returns true
        assertTrue(standardCommand.equals(new AddSubmissionCommand(new Submission(VALID_SUBMISSION_ASSIGNMENT_1))));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different submission -> returns false
        assertFalse(standardCommand.equals(new AddSubmissionCommand(new Submission(VALID_SUBMISSION_TUTORIAL_1))));
    }
}
