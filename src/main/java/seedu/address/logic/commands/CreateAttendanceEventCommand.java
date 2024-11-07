package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;

/**
 * Creates a new attendance event.
 */
public class CreateAttendanceEventCommand extends Command {
    public static final String COMMAND_WORD = "createattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new attendance event.\n"
            + "Parameters: e/EVENT_NAME\n"
            + "Example: " + COMMAND_WORD + " e/Tutorial 4";

    public static final String MESSAGE_SUCCESS = "Created attendance event: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This attendance event already exists.";

    private final List<String> eventNames;

    public CreateAttendanceEventCommand(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    /**
     * Executes the command to create a new attendance event.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the success of the command.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        for (String eventName : eventNames) {
            if (model.getAttendanceEvent(eventName).isPresent()) {
                throw new CommandException(String.format("Attendance event '%s' already exists.", eventName));
            }
        }

        for (String eventName : eventNames) {
            AttendanceEvent event = new AttendanceEvent(eventName);
            model.addAttendanceEvent(event);
        }

        return new CommandResult(String.format("Created attendance events: %s",
                String.join(", ", eventNames)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CreateAttendanceEventCommand)) {
            return false;
        }
        CreateAttendanceEventCommand otherCommand = (CreateAttendanceEventCommand) other;
        return Objects.equals(eventNames, otherCommand.eventNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventNames);
    }


}
