package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EVENT_NOT_FOUND;

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
 * Marks attendance for one or more students in a specific event.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for students.\n"
            + "Parameters: e/EVENT_NAME i/INDEX [i/INDEX]...\n"
            + "Example: " + COMMAND_WORD + " e/Tutorial 4 i/1 i/2 i/3";

    public static final String MESSAGE_SUCCESS = "Marked attendance for %1$s in %2$s";

    public static final String MESSAGE_INVALID_ATTENDANCE_INDEX = "The student index provided is invalid. \n"
            + "Please ensure that the student index is a positive integer within the range of the list of students \n"
            + " displayed. \n"
            + "To mark multiple instances of attendance, please provide the student index for each student \n"
            + "separately and with a separate 'i/' prefix.";

    private final String eventName;
    private final List<Index> indices;

    /**
     * Creates a MarkAttendanceCommand to mark attendance for student(s) in a specific event.
     */
    public MarkAttendanceCommand(String eventName, List<Index> indices) {
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
        int studentsMarked = 0;

        for (Index index : indices) {
            int zeroBased = index.getZeroBased();
            if (!uniqueIndexValues.add(zeroBased)) {
                continue; // Skip duplicate indices
            }
            if (zeroBased >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_ATTENDANCE_INDEX);
            }
            Person student = lastShownList.get(zeroBased);
            model.markStudentAttendance(eventName, student.getStudentId(), true);
            studentsMarked++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentsMarked, eventName));
    }
}
