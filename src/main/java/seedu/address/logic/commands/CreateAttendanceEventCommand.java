package seedu.address.logic.commands;

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

    private final String eventName;

    public CreateAttendanceEventCommand(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.getAttendanceEvent(eventName).isPresent()) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        AttendanceEvent event = new AttendanceEvent(eventName);
        model.addAttendanceEvent(event);

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventName));
    }
}
