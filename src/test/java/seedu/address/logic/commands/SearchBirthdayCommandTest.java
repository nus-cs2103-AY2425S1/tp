package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchBirthdayCommand}.
 */
public class SearchBirthdayCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SearchBirthdayCommand searchFirstCommand = null;
        SearchBirthdayCommand searchSecondCommand = null;
        try {
            searchFirstCommand = new SearchBirthdayCommand("2000-04-25");
            searchSecondCommand = new SearchBirthdayCommand("2001-05-30");
        } catch (CommandException e) {
            e.printStackTrace();
        }

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        try {
            SearchBirthdayCommand searchFirstCommandCopy = new SearchBirthdayCommand("2000-04-25");
            assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));
        } catch (CommandException e) {
            e.printStackTrace();
        }

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void constructor_invalidDateFormat_throwsCommandException() {
        // Test with invalid date format
        String invalidDate = "04-25";
        try {
            new SearchBirthdayCommand(invalidDate);
        } catch (CommandException e) {
            assertEquals(SearchBirthdayCommand.MESSAGE_INVALID_DATE_FORMAT, e.getMessage());
        }
    }

    @Test
    public void execute_zeroMatches_noPersonFound() {
        String date = "2000-12-31"; // Date that no person has in the typical address book
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, date);
        try {
            SearchBirthdayCommand command = new SearchBirthdayCommand(date);
            expectedModel.updateFilteredPersonList(person -> false); // No one matches

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_oneMatch_personFound() {
        String date = "2000-12-12"; // Assume AMY has a birthday on December 12, 2000
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, date);
        try {
            SearchBirthdayCommand command = new SearchBirthdayCommand(date);

            // Update expected model to filter only AMY
            expectedModel.updateFilteredPersonList(person -> person.getBirthday() != null
                    && person.getBirthday().toString().equals(date));
            System.out.println("Filtered list: " + model.getFilteredPersonList());
            System.out.println("AMY's birthday: " + BOB.getBirthday());
            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.singletonList(AMY), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_multipleMatches_multiplePersonsFound() {
        String date = "1990-10-10"; // Assume both ALICE and BENSON have birthdays on December 12, 2000
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, date);
        try {
            SearchBirthdayCommand command = new SearchBirthdayCommand(date);

            // Update expected model to filter both AMY and BOB
            expectedModel.updateFilteredPersonList(person -> person.getBirthday() != null
                    && person.getBirthday().toString().equals(date));

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, BOB),
                    model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_invalidDateFormat_throwsCommandException() {
        String invalidDate = "04-25"; // Incorrect format

        // Check if the command throws the expected exception
        try {
            new SearchBirthdayCommand(invalidDate);
        } catch (CommandException e) {
            assertEquals(SearchBirthdayCommand.MESSAGE_INVALID_DATE_FORMAT, e.getMessage());
        }
    }
}
