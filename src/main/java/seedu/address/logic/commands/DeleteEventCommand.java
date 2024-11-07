package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_EVENT_NOT_FOUND;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;

/**
 * Deletes a specific attendance event.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteevent";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the attendance event with the given name.\n"
            + "Parameters: e/EVENT_NAME\n"
            + "Example: " + COMMAND_WORD + " e/Tutorial 4";

    public static final String MESSAGE_SUCCESS = "Deleted attendance event: %1$s";

    private final List<String> eventNames;

    /**
     * Creates a DeleteEventCommand to delete the specified attendance event.
     */
    public DeleteEventCommand(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    /**
     * Executes the command to delete the specified attendance event.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the success of the command.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        for (String eventName : eventNames) {
            if (!model.getAttendanceEvent(eventName).isPresent()) {
                throw new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, eventName));
            }
        }

        for (String eventName : eventNames) {
            AttendanceEvent eventToDelete = model.getAttendanceEvent(eventName).get();
            model.deleteAttendanceEvent(eventToDelete);
        }

        return new CommandResult(String.format("Deleted attendance events: %s",
                String.join(", ", eventNames)));
    }

    @Override
    public boolean equals(Object other) {
        // Check for reference equality
        if (this == other) {
            return true;
        }

        // Check for instance type
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        // Cast and compare event names
        DeleteEventCommand otherCommand = (DeleteEventCommand) other;
        return Objects.equals(eventNames, otherCommand.eventNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventNames);
    }

}
