package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Date;
import seedu.address.model.person.SchedulePredicate;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SchedulePredicate firstPredicate =
              new SchedulePredicate(new Date(LocalDateTime.of(2023, 2, 16, 18, 45)));
        SchedulePredicate secondPredicate =
              new SchedulePredicate(new Date(LocalDateTime.of(2024, 5, 26, 15, 0)));

        ScheduleCommand scheduleFirstCommand = new ScheduleCommand(firstPredicate);
        ScheduleCommand scheduleSecondCommand = new ScheduleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommand));

        // same values -> returns true
        ScheduleCommand scheduleFirstCommandCopy = new ScheduleCommand(firstPredicate);
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(scheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleSecondCommand));
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SchedulePredicate predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 18, 45)));
        ScheduleCommand command = new ScheduleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        SchedulePredicate predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2024,12,3,16,30)));
        ScheduleCommand command = new ScheduleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        SchedulePredicate predicate = new SchedulePredicate(
                new Date(LocalDateTime.of(2023, 2, 16, 18, 45)));
        ScheduleCommand scheduleCommand = new ScheduleCommand(predicate);
        String expected = ScheduleCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, scheduleCommand.toString());
    }

}
