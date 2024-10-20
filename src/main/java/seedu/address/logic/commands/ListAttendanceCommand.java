//package seedu.address.logic.commands;
//
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.person.Person;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Lists students based on attendance in a specific event.
// */
//public class ListAttendanceCommand extends Command {
//
//    public static final String COMMAND_WORD = "listAttendance";
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists students based on attendance.\n"
//            + "Parameters: EVENT_NAME [present/absent]\n"
//            + "Example: " + COMMAND_WORD + " Tutorial4 present";
//
//    public static final String MESSAGE_SUCCESS = "Listed %1$d %2$s students for %3$s";
//
//    private final String eventName;
//    private final boolean isPresent;
//
//    public ListAttendanceCommand(String eventName, boolean isPresent) {
//        this.eventName = eventName;
//        this.isPresent = isPresent;
//    }
//
//    @Override
//    public CommandResult execute(Model model) throws CommandException {
//        List<Person> students = model.getStudentsByAttendance(eventName, isPresent);
//
//        String status = isPresent ? "present" : "absent";
//        String resultMessage = String.format(MESSAGE_SUCCESS, students.size(), status, eventName);
//
//        // You may need to update the model's filtered list or UI to display the students
//        // For now, we'll just return the message
//        return new CommandResult(resultMessage);
//    }
//}
package seedu.address.logic.commands;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists students based on attendance in a specific event.
 */
public class ListAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "listattendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists students based on attendance.\n"
            + "Parameters: EVENT_NAME [present/absent]\n"
            + "Example: " + COMMAND_WORD + " Tutorial4 present";

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
        List<Person> students = model.getStudentsByAttendance(eventName, isPresent);

        if (students.isEmpty()) {
            String status = isPresent ? "present" : "absent";
            throw new CommandException(String.format("No %s students found for %s.", status, eventName));
        }

        // Update the filtered person list in the model to show the students
        Predicate<Person> attendancePredicate = person -> students.contains(person);
        model.updateFilteredPersonList(attendancePredicate);

        String status = isPresent ? "present" : "absent";
        String resultMessage = String.format(MESSAGE_SUCCESS, students.size(), status, eventName);

        return new CommandResult(resultMessage);
    }
}
