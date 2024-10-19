package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.showPersonAtIndex;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.person.Person;

public class ListOrderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs()).createCopy();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person selectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ListOrderCommand listOrderCommand = new ListOrderCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ListOrderCommand.MESSAGE_LIST_ORDER_SUCCESS,
                selectedPerson.getName().fullName);

        expectedModel.updateSelectedPerson(
                expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        assertCommandSuccess(listOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ListOrderCommand listOrderCommand = new ListOrderCommand(outOfBoundIndex);

        assertCommandFailure(listOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Person selectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ListOrderCommand listOrderCommand = new ListOrderCommand(INDEX_FIRST_PERSON);

        expectedModel.updateSelectedPerson(
                expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        String expectedMessage = String.format(ListOrderCommand.MESSAGE_LIST_ORDER_SUCCESS,
                selectedPerson.getName().fullName);

        assertCommandSuccess(listOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        assertTrue(outOfBoundIndex.getZeroBased() < expectedModel.getAddressBook().getPersonList().size());

        ListOrderCommand listOrderCommand = new ListOrderCommand(outOfBoundIndex);

        assertCommandFailure(listOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListOrderCommand listOrderOfFirstPersonCommand = new ListOrderCommand(INDEX_FIRST_PERSON);
        ListOrderCommand listOrderOfSecondPersonCommand = new ListOrderCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(listOrderOfFirstPersonCommand.equals(listOrderOfFirstPersonCommand));

        // same values -> returns true
        ListOrderCommand listOrderOfFirstPersonCommandCopy = new ListOrderCommand(INDEX_FIRST_PERSON);
        assertTrue(listOrderOfFirstPersonCommand.equals(listOrderOfFirstPersonCommandCopy));

        // different types -> returns false
        assertFalse(listOrderOfFirstPersonCommand.equals(1));

        // null -> returns false
        assertFalse(listOrderOfFirstPersonCommand.equals(null));

        // different person -> returns false
        assertFalse(listOrderOfFirstPersonCommand.equals(listOrderOfSecondPersonCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ListOrderCommand listOrderCommand = new ListOrderCommand(targetIndex);
        String expected = ListOrderCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, listOrderCommand.toString());
    }

}
