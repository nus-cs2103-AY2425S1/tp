package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_JOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_JOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalCompanies;
import seedu.address.testutil.TypicalJobs;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnmatchCommand.
 */
public class UnmatchCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        assertThrows(NullPointerException.class, () -> new UnmatchCommand(null, null));
        assertThrows(NullPointerException.class, () -> new UnmatchCommand(null, validIndex));
        assertThrows(NullPointerException.class, () -> new UnmatchCommand(validIndex, null));
    }

    @Test
    public void execute_validContactAndJob_unmatchSuccessful() throws Exception {
        Index contactIndex = INDEX_FIRST_PERSON;
        Index jobIndex = INDEX_FIRST_JOB;
        // First, set up the model with an existing match
        MatchCommand initialMatchCommand = new MatchCommand(contactIndex, jobIndex);
        initialMatchCommand.execute(model);

        UnmatchCommand unmatchCommand = new UnmatchCommand(contactIndex, jobIndex);

        Person contactToUnmatch = model.getFilteredPersonList().get(contactIndex.getZeroBased());
        Job jobToUnmatch = model.getFilteredJobList().get(jobIndex.getZeroBased());

        // Ensure the contact is currently matched to the job
        assert contactToUnmatch.hasMatched(jobToUnmatch.getIdentifier());
        // Execute the unmatch command to update the model's state
        CommandResult commandResult = unmatchCommand.execute(model);

        // Retrieve the updated contact from the model after unmatching
        Person updatedContact = model.getFilteredPersonList().get(contactIndex.getZeroBased());
        Person matchedContact = new PersonBuilder(contactToUnmatch).withMatch(jobToUnmatch.getIdentifier()).build();
        // Check if the contact is no longer matched with the job
        assertFalse(updatedContact.hasMatched(jobToUnmatch.getIdentifier()));

        String expectedMessage = String.format(UnmatchCommand.MESSAGE_UNMATCH_SUCCESS,
                Messages.format(updatedContact), Messages.format(jobToUnmatch));

        // Update expectedModel with the unmatched contact from expectedModel's list
        Person contactInExpectedModel = expectedModel.getFilteredPersonList().get(contactIndex.getZeroBased());
        Person unmatchedContact = new PersonBuilder(contactInExpectedModel).withoutMatch().build();
        expectedModel.setPerson(contactInExpectedModel, unmatchedContact);
        model.setPerson(model.getFilteredPersonList().get(contactIndex.getZeroBased()), matchedContact);

        // Verify the output message and model state
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertCommandSuccess(unmatchCommand, model, commandResult, expectedModel);
    }


    @Test
    public void execute_contactIndexOutOfRange_failure() {
        UnmatchCommand unmatchCommand = new UnmatchCommand(INDEX_OUT_OF_BOUNDS, INDEX_FIRST_JOB);
        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_jobIndexOutOfRange_failure() {
        UnmatchCommand unmatchCommand = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_OUT_OF_BOUNDS);
        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyModel_failure() {
        UnmatchCommand unmatchCommand = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandFailure(unmatchCommand, emptyModel, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_contactNotMatched_failure() {
        UnmatchCommand unmatchCommand = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);

        Person contactToUnmatch = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(UnmatchCommand.MESSAGE_CONTACT_HAS_NO_JOBS, contactToUnmatch.getName());

        assertCommandFailure(unmatchCommand, model, expectedMessage);
    }


    @Test
    public void execute_contactMatchedToAnotherJob_failure() {
        setModelAfterMatching(model, INDEX_FIRST_PERSON, INDEX_SECOND_JOB);
        UnmatchCommand unmatchCommand = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        assertCommandFailure(unmatchCommand, model, UnmatchCommand.MESSAGE_CONTACT_NOT_MATCHED);
    }

    @Test
    public void equals() {
        Index firstContactIndex = INDEX_FIRST_PERSON;
        Index firstJobIndex = INDEX_FIRST_JOB;

        UnmatchCommand unmatchCommand = new UnmatchCommand(firstContactIndex, firstJobIndex);

        // same object -> returns true
        assertTrue(unmatchCommand.equals(unmatchCommand));

        // same values -> returns true
        UnmatchCommand unmatchCommandCopy = new UnmatchCommand(firstContactIndex, firstJobIndex);
        assertTrue(unmatchCommand.equals(unmatchCommandCopy));

        // different types -> returns false
        assertFalse(unmatchCommand.equals(1));

        // null -> returns false
        assertFalse(unmatchCommand.equals(null));

        // different unmatch -> returns false
        UnmatchCommand differentJobUnmatchCommand = new UnmatchCommand(firstContactIndex, INDEX_SECOND_JOB);
        assertFalse(unmatchCommand.equals(differentJobUnmatchCommand));

        UnmatchCommand differentContactUnmatchCommand = new UnmatchCommand(INDEX_SECOND_PERSON, firstJobIndex);
        assertFalse(unmatchCommand.equals(differentContactUnmatchCommand));
    }

    @Test
    public void toStringMethod() {
        UnmatchCommand unmatchCommand = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        String expected = UnmatchCommand.class.getCanonicalName() + "{contactIndex=" + INDEX_FIRST_PERSON
                + ", jobIndex="
                + INDEX_FIRST_JOB + "}";
        assertEquals(expected, unmatchCommand.toString());
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons, jobs and companies.
     */
    private AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Company company : TypicalCompanies.getTypicalCompanies()) {
            ab.addCompany(company);
        }
        for (Job job : TypicalJobs.getTypicalJobs()) {
            ab.addJob(job);
        }
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    private void setModelAfterMatching(Model model, Index contactIndex, Index jobIndex) {
        Person contactToMatch = model.getFilteredPersonList().get(contactIndex.getZeroBased());
        Job jobToMatch = model.getFilteredJobList().get(jobIndex.getZeroBased());

        // Ensure the contact has not matched to any job yet
        assert !contactToMatch.isMatchPresent();

        String jobIdentifier = jobToMatch.getIdentifier();
        Person matchedContact = new PersonBuilder(contactToMatch).withMatch(jobIdentifier).build();

        model.setPerson(contactToMatch, matchedContact);
    }
}
