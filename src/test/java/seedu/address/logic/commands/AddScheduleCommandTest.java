package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddScheduleCommand.MESSAGE_DUPLICATE_SCHEDULE;
import static seedu.address.logic.commands.AddScheduleCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Meeting;

public class AddScheduleCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetings());

    @Test
    public void execute_validSchedule_success() throws Exception {
        // Define valid contact indexes, event name, date, and time
        List<Index> contactIndexes = Arrays.asList(Index.fromZeroBased(0), Index.fromZeroBased(1));
        String eventName = "Dinner";
        LocalDate eventDate = LocalDate.of(2024, 10, 10);
        LocalTime eventTime = LocalTime.of(18, 0);

        // Create AddScheduleCommand with valid parameters
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(contactIndexes, eventName, eventDate, eventTime);

        // Execute the command
        CommandResult result = addScheduleCommand.execute(model);

        // Expected success message
        String expectedMessage = String.format(MESSAGE_SUCCESS, eventName + " on " + eventDate + " at " + eventTime);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateSchedule_throwsCommandException() throws Exception {
        // Define valid contact indexes, event name, date, and time
        List<Index> contactIndexes = Collections.singletonList(Index.fromZeroBased(0));
        String eventName = "Workshop";
        LocalDate eventDate = LocalDate.of(2024, 12, 5);
        LocalTime eventTime = LocalTime.of(10, 0);

        // Add a pre-existing meeting that will conflict with the new one
        Meeting existingMeeting = new Meeting(
                Arrays.asList(model.getFilteredPersonList().get(0).getUid()),
                eventName, eventDate, eventTime);
        model.addMeeting(existingMeeting);

        // Create AddScheduleCommand that attempts to add the same meeting
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(contactIndexes, eventName, eventDate, eventTime);

        // Verify that CommandException is thrown due to duplicate schedule conflict
        Exception exception = assertThrows(CommandException.class, () -> addScheduleCommand.execute(model));
        assertEquals(MESSAGE_DUPLICATE_SCHEDULE, exception.getMessage());
    }

    @Test
    public void execute_singleContact_success() throws Exception {
        List<Index> contactIndexes = Collections.singletonList(Index.fromZeroBased(0));
        String eventName = "One-on-One Meeting";
        LocalDate eventDate = LocalDate.of(2024, 11, 15);
        LocalTime eventTime = LocalTime.of(10, 0);

        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(contactIndexes, eventName, eventDate, eventTime);

        CommandResult result = addScheduleCommand.execute(model);

        String expectedMessage = String.format(MESSAGE_SUCCESS, eventName + " on " + eventDate + " at " + eventTime);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleContactsIncludingFirstAndLast_success() throws Exception {
        List<Index> contactIndexes = Arrays.asList(Index.fromZeroBased(0),
                Index.fromZeroBased(model.getFilteredPersonList().size() - 1));
        String eventName = "Team Meeting";
        LocalDate eventDate = LocalDate.of(2024, 11, 20);
        LocalTime eventTime = LocalTime.of(14, 0);

        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(contactIndexes, eventName, eventDate, eventTime);

        CommandResult result = addScheduleCommand.execute(model);

        String expectedMessage = String.format(MESSAGE_SUCCESS, eventName + " on " + eventDate + " at " + eventTime);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }
}
