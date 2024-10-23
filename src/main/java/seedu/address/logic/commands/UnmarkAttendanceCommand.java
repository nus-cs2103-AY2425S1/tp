package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.MarkAttendanceCommand.findByTelegram;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.role.Role;

/**
 * Unmark the attendance of people with targeted telegram handle.
 */
public class UnmarkAttendanceCommand extends AttendanceMarkingCommand {
    public static final String COMMAND_WORD = "unmark";
    public static final String COMMAND_ALIAS = "um";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmark(delete) the attendance of people with list of input telegrams on designated date.\n"
            + "Parameters: "
            + PREFIX_TELEGRAM + "TELEGRAM "
            + PREFIX_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TELEGRAM + "alexYeoh "
            + PREFIX_TELEGRAM + "berniceYu " + PREFIX_DATE + "2024-10-21";

    public static final String MESSAGE_UNMARK_PERSON_SUCCESS =
            "Successfully unmark the attendance on %1$s for these people: %2$s";

    private final List<Telegram> telegrams;
    private final Attendance attendance;

    /**
     * Constructs a {@code UnMarkAttendanceCommand} that unmarks (deletes) the attendance
     * for the owners of the specified telegrams on the given date.
     *
     * @param telegrams the list of {@code Telegram} objects representing the people
     *                  whose attendance will be unmarked.
     * @param attendance the {@code Attendance} object representing the date
     *                   of the attendance to be unrecorded.
     */

    public UnmarkAttendanceCommand(List<Telegram> telegrams, Attendance attendance) {
        this.telegrams = telegrams;
        this.attendance = attendance;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> peopleToUnmarkAttendance = findByTelegram(telegrams, lastShownList);
        List<String> peopleNames = peopleToUnmarkAttendance.stream().map(p -> p.getName().toString()).toList();
        if (peopleToUnmarkAttendance.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TELEGRAM, telegrams));
        }

        unmarkAttendance(model, peopleToUnmarkAttendance, this.attendance);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(
                MESSAGE_UNMARK_PERSON_SUCCESS, attendance, peopleNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnmarkAttendanceCommand)) {
            return false;
        }

        UnmarkAttendanceCommand otherMarkAttendanceCommand = (UnmarkAttendanceCommand) other;
        return this.telegrams.equals(otherMarkAttendanceCommand.telegrams)
                & this.attendance.equals(otherMarkAttendanceCommand.attendance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("telegrams", telegrams)
                .add("attendance", attendance)
                .toString();
    }


    /**
     * Creates and returns a {@code Person} with the {@code attendance} parameter removed from its attendance list
     * The attendance list will automatically keep the same if the attendance doesn't exist initially.
     */
    private Person removeAttendanceToPerson(Person person, Attendance attendance) {
        Set<Attendance> newAttendanceList = new HashSet<>(person.getAttendance());
        newAttendanceList.remove(attendance);
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Telegram telegram = person.getTelegram();
        Set<Role> roles = person.getRoles();
        Set<Attendance> updatedAttendances = newAttendanceList;
        FavouriteStatus favouriteStatus = person.getFavouriteStatus();

        return new Person(name, phone, email, telegram, roles, updatedAttendances, favouriteStatus);
    }

    private void unmarkAttendance(Model model, List<Person> peopleToMark, Attendance attendance) {
        for (Person p: peopleToMark) {
            Person markedPerson = removeAttendanceToPerson(p, attendance);
            model.setPerson(p, markedPerson);
        }
    }
}
