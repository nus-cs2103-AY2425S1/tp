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
 * Contains integration tests (interaction with the Model) and unit tests for MatchCommand.
 */
public class MatchCommandTest {

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
        assertThrows(NullPointerException.class, () -> new MatchCommand(null, null));
        assertThrows(NullPointerException.class, () -> new MatchCommand(null, validIndex));
        assertThrows(NullPointerException.class, () -> new MatchCommand(validIndex, null));
    }

    @Test
    public void execute_validContactAndJob_matchSuccessful() throws Exception {
        Index contactIndex = INDEX_FIRST_PERSON;
        Index jobIndex = INDEX_FIRST_JOB;

        MatchCommand matchCommand = new MatchCommand(contactIndex, jobIndex);

        Person contactToMatch = model.getFilteredPersonList().get(contactIndex.getZeroBased());
        Job jobToMatch = model.getFilteredJobList().get(jobIndex.getZeroBased());

        // Ensure the contact has not matched to any job yet
        assert !contactToMatch.isMatchPresent();

        String jobIdentifier = jobToMatch.getIdentifier();
        Person matchedContact = new PersonBuilder(contactToMatch).withMatch(jobIdentifier).build();

        String expectedMessage = String.format(MatchCommand.MESSAGE_MATCH_SUCCESS, Messages.format(matchedContact),
                Messages.format(jobToMatch));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        expectedModel.setPerson(model.getFilteredPersonList().get(contactIndex.getZeroBased()), matchedContact);

        assertCommandSuccess(matchCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_contactIndexOutOfRange_failure() {
        MatchCommand matchCommand = new MatchCommand(INDEX_OUT_OF_BOUNDS, INDEX_FIRST_JOB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_jobIndexOutOfRange_failure() {
        MatchCommand matchCommand = new MatchCommand(INDEX_FIRST_PERSON, INDEX_OUT_OF_BOUNDS);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyModel_failure() {
        MatchCommand matchCommand = new MatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandFailure(matchCommand, emptyModel, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_contactAndJobAlreadyMatched_failure() {
        setModelAfterMatching(model, INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        MatchCommand matchCommand = new MatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        assertCommandFailure(matchCommand, model, MatchCommand.MESSAGE_ALREADY_MATCHED);
    }

    @Test
    public void execute_contactHasOtherMatches_failure() {
        setModelAfterMatching(model, INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        MatchCommand matchCommand = new MatchCommand(INDEX_FIRST_PERSON, INDEX_SECOND_JOB);
        assertCommandFailure(matchCommand, model, MatchCommand.MESSAGE_HAS_OTHER_MATCHES);
    }

    @Test
    public void equals() {
        Index firstContactIndex = INDEX_FIRST_PERSON;
        Index firstJobIndex = INDEX_FIRST_JOB;

        MatchCommand matchCommand = new MatchCommand(firstContactIndex, firstJobIndex);

        // same object -> returns true
        assertTrue(matchCommand.equals(matchCommand));

        // same values -> returns true
        MatchCommand matchCommandCopy = new MatchCommand(firstContactIndex, firstJobIndex);
        assertTrue(matchCommand.equals(matchCommandCopy));

        // different types -> returns false
        assertFalse(matchCommand.equals(1));

        // null -> returns false
        assertFalse(matchCommand.equals(null));

        // different match -> returns false
        MatchCommand differentJobMatchCommand = new MatchCommand(firstContactIndex, INDEX_SECOND_JOB);
        assertFalse(matchCommand.equals(differentJobMatchCommand));

        MatchCommand differentContactMatchCommand = new MatchCommand(INDEX_SECOND_PERSON, firstJobIndex);
        assertFalse(matchCommand.equals(differentContactMatchCommand));
    }

    @Test
    public void toStringMethod() {
        MatchCommand matchCommand = new MatchCommand(INDEX_FIRST_PERSON, INDEX_FIRST_JOB);
        String expected = MatchCommand.class.getCanonicalName() + "{contactIndex=" + INDEX_FIRST_PERSON + ", jobIndex="
                + INDEX_FIRST_JOB + "}";
        assertEquals(expected, matchCommand.toString());
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
