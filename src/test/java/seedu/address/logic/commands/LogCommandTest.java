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
    private final String message = "placeholder log message";
    @Test
    public void execute_addLogUnfilteredList_success() {
        LocalDate logDate = LocalDate.now();
        String logMessage = VALID_LOG_MESSAGE;
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        System.out.println(model.getAddressBook().getPersonList().get(0));
        Person editedPerson = new PersonBuilder(personToEdit).withHistory(logDate, logMessage).build();
        System.out.println(model.getAddressBook().getPersonList().get(0));
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, logDate, logMessage);

        String expectedMessage = String.format(LogCommand.MESSAGE_ADD_HISTORY_SUCCESS, Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
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
        String expectedMessage = String.format(LogCommand.MESSAGE_ADD_HISTORY_SUCCESS, Messages.format(editedPerson));
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
    public void execute_addLogDateBeforeDateOfCreation_failure() {
        // Set up a date that is before the person's date of creation
        LocalDate invalidDate = LocalDate.of(2023, 1, 1); // Assuming person's creation date is later than this date
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, invalidDate, message);

        // Expect a CommandException with the message for date before the date of creation
        String expectedMessage = String.format(Messages.MESSAGE_BEFORE_DATE_OF_CREATION, invalidDate,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getDateOfCreation());

        assertCommandFailure(logCommand, model, expectedMessage);
    }

    @Test
    public void execute_addLogFutureDate_failure() {
        // Set up a date that is in the future
        LocalDate futureDate = LocalDate.now().plusDays(1); // Any date after today
        LogCommand logCommand = new LogCommand(INDEX_FIRST_PERSON, futureDate, message);
        // Expect a CommandException with the message for a date in the future
        String expectedMessage = String.format(Messages.MESSAGE_AFTER_TODAY, futureDate);

        assertCommandFailure(logCommand, model, expectedMessage);
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
