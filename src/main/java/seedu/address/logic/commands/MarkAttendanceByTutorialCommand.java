package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Marks the attendance of a person identified using it's displayed index from the address book.
 */
public class MarkAttendanceByTutorialCommand extends Command {

    public static final String COMMAND_WORD = "markattendtut";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the all the students in the specified tutorial.\n"
            + "Parameters: "
            + "[" + PREFIX_ATTENDANCE + "ATTENDANCE]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_ATTENDANCE + "20/10/2024";

    public static final String MESSAGE_MARK_ATTENDANCE_TUTORIAL_SUCCESS =
            "Marked attendance of all students in tutorial: %1$s";

    private final Tutorial tutorial;
    private final String attendance;

    /**
     * @param tutorial Tutorial to mark the attendance of all students
     * @param attendance Attendance of the students
     */
    public MarkAttendanceByTutorialCommand(Tutorial tutorial, String attendance) {
        requireAllNonNull(tutorial, attendance);
        this.tutorial = tutorial;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToMarkAttendance = lastShownList.get(0);
        Person markedPerson = new Person(
                personToMarkAttendance.getName(), personToMarkAttendance.getPhone(),
                personToMarkAttendance.getEmail(), personToMarkAttendance.getAddress(),
                personToMarkAttendance.getPayment(), new ArrayList<Participation>(),
                personToMarkAttendance.getTags());

        model.setPerson(personToMarkAttendance, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_TUTORIAL_SUCCESS,
                Messages.format(markedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceByTutorialCommand)) {
            return false;
        }

        MarkAttendanceByTutorialCommand otherMarkAttendanceCommand = (MarkAttendanceByTutorialCommand) other;
        return tutorial.equals(otherMarkAttendanceCommand.tutorial) &&
                attendance.equals(otherMarkAttendanceCommand.attendance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorial", tutorial)
                .add("attendance", attendance)
                .toString();
    }
}
