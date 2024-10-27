package seedu.address.logic.commands.eventcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.ANIME;
import static seedu.address.testutil.TypicalEvents.BARBEQUE;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.DINNER;
import static seedu.address.testutil.TypicalEvents.EXHIBITION;
import static seedu.address.testutil.TypicalEvents.FASHION_SHOW;
import static seedu.address.testutil.TypicalEvents.GALA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.EventInSchedulePredicate;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EventInSchedulePredicate firstPredicate = new EventInSchedulePredicate(1);
        EventInSchedulePredicate secondPredicate = new EventInSchedulePredicate(2);
        EventInSchedulePredicate thirdPredicate = new EventInSchedulePredicate(new DateTime("2024-10-15 14:30"));
        EventInSchedulePredicate fourthPredicate = new EventInSchedulePredicate(new DateTime("2024-10-16 15:30"));

        ScheduleCommand scheduleFirstCommand = new ScheduleCommand(firstPredicate);
        ScheduleCommand scheduleSecondCommand = new ScheduleCommand(secondPredicate);
        ScheduleCommand scheduleThirdCommand = new ScheduleCommand(thirdPredicate);
        ScheduleCommand scheduleFourthCommand = new ScheduleCommand(fourthPredicate);

        // same object -> returns true
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommand));
        assertTrue(scheduleThirdCommand.equals(scheduleThirdCommand));

        // same values -> returns true
        ScheduleCommand scheduleFirstCommandCopy = new ScheduleCommand(firstPredicate);
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommandCopy));
        ScheduleCommand scheduleThirdCommandCopy = new ScheduleCommand(thirdPredicate);
        assertTrue(scheduleThirdCommand.equals(scheduleThirdCommandCopy));

        // different types -> returns false
        assertFalse(scheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleFirstCommand.equals(null));

        // different value -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleSecondCommand));
        assertFalse(scheduleFirstCommand.equals(scheduleThirdCommand));
        assertFalse(scheduleThirdCommand.equals(scheduleFourthCommand));
    }

    @Test
    public void execute_zero_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventInSchedulePredicate predicate = new EventInSchedulePredicate(0);
        ScheduleCommand command = new ScheduleCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    //    @Test
    //    public void execute_year_multipleEventsFound() {
    //        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 7);
    //        EventInSchedulePredicate predicate = new EventInSchedulePredicate(365);
    //        ScheduleCommand command = new ScheduleCommand(predicate);
    //        expectedModel.updateFilteredEventList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Arrays.asList(ANIME, BARBEQUE, CONCERT, DINNER, EXHIBITION, FASHION_SHOW, GALA),
    //                model.getFilteredEventList());
    //    }

    @Test
    public void toStringMethod() {
        EventInSchedulePredicate predicate = new EventInSchedulePredicate(0);
        ScheduleCommand scheduleCommand = new ScheduleCommand(predicate);
        String expected = ScheduleCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, scheduleCommand.toString());
    }
}
