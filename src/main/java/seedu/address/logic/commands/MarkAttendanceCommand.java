package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.Person;


/**
 * Marks attendance for a student in a specific event.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markattendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for a student.\n"
            + "Parameters: EVENT_NAME INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " Tutorial 4 1";

    public static final String MESSAGE_SUCCESS = "Marked attendance for %1$s in %2$s";

    private final String eventName;
    private final Index index;

    /**
     * Creates a MarkAttendanceCommand to mark attendance for a student in a specific event.
     */
    public MarkAttendanceCommand(String eventName, Index index) {
        this.eventName = eventName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        AttendanceEvent event = model.getAttendanceEvent(eventName)
                .orElseThrow(() -> new CommandException("Attendance event not found"));

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid student index");
        }

        Person student = lastShownList.get(index.getZeroBased());

        model.markStudentAttendance(eventName, student.getStudentId(), true);

        return new CommandResult(String.format(MESSAGE_SUCCESS, student.getName(), eventName));
    }
}
