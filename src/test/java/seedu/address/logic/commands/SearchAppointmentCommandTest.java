package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        // Invalid date-time range format (missing one date-time)
        String invalidDateTimeRange = "2023-12-31 14:30 to";
        try {
            new SearchAppointmentCommand(invalidDateTimeRange);
            assertFalse(true, "Expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(SearchAppointmentCommand.MESSAGE_INVALID_DATERANGE_FORMAT, e.getMessage());
        }

        // Invalid date-time range format (both date-times invalid)
        invalidDateTimeRange = "12-31-2023 14:30 to 01-01-2024 09:00";
        try {
            new SearchAppointmentCommand(invalidDateTimeRange);
            assertFalse(true, "Expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(SearchAppointmentCommand.MESSAGE_INVALID_DATE_FORMAT, e.getMessage());
        }

        // Start date-time after end date-time
        String startAfterEndDateTime = "2024-01-01 09:00 to 2023-12-31 14:30";
        try {
            new SearchAppointmentCommand(startAfterEndDateTime);
            assertFalse(true, "Expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(SearchAppointmentCommand.MESSAGE_START_DATE_AFTER_END_DATE, e.getMessage());
        }
    }

    @Test
    public void execute_noPersonWithAppointment_noPersonFound() {
        String dateTime = "2025-01-01 14:00"; // Date and time that no person in the typical address book has
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS, "on " + dateTime);

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
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS, "on " + dateTime);

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
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS, "on " + date);

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

    @Test
    public void execute_dateTimeRange_matchesPersonsInRange() {
        Model model = new ModelManager();
        Person alice = new PersonBuilder().withName("Alice").withAppointment("2024-10-10 10:00").build();
        model.addPerson(alice);
        Person bob = new PersonBuilder().withName("Bob").withAppointment("2024-10-10 12:00").build();
        model.addPerson(bob);
        Person charles = new PersonBuilder().withName("Charles").withAppointment("2024-11-20 12:00").build();
        model.addPerson(charles);

        String dateTimeRange = "2024-10-10 00:00 to 2024-10-10 23:59";
        String expectedMessage = String.format(SearchAppointmentCommand.MESSAGE_SUCCESS,
                "from 2024-10-10 00:00 to 2024-10-10 23:59");

        try {
            SearchAppointmentCommand command = new SearchAppointmentCommand(dateTimeRange);

            Model expectedModel = new ModelManager();
            expectedModel.addPerson(alice);
            expectedModel.addPerson(bob);
            expectedModel.addPerson(charles);

            expectedModel.updateFilteredPersonList(person -> {
                if (person.getAppointment() == null || person.getAppointment().value.isEmpty()) {
                    return false;
                }
                String appointmentStr = person.getAppointment().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime appointmentDateTime;
                try {
                    appointmentDateTime = LocalDateTime.parse(appointmentStr, formatter);
                } catch (DateTimeParseException e) {
                    return false;
                }
                LocalDateTime start = LocalDateTime.parse("2024-10-10 10:00", formatter);
                LocalDateTime end = LocalDateTime.parse("2024-10-10 12:00", formatter);
                return (!appointmentDateTime.isBefore(start) && !appointmentDateTime.isAfter(end));
            });

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Arrays.asList(alice, bob), model.getFilteredPersonList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

}
