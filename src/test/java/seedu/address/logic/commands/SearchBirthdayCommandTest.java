package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

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

        String invalidDateRange = "2000-04-25 to";
        try {
            new SearchBirthdayCommand(invalidDateRange);
            assertFalse(true, "Expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(SearchBirthdayCommand.MESSAGE_INVALID_DATERANGE_FORMAT, e.getMessage());
        }

        invalidDateRange = "04-25 to 05-25";
        try {
            new SearchBirthdayCommand(invalidDateRange);
            assertFalse(true, "Expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(SearchBirthdayCommand.MESSAGE_INVALID_DATE_FORMAT, e.getMessage());
        }

        // Start date after end date
        String startAfterEndDate = "2000-05-25 to 2000-04-25";
        try {
            new SearchBirthdayCommand(startAfterEndDate);
            assertFalse(true, "Expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(SearchBirthdayCommand.MESSAGE_START_DATE_AFTER_END_DATE, e.getMessage());
        }
    }

    @Test
    public void execute_zeroMatches_noPersonFound() {
        String date = "2000-12-31";
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, "on " + date);
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
        String date = "2000-12-12";
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, "on " + date);
        try {
            SearchBirthdayCommand command = new SearchBirthdayCommand(date);

            // Update expected model to filter only AMY
            expectedModel.updateFilteredPersonList(person -> person.getBirthday() != null
                    && person.getBirthday().toString().equals(date));
            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.singletonList(AMY), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_multipleMatches_multiplePersonsFound() {
        String date = "1990-10-10";
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, "on " + date);
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

    @Test
    public void execute_dateRange_matchesPersonsInRange() {
        Model model = new ModelManager();
        Person alice = new PersonBuilder().withName("Alice").withBirthday("2000-10-10").build();
        model.addPerson(alice);
        Person bob = new PersonBuilder().withName("Bob").withBirthday("2000-11-10").build();
        model.addPerson(bob);
        Person charles = new PersonBuilder().withName("Charles").withBirthday("2000-12-10").build();
        model.addPerson(charles);

        String dateRange = "2000-10-01 to 2000-11-30";
        String expectedMessage = String.format(SearchBirthdayCommand.MESSAGE_SUCCESS, "from 2000-10-01 to 2000-11-30");
        try {
            Model expectedModel = new ModelManager();
            expectedModel.addPerson(alice);
            expectedModel.addPerson(bob);
            expectedModel.addPerson(charles);
            SearchBirthdayCommand command = new SearchBirthdayCommand(dateRange);

            expectedModel.updateFilteredPersonList(person -> {
                if (person.getBirthday() == null || person.getBirthday().value.isEmpty()) {
                    return false;
                }
                String birthdayStr = person.getBirthday().toString();
                LocalDate birthdayDate = LocalDate.parse(birthdayStr);
                LocalDate startDate = LocalDate.parse("2000-10-01");
                LocalDate endDate = LocalDate.parse("2000-11-30");
                return (!birthdayDate.isBefore(startDate) && !birthdayDate.isAfter(endDate));
            });

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Arrays.asList(alice, bob), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
