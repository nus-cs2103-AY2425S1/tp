package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_REMOVE_WEDDING_JOBS_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY_WEDDING;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE_WEDDING;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getAdditionalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model additionalModel = new ModelManager(getAdditionalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Ensure person is not a client in any wedding
        for (Wedding wedding : model.getFilteredWeddingList()) {
            if (wedding.getClient() != null) {
                model.deleteWedding(wedding);
            }
        }

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, null);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, null, null);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                model.getFilteredPersonList().size()));
    }

    @Test
    public void execute_personIsClient_throwsCommandException() {
        // Get a person who is a client
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Wedding wedding = new Wedding(personToDelete.getName(), null, null);
        model.addWedding(wedding);
        personToDelete.setOwnWedding(wedding);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, null);
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_PERSON_IS_CLIENT);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Ensure person is not a client in any wedding
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        for (Wedding wedding : model.getFilteredWeddingList()) {
            if (wedding.getClient() != null && wedding.getClient().getPerson().equals(personToDelete)) {
                model.deleteWedding(wedding);
            }
        }

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, null);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        // Make sure expectedModel has same filter state
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, null, null);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                model.getFilteredPersonList().size()));
    }

    @Test
    public void execute_validKeyword_success() {
        // Ensure ALICE is not a client in any wedding
        for (Wedding wedding : model.getFilteredWeddingList()) {
            if (wedding.getClient() != null && wedding.getClient().getPerson().equals(ALICE)) {
                model.deleteWedding(wedding);
            }
        }

        // unique name
        NameMatchesKeywordPredicate predicate = preparePredicate("Alice");
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = new DeleteCommand(null, predicate, null);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        // Make sure expectedModel has the same filter state
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validKeywordMultipleMatches_success() {
        // keyword matches with multiple persons
        NameMatchesKeywordPredicate predicate = preparePredicate("Carl");
        DeleteCommand deleteCommand = new DeleteCommand(null, predicate, null);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DUPLICATE_HANDLING);

        ModelManager expectedModel = new ModelManager(additionalModel.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(deleteCommand, additionalModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeyword_throwsCommandException() {
        NameMatchesKeywordPredicate predicate = preparePredicate("Alex");
        DeleteCommand deleteCommand = new DeleteCommand(null, predicate, null);

        Model actualModel = model;
        showNoPerson(actualModel);

        assertCommandFailure(deleteCommand, actualModel,
                String.format(DeleteCommand.MESSAGE_DELETE_EMPTY_PERSON_LIST_ERROR));
    }

    @Test
    public void execute_personAssignedToWeddings_removeWeddingJobsSuccess() throws CommandException {
        // Setup a person with assigned wedding jobs
        Person personToDelete = model.getFilteredPersonList().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);
        Wedding elleWeddingCopy = new WeddingBuilder(ELLE_WEDDING).build();
        elleWeddingCopy.setClient(new PersonBuilder(FIONA).build());
        model.addWedding(elleWeddingCopy);

        personToDelete.addWeddingJob(amyWeddingCopy);
        personToDelete.addWeddingJob(elleWeddingCopy);

        Set<Index> weddingIndices = Set.of(Index.fromOneBased(1), Index.fromOneBased(2));
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, weddingIndices);

        Person expectedPerson = new PersonBuilder(personToDelete).build();
        expectedPerson.removeWeddingJob(amyWeddingCopy);
        expectedPerson.removeWeddingJob(elleWeddingCopy);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToDelete, expectedPerson);
        CommandResult result = deleteCommand.execute(model);
        assertEquals(String.format(MESSAGE_REMOVE_WEDDING_JOBS_SUCCESS,
                        Messages.format(expectedPerson),
                        Messages.format(expectedPerson.getWeddingJobs())
                ),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_personNotAssignedToSpecifiedWeddings_throwsCommandException() {
        // Setup a person some unassigned wedding jobs
        Person personToDelete = model.getFilteredPersonList().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);
        Wedding elleWeddingCopy = new WeddingBuilder(ELLE_WEDDING).build();
        elleWeddingCopy.setClient(new PersonBuilder(FIONA).build());
        model.addWedding(elleWeddingCopy);

        personToDelete.addWeddingJob(amyWeddingCopy);

        Set<Index> invalidWeddingIndices = Set.of(Index.fromOneBased(2)); // Non-assigned wedding index
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, invalidWeddingIndices);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_PERSON_NOT_ASSIGNED_WEDDING);
    }

    @Test
    public void execute_emptyWeddingList_throwsCommandException() {
        Set<Index> weddingIndices = Set.of(Index.fromOneBased(1)); // Trying to delete weddings when none exist
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, weddingIndices);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_DELETE_EMPTY_WEDDING_LIST_ERROR);
    }

    @Test
    public void execute_invalidWeddingIndex_throwsCommandException() {
        // Setup a wedding and assign it to a person
        Person personToDelete = model.getFilteredPersonList().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Wedding amyWeddingCopy = new WeddingBuilder(AMY_WEDDING).build();
        amyWeddingCopy.setClient(new PersonBuilder(BENSON).build());
        model.addWedding(amyWeddingCopy);

        personToDelete.addWeddingJob(amyWeddingCopy);

        Set<Index> invalidWeddingIndices = Set.of(Index.fromOneBased(5)); // Out-of-bound wedding index
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, invalidWeddingIndices);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX,
                model.getFilteredWeddingList().size()));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON, null, null);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON, null, null);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON, null, null);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, null, null);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());

        String targetKeyword = VALID_NAME_AMY;
        NameMatchesKeywordPredicate predicate = preparePredicate(targetKeyword);
        deleteCommand = new DeleteCommand(null, predicate, null);
        expected = DeleteCommand.class.getCanonicalName() + "{targetKeywords=" + predicate.toString() + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameMatchesKeywordPredicate preparePredicate(String userInput) {
        return new NameMatchesKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
