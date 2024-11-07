package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;

/**
 * Lists all attendance events.
 */
public class ListEventsCommand extends Command {

    public static final String COMMAND_WORD = "listevents";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all attendance events.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Attendance Events:\n%1$s";
    public static final String MESSAGE_NO_EVENTS = "There are no attendance events to display.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<AttendanceEvent> events = model.getAttendanceEventList();

        if (events.isEmpty()) {
            throw new CommandException(MESSAGE_NO_EVENTS);
        }

        String eventList = events.stream()
                .map(AttendanceEvent::getEventName)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventList));
    }

    @Override
    public boolean equals(Object other) {
        // Since ListEventsCommand has no fields, all instances are equal
        return other instanceof ListEventsCommand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(COMMAND_WORD);
    }
}
