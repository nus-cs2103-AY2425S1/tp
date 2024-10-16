package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
 * Contains integration tests (interaction with the Model) for {@code SearchAppointmentCommand}.
 */
public class SearchAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws CommandException {
        SearchAppointmentCommand searchFirstCommand = new SearchAppointmentCommand("2023-12-31 14:30");
        SearchAppointmentCommand searchSecondCommand = new SearchAppointmentCommand("2024-01-01 09:00");

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchAppointmentCommand searchFirstCommandCopy = new SearchAppointmentCommand("2023-12-31 14:30");
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different dateTimes -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void constructor_invalidDateTimeFormat_throwsCommandException() {
        // Test with invalid date and time format
        String invalidDateTime = "12-31-2023 14:30";
        try {
            new SearchAppointmentCommand(invalidDateTime);
        } catch (CommandException e) {
            assertEquals("The date format is invalid. Please use yyyy-MM-dd HH:mm format.", e.getMessage());
        }
    }

    @Test
    public void execute_noPersonWithAppointment_noPersonFound() {
        String dateTime = "2025-01-01 14:00"; // Date and time that no person in the typical address book has
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS, dateTime);

        try {
            SearchAppointmentCommand command = new SearchAppointmentCommand(dateTime);
            expectedModel.updateFilteredPersonList(person -> false); // No one matches

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_oneMatch_personFound() {
        String dateTime = "2024-10-10 11:00"; // Assume AMY has an appointment on this date and time
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS, dateTime);

        try {
            SearchAppointmentCommand command = new SearchAppointmentCommand(dateTime);

            // Update the expected model to filter only AMY
            expectedModel.updateFilteredPersonList(person -> person.getAppointment() != null
                    && person.getAppointment().toString().equals(dateTime));

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.singletonList(BOB), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_multipleMatches_multiplePersonsFound() {
        String date = "2024-12-12 10:00"; // Assume both AMY and BOB have appointments on this date
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS, date);

        try {
            SearchAppointmentCommand command = new SearchAppointmentCommand(date);

            expectedModel.updateFilteredPersonList(person -> person.getAppointment() != null
                    && person.getAppointment().toString().equals(date));

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Arrays.asList(FIONA, AMY), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
