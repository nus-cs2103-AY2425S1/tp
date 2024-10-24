package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.submission.Submission;
import seedu.address.testutil.PersonBuilder;

public class AddSubmissionStatusCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addSubmissionStatus_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withSubmissionStatus(VALID_SUBMISSION_ASSIGNMENT_1, VALID_SUBMISSION_STATUS_AMY).build();
        AddSubmissionStatusCommand addSubmissionStatusCommand = new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1), VALID_SUBMISSION_STATUS_AMY);
        String expectedMessage = String.format(AddSubmissionStatusCommand.MESSAGE_ADDSUBMISSIONSTATUS_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addSubmissionStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddSubmissionStatusCommand addSubmissionStatusCommand =
                new AddSubmissionStatusCommand(outOfBoundIndex, new Submission(VALID_SUBMISSION_ASSIGNMENT_1),
                VALID_SUBMISSION_STATUS_AMY);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, (
                ) -> addSubmissionStatusCommand.execute(model));
    }

    @Test
    public void execute_submissionNotFound_throwsCommandException() {
        AddSubmissionStatusCommand addSubmissionStatusCommand = new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_2), VALID_SUBMISSION_STATUS_AMY);
        assertThrows(CommandException.class, AddSubmissionStatusCommand.MESSAGE_SUBMISSION_NOT_FOUND, (
                ) -> addSubmissionStatusCommand.execute(model));
    }

    @Test
    public void equals() {
        final AddSubmissionStatusCommand standardCommand = new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1), VALID_SUBMISSION_STATUS_AMY);

        // same values -> returns true
        assertTrue(standardCommand.equals(new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1), VALID_SUBMISSION_STATUS_AMY)));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddSubmissionStatusCommand(INDEX_SECOND_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1), VALID_SUBMISSION_STATUS_AMY)));

        // different submission -> returns false
        assertFalse(standardCommand.equals(new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_2), VALID_SUBMISSION_STATUS_AMY)));

        // different submission status -> returns false
        assertFalse(standardCommand.equals(new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1), VALID_SUBMISSION_STATUS_BOB)));
    }
}
