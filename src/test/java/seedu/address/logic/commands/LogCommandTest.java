package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LogCommand.
 */
public class LogCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addLogUnfilteredList_success() {
        LocalDate logDate = LocalDate.now();
        String logMessage = VALID_LOG_MESSAGE;
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, logDate, logMessage);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withHistory(logDate, logMessage).build();

        String expectedMessage = String.format(Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        System.out.println("MODEL:" + model.getAddressBook() + "\n");
        System.out.println("EXPECTED MODEL:" + expectedModel.getAddressBook());
        assertCommandSuccess(logCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addLogFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        LocalDate logDate = LocalDate.now();
        String logMessage = VALID_LOG_MESSAGE;
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, logDate, logMessage);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withHistory(logDate, logMessage).build();

        String expectedMessage = String.format(Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(logCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LogCommand logCommand = new LogCommand(outOfBoundIndex, VALID_LOG_MESSAGE);

        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LogCommand logCommand = new LogCommand(outOfBoundIndex, VALID_LOG_MESSAGE);

        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LocalDate date = LocalDate.now();
        LogCommand logFirstCommand = new LogCommand(INDEX_FIRST_PERSON, date, VALID_LOG_MESSAGE);
        LogCommand logSecondCommand = new LogCommand(INDEX_SECOND_PERSON, date, VALID_LOG_MESSAGE);

        // same object -> returns true
        assertTrue(logFirstCommand.equals(logFirstCommand));

        // same values -> returns true
        LogCommand logFirstCommandCopy = new LogCommand(INDEX_FIRST_PERSON, date, VALID_LOG_MESSAGE);
        assertTrue(logFirstCommand.equals(logFirstCommandCopy));

        // different types -> returns false
        assertFalse(logFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(logFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(logFirstCommand.equals(logSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        LogCommand logCommand = new LogCommand(index, VALID_LOG_MESSAGE);
        String expected = LogCommand.class.getCanonicalName() + "{index=" + index + ", date=" + LocalDate.now()
                + ", message=" + VALID_LOG_MESSAGE + "}";
        assertEquals(expected, logCommand.toString());
    }
}
