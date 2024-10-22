package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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

/**
 * Marks the attendance of a person identified using it's displayed index from the address book.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markattend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the student identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Marked attendance of person: %1$s";

    private final Index targetIndex;
    private final Attendance attendance;

    /**
     * @param targetIndex Index of the person in the filtered person list to mark
     * @param attendance Attendance of the person specified by index
     */
    public MarkAttendanceCommand(Index targetIndex, Attendance attendance) {
        requireAllNonNull(targetIndex, attendance);
        this.targetIndex = targetIndex;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMarkAttendance = lastShownList.get(targetIndex.getZeroBased());
        Person markedPerson = new Person(
                personToMarkAttendance.getName(), personToMarkAttendance.getPhone(),
                personToMarkAttendance.getEmail(), personToMarkAttendance.getAddress(),
                personToMarkAttendance.getPayment(), new ArrayList<Participation>(),
                personToMarkAttendance.getTags());

        model.setPerson(personToMarkAttendance, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                Messages.format(markedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand otherMarkAttendanceCommand = (MarkAttendanceCommand) other;
        return targetIndex.equals(otherMarkAttendanceCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
