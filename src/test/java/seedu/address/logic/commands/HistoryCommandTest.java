package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
//import static seedu.address.testutil.TypicalPersons.CONTACT_DATE_1;
//import static seedu.address.testutil.TypicalPersons.CONTACT_DATE_2;
//import static seedu.address.testutil.TypicalPersons.CONTACT_DATE_3;
//import static seedu.address.testutil.TypicalPersons.CONTACT_DATE_4;
//import static seedu.address.testutil.TypicalPersons.CONTACT_DATE_5;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code HistoryCommand}.
 */
public class HistoryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

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

    /*
    @Test
    public void execute_personContacted_historyDisplayed() throws CommandException {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON);

        markCommand.execute(model);

        HistoryCommand firstHistoryCommand = new HistoryCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SHOW_HISTORY_SUCCESS,
                Messages.format());

        expectedModel.markAsContacted(personToMark);

        System.out.println("Expected Message: " + expectedMessage);
        System.out.println("Actual Model State: " + model);

        assertCommandSuccess(firstHistoryCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        HistoryCommand historyCommand = new HistoryCommand(outOfBoundIndex);

        assertCommandFailure(historyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*
    @Test
    public void executeHistoryCommand_personHasCallHistory_returnsCorrectHistory() throws CommandException {
        HistoryCommand firstHistoryCommand = new HistoryCommand(INDEX_FIRST_PERSON);

        CommandResult result1 = firstHistoryCommand.execute(model);

        System.out.println("Actual result: " + result1);

        assertTrue(result1.getFeedbackToUser().contains("Call history for Alice Pauline:"));
        assertTrue(result1.getFeedbackToUser().contains(CONTACT_DATE_1.toString()));
        assertTrue(result1.getFeedbackToUser().contains(CONTACT_DATE_2.toString()));
        assertTrue(result1.getFeedbackToUser().contains(CONTACT_DATE_3.toString()));

        HistoryCommand secondHistoryCommand = new HistoryCommand(INDEX_SECOND_PERSON);

        CommandResult result2 = secondHistoryCommand.execute(model);

        assertTrue(result2.getFeedbackToUser().contains("Call history for Benson Meier:"));
        assertTrue(result2.getFeedbackToUser().contains(CONTACT_DATE_4.toString()));
        assertTrue(result2.getFeedbackToUser().contains(CONTACT_DATE_5.toString()));
    }


    @Test
    public void executeHistoryCommand_personHasNoCallHistory_returnsCorrectMessage() throws CommandException {
        Person target = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        HistoryCommand historyCommand = new HistoryCommand(INDEX_THIRD_PERSON);
        CommandResult result = historyCommand.execute(model);
        assertEquals(String.format(Messages.MESSAGE_NO_CALL_HISTORY, target.getName()), result.getFeedbackToUser());
    }
    */

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        HistoryCommand historyCommand = new HistoryCommand(targetIndex);
        String expected = HistoryCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, historyCommand.toString());
    }

}
