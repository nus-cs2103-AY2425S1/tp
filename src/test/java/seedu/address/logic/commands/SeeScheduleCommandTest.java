package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.SameWeekAsDatePredicate;

public class SeeScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(),
            new UserPrefs(), getTypicalMeetings());

    @Test
    public void execute_success() {
        SameWeekAsDatePredicate predicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-11-05"));
        SeeScheduleCommand seeScheduleCommand = new SeeScheduleCommand(predicate);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(Messages.MESSAGE_SCHEDULE_LISTED_OVERVIEW,
                "Sun 03-11-2024", "Sat 09-11-2024"));
        assertCommandSuccess(seeScheduleCommand, model, expectedCommandResult, model);
        assertEquals(2, model.getWeeklySchedule().filtered(predicate).size());
    }

    @Test
    public void equals() {
        SameWeekAsDatePredicate firstPredicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-11-05"));
        SameWeekAsDatePredicate secondPredicate = new SameWeekAsDatePredicate(LocalDate
                .parse("2024-11-06"));
        SeeScheduleCommand seeFirstCommand = new SeeScheduleCommand(firstPredicate);
        SeeScheduleCommand seeSecondCommand = new SeeScheduleCommand(secondPredicate);
        // same object -> returns true
        assertEquals(seeFirstCommand, seeFirstCommand);
        // same values -> returns true
        SeeScheduleCommand seeFirstCommandCopy = new SeeScheduleCommand(firstPredicate);
        assertEquals(seeFirstCommand, seeFirstCommandCopy);
        // different types -> returns false
        assertNotEquals(1, seeFirstCommand);
        // null -> returns false
        assertNotEquals(null, seeFirstCommand);
        // different person -> returns false
        assertNotEquals(seeFirstCommand, seeSecondCommand);
        // completely different command -> returns false
        assertNotEquals(seeFirstCommand, new ListCommand());
    }
}
