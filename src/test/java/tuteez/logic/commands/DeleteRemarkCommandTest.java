package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_REMARK;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.model.AddressBook;
import tuteez.model.Model;
import tuteez.model.ModelManager;
import tuteez.model.UserPrefs;
import tuteez.model.person.Person;
import tuteez.model.remark.Remark;
import tuteez.model.remark.RemarkList;
import tuteez.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteRemarkCommand}.
 */
public class DeleteRemarkCommandTest {

    private static final RemarkList VALID_REMARKLIST = new RemarkList(
            Arrays.asList(new Remark("First Remark"), new Remark("Second Remark")));

    private static final RemarkList UPDATED_REMARKLIST = new RemarkList(
            Arrays.asList(new Remark("Second Remark")));

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDeleteRemark = new PersonBuilder(originalPerson).withRemarks(VALID_REMARKLIST).build();

        model.setPerson(originalPerson, personToDeleteRemark);

        DeleteRemarkCommand deleteRemarkCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_REMARK);

        Person updatedPerson = new PersonBuilder(personToDeleteRemark).withRemarks(UPDATED_REMARKLIST).build();

        String expectedMessage = String.format("Deleted remark at index %1$s from Person %2$s",
                1, 1);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToDeleteRemark, updatedPerson);

        assertCommandSuccess(deleteRemarkCommand, model, expectedMessage, expectedModel);

        Person personAfterCommand = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(1 == personAfterCommand.getRemarkList().getSize());
    }

    @Test
    public void execute_deleteRemarkFilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDeleteRemark = new PersonBuilder(originalPerson).withRemarks(VALID_REMARKLIST).build();

        model.setPerson(originalPerson, personToDeleteRemark);

        DeleteRemarkCommand deleteRemarkCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_REMARK);

        Person updatedPerson = new PersonBuilder(personToDeleteRemark).withRemarks(UPDATED_REMARKLIST).build();

        String expectedMessage = String.format("Deleted remark at index %1$s from Person %2$s",
                1, 1);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToDeleteRemark, updatedPerson);

        assertCommandSuccess(deleteRemarkCommand, model, expectedMessage, expectedModel);

        Person personAfterCommand = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(1 == personAfterCommand.getRemarkList().getSize());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteRemarkCommand deleteRemarkCommand = new DeleteRemarkCommand(outOfBoundIndex,
                Index.fromOneBased(1));

        assertCommandFailure(deleteRemarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteRemarkCommand deleteRemarkCommand = new DeleteRemarkCommand(outOfBoundIndex,
                Index.fromOneBased(1));

        assertCommandFailure(deleteRemarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRemarkIndex_failure() {
        Person personToDeleteRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int invalidRemarkIndex = personToDeleteRemark.getRemarkList().getRemarks().size() + 1; // Out of bounds

        DeleteRemarkCommand deleteRemarkCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(invalidRemarkIndex));

        assertCommandFailure(deleteRemarkCommand, model, Messages.MESSAGE_INVALID_REMARK_INDEX);
    }

    @Test
    public void execute_deleteRemark_updatesLastViewedPerson() throws Exception {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemarkList updatedRemarkList = new RemarkList(Arrays.asList(new Remark("First Remark"), new Remark("Second Remark")));
        Person personWithRemarks = new PersonBuilder(personToUpdate).withRemarks(updatedRemarkList).build();
        model.setPerson(personToUpdate, personWithRemarks);

        DeleteRemarkCommand deleteRemarkCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON, Index.fromZeroBased(0));

        RemarkList expectedRemarkList = new RemarkList(Arrays.asList(new Remark("Second Remark")));
        Person expectedPerson = new PersonBuilder(personToUpdate).withRemarks(expectedRemarkList).build();

        deleteRemarkCommand.execute(model);

        assertTrue(model.getLastViewedPerson().get().isPresent(), "Expected lastViewedPerson to be set");
        assertTrue(model.getLastViewedPerson().get().get().equals(expectedPerson),
                "Expected lastViewedPerson to match the person with the updated remark list");
    }

    @Test
    public void equals() {
        DeleteRemarkCommand deleteFirstRemarkCommand = new DeleteRemarkCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(1));
        DeleteRemarkCommand deleteSecondRemarkCommand = new DeleteRemarkCommand(INDEX_SECOND_PERSON,
                Index.fromOneBased(1));

        // same object -> returns true
        assertTrue(deleteFirstRemarkCommand.equals(deleteFirstRemarkCommand));

        // same values -> returns true
        DeleteRemarkCommand deleteFirstRemarkCommandCopy = new DeleteRemarkCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(1));
        assertTrue(deleteFirstRemarkCommand.equals(deleteFirstRemarkCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstRemarkCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(deleteFirstRemarkCommand.equals(null));

        // different person index -> returns false
        assertFalse(deleteFirstRemarkCommand.equals(deleteSecondRemarkCommand));
    }
}
