package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EVENT_NOT_FOUND;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_INVALID_ATTENDANCE_INDEX;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.Person;

/**
 * Unmarks attendance for one or more students in a specific event.
 */
public class UnmarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "unmark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks attendance for students.\n"
            + "Parameters: e/EVENT_NAME i/INDEX [i/INDEX]...\n"
            + "Example: " + COMMAND_WORD + " e/Tutorial 4 i/1 i/2 i/3";

    public static final String MESSAGE_SUCCESS = "Unmarked attendance for %1$s in %2$s";

    private final String eventName;
    private final List<Index> indices;

    /**
     * Creates an UnmarkAttendanceCommand to unmark attendance for student(s) in a specific event.
     */
    public UnmarkAttendanceCommand(String eventName, List<Index> indices) {
        this.eventName = eventName;
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<AttendanceEvent> optionalEvent = model.getAttendanceEvent(eventName);
        if (!optionalEvent.isPresent()) {
            throw new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, eventName));
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        Set<Integer> uniqueIndexValues = new HashSet<>();
        int studentsUnmarked = 0;

        for (Index index : indices) {
            int zeroBased = index.getZeroBased();
            if (!uniqueIndexValues.add(zeroBased)) {
                continue; // Skip duplicate indices
            }
            if (zeroBased >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_ATTENDANCE_INDEX);
            }
            Person student = lastShownList.get(zeroBased);
            model.markStudentAttendance(eventName, student.getStudentId(), false); // Set attendance to false
            studentsUnmarked++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentsUnmarked, eventName));
    }
}
