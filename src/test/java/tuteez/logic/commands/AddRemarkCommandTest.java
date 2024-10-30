package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddRemarkCommand}.
 */
public class AddRemarkCommandTest {

    private static final RemarkList VALID_REMARKLIST = new RemarkList(
            Arrays.asList(new Remark("First Remark"), new Remark("Second Remark")));

    private static final Remark VALID_REMARK = new Remark("Third Remark");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person personToAddRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personToAddRemark).withRemarks(VALID_REMARKLIST).build();

        AddRemarkCommand addRemarkCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK);

        String expectedMessage = String.format("Added remark to Person %1$s: %2$s",
                INDEX_FIRST_PERSON.getOneBased(), VALID_REMARK);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToAddRemark, updatedPerson);

        assertCommandSuccess(addRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addRemarkFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personInFilteredList).withRemarks(VALID_REMARKLIST).build();

        AddRemarkCommand addRemarkCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK);

        String expectedMessage = String.format("Added remark to Person %1$s: %2$s",
                INDEX_FIRST_PERSON.getOneBased(), VALID_REMARK.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personInFilteredList, updatedPerson);

        assertCommandSuccess(addRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddRemarkCommand addRemarkCommand = new AddRemarkCommand(outOfBoundIndex, VALID_REMARK);

        assertCommandFailure(addRemarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddRemarkCommand addRemarkCommand = new AddRemarkCommand(outOfBoundIndex, VALID_REMARK);

        assertCommandFailure(addRemarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addRemark_updatesLastViewedPerson() {
        Person personToAddRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personToAddRemark).withRemarks(VALID_REMARKLIST).build();
        AddRemarkCommand addRemarkCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK);

        String expectedMessage = String.format("Added remark to Person %1$s: %2$s",
                INDEX_FIRST_PERSON.getOneBased(), VALID_REMARK);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToAddRemark, updatedPerson);

        assertCommandSuccess(addRemarkCommand, model, expectedMessage, expectedModel);

        assertTrue(model.getLastViewedPerson().get().isPresent(), "Expected lastViewedPerson to be set");
        assertTrue(model.getLastViewedPerson().get().get().equals(updatedPerson));
    }

    @Test
    public void equals() {
        AddRemarkCommand addFirstRemarkCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK);
        AddRemarkCommand addSecondRemarkCommand = new AddRemarkCommand(INDEX_SECOND_PERSON, VALID_REMARK);

        // same object -> returns true
        assertTrue(addFirstRemarkCommand.equals(addFirstRemarkCommand));

        // same values -> returns true
        AddRemarkCommand addFirstRemarkCommandCopy = new AddRemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK);
        assertTrue(addFirstRemarkCommand.equals(addFirstRemarkCommandCopy));

        // different types -> returns false
        assertFalse(addFirstRemarkCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(addFirstRemarkCommand.equals(null));

        // different person index -> returns false
        assertFalse(addFirstRemarkCommand.equals(addSecondRemarkCommand));
    }
}
