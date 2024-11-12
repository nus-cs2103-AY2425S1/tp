package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EVENT_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_NO_STUDENTS_FOUND;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.Person;

/**
 * Lists students based on attendance in a specific event.
 */
public class ListAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "listattn";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists students based on attendance.\n"
            + "Parameters: "
            + "e/EVENT_NAME "
            + "s/STATUS (present/absent)\n"
            + "Example: " + COMMAND_WORD + " e/Tutorial 4 s/present";
    public static final String MESSAGE_SUCCESS = "Listed %1$d %2$s students for %3$s";

    private final String eventName;
    private final boolean isPresent;

    /**
     * Creates a ListAttendanceCommand to list students based on attendance in a specific event.
     *
     * @param eventName Name of the event.
     * @param isPresent Whether to list present or absent students.
     */
    public ListAttendanceCommand(String eventName, boolean isPresent) {
        this.eventName = eventName;
        this.isPresent = isPresent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<AttendanceEvent> optionalEvent = model.getAttendanceEvent(eventName);
        if (!optionalEvent.isPresent()) {
            throw new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, eventName));
        }

        AttendanceEvent event = optionalEvent.get();

        List<Person> students = model.getStudentsByAttendance(eventName, isPresent);

        if (students.isEmpty()) {
            throw new CommandException(MESSAGE_NO_STUDENTS_FOUND);
        }

        // Update the filtered person list in the model to show the students
        Predicate<Person> attendancePredicate = students::contains;
        model.updateFilteredPersonList(attendancePredicate);

        String status = isPresent ? "present" : "absent";
        String resultMessage = String.format(MESSAGE_SUCCESS, students.size(), status, eventName);

        return new CommandResult(resultMessage);
    }
}
