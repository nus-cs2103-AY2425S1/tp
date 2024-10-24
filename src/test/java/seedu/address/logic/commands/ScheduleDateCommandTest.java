package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;
import static seedu.address.logic.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentContainsDatePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ScheduleDateCommand}.
 */
public class ScheduleDateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AppointmentContainsDatePredicate firstPredicate = preparePredicate("24-10-2024");
        AppointmentContainsDatePredicate secondPredicate = preparePredicate("25-12-2024");

        ScheduleDateCommand scheduleFirstCommand = new ScheduleDateCommand(firstPredicate);
        ScheduleDateCommand scheduleSecondCommand = new ScheduleDateCommand(secondPredicate);

        // same object -> returns true
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommand));

        // same values -> returns true
        ScheduleDateCommand scheduleFirstCommandCopy = new ScheduleDateCommand(firstPredicate);
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(scheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleFirstCommand.equals(null));

        // different keywords -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleSecondCommand));
    }

    @Test
    public void execute_noAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 0, "29 February 2024");
        AppointmentContainsDatePredicate predicate = preparePredicate("29-02-2024");
        ScheduleDateCommand command = new ScheduleDateCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 2, "23 October 2024");
        AppointmentContainsDatePredicate predicate = preparePredicate("23-10-2024");
        ScheduleDateCommand command = new ScheduleDateCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, GEORGE), expectedModel.getFilteredAppointmentList());
    }

    @Test
    public void toStringMethod() {
        AppointmentContainsDatePredicate predicate = preparePredicate("13-03-2002");
        ScheduleDateCommand scheduleDateCommand = new ScheduleDateCommand(predicate);
        String expected = ScheduleDateCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, scheduleDateCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private AppointmentContainsDatePredicate preparePredicate(String userInput) {
        return new AppointmentContainsDatePredicate(LocalDate.parse(userInput, DATE_FORMATTER));
    }
}
