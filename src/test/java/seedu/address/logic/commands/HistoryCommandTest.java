package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contactdate.ContactDateList;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code HistoryCommand}.
 */
public class HistoryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        HistoryCommand firstHistoryCommand = new HistoryCommand(INDEX_FIRST_PERSON);
        HistoryCommand secondHistoryCommand = new HistoryCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstHistoryCommand.equals(firstHistoryCommand));

        // same values -> returns true
        HistoryCommand firstHistoryCommandCopy = new HistoryCommand(INDEX_FIRST_PERSON);
        assertTrue(firstHistoryCommand.equals(firstHistoryCommandCopy));

        // different types -> returns false
        assertFalse(firstHistoryCommand.equals(1));

        // null -> returns false
        assertFalse(firstHistoryCommand.equals(null));

        // different command -> returns false
        assertFalse(firstHistoryCommand.equals(secondHistoryCommand));
    }

    // Test for HistoryCommand for Index
    @Test
    public void equalsIndexCommandTest() {
        HistoryCommand historyFirstCommand = new HistoryCommand(INDEX_FIRST_PERSON);
        HistoryCommand historySecondCommand = new HistoryCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(historyFirstCommand.equals(historyFirstCommand));

        // same values -> returns true
        HistoryCommand historyFirstCommandCopy = new HistoryCommand(INDEX_FIRST_PERSON);
        assertTrue(historyFirstCommand.equals(historyFirstCommandCopy));

        // different types -> returns false
        assertFalse(historyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(historyFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(historyFirstCommand.equals(historySecondCommand));
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        HistoryCommand historyCommand = new HistoryCommand(outOfBoundIndex);

        assertCommandFailure(historyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        HistoryCommand historyCommand = new HistoryCommand(outOfBoundIndex);

        assertCommandFailure(historyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Person personToViewHistory = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HistoryCommand historyCommand = new HistoryCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SHOW_HISTORY_SUCCESS,
                personToViewHistory.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ContactDateList expectedCallHistory = expectedModel.getCallHistory(personToViewHistory);
        CommandResult result = historyCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedCallHistory, model.getDisplayedCallHistory());
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToViewHistory = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HistoryCommand historyCommand = new HistoryCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SHOW_HISTORY_SUCCESS,
                personToViewHistory.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ContactDateList expectedCallHistory = expectedModel.getCallHistory(personToViewHistory);
        CommandResult result = historyCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedCallHistory, model.getDisplayedCallHistory());
    }

    // Test for HistoryCommand for Nric
    @Test
    public void equalsNricCommandTest() {
        HistoryCommand historyFirstNric = new HistoryCommand(new Nric(VALID_NRIC_AMY));
        HistoryCommand historySecondNric = new HistoryCommand(new Nric(VALID_NRIC_BOB));

        // same object -> returns true
        assertTrue(historyFirstNric.equals(historyFirstNric));

        // same values -> returns true
        HistoryCommand historyFirstNricCopy = new HistoryCommand(new Nric(VALID_NRIC_AMY));
        assertTrue(historyFirstNric.equals(historyFirstNricCopy));

        // different types -> returns false
        assertFalse(historyFirstNric.equals(1));

        // null -> returns false
        assertFalse(historyFirstNric.equals(null));

        // different person -> returns false
        assertFalse(historyFirstNric.equals(historySecondNric));
    }

    @Test
    public void execute_invalidNricFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        HistoryCommand historyCommand = new HistoryCommand(new Nric(VALID_NRIC_BOB));

        assertCommandFailure(historyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
    }

    @Test
    public void execute_invalidNricUnfilteredList_throwsCommandException() {
        HistoryCommand historyCommand = new HistoryCommand(new Nric(VALID_NRIC_BOB));

        assertCommandFailure(historyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
    }

    @Test
    public void execute_validNricFilteredList_success() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToViewHistory = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HistoryCommand historyCommand = new HistoryCommand(personToViewHistory.getNric());

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SHOW_HISTORY_SUCCESS,
                personToViewHistory.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ContactDateList expectedCallHistory = expectedModel.getCallHistory(personToViewHistory);
        CommandResult result = historyCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedCallHistory, model.getDisplayedCallHistory());
    }

    @Test
    public void execute_validNricUnfilteredList_success() throws CommandException {
        Person personToViewHistory = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HistoryCommand historyCommand = new HistoryCommand(personToViewHistory.getNric());

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SHOW_HISTORY_SUCCESS,
                personToViewHistory.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ContactDateList expectedCallHistory = expectedModel.getCallHistory(personToViewHistory);
        CommandResult result = historyCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedCallHistory, model.getDisplayedCallHistory());
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        HistoryCommand historyCommandWithIndex = new HistoryCommand(targetIndex);
        String expected = HistoryCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", targetNric=null}";
        assertEquals(expected, historyCommandWithIndex.toString());

        Nric targetNric = new Nric(VALID_NRIC_AMY);
        HistoryCommand historyCommandWithNric = new HistoryCommand(targetNric);
        String expectedNricString = HistoryCommand.class.getCanonicalName() + "{targetIndex=null, targetNric="
                + targetNric + "}";
        assertEquals(expectedNricString, historyCommandWithNric.toString());
    }

}
