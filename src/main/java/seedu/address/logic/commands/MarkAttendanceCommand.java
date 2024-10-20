package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.Person;


/**
 * Marks attendance for one or more students in a specific event.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markattendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for students.\n"
            + "Parameters: e/EVENT_NAME i/INDEX [i/INDEX]...\n"
            + "Example: " + COMMAND_WORD + " e/Tutorial 4 i/1 i/2 i/3";

    public static final String MESSAGE_SUCCESS = "Marked attendance for %1$s in %2$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Attendance event not found.";

    private final String eventName;
    private final List<Index> indices;

    /**
     * Creates a MarkAttendanceCommand to mark attendance for student(s) in a specific event.
     */
    public MarkAttendanceCommand(String eventName, List<Index> indices) {
        this.eventName = eventName;
        this.indices = indices;
    }

    //    @Override
    //    public CommandResult execute(Model model) throws CommandException {
    //        AttendanceEvent event = model.getAttendanceEvent(eventName)
    //                .orElseThrow(() -> new CommandException("Attendance event not found"));
    //
    //        List<Person> lastShownList = model.getFilteredPersonList();
    //
    //        if (index.getZeroBased() >= lastShownList.size()) {
    //            throw new CommandException("Invalid student index");
    //        }
    //
    //        Person student = lastShownList.get(index.getZeroBased());
    //
    //        model.markStudentAttendance(eventName, student.getStudentId(), true);
    //
    //        return new CommandResult(String.format(MESSAGE_SUCCESS, student.getName(), eventName));
    //    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<AttendanceEvent> optionalEvent = model.getAttendanceEvent(eventName);
        if (!optionalEvent.isPresent()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        AttendanceEvent event = optionalEvent.get();


        //        if (!model.hasAttendanceEvent(eventName)) {
        //            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        //        }

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person student = lastShownList.get(index.getZeroBased());
            model.markStudentAttendance(eventName, student.getStudentId(), true);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, indices.size(), eventName));
    }
}
