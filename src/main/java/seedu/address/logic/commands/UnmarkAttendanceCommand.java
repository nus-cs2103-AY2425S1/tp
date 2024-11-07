package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NONMEMBER_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
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

    public static final String MESSAGE_UNMARK_MEMBER_SUCCESS =
            "Successfully unmarked the attendance on %1$s for member(s):\n%2$s\n";
    public static final String MESSAGE_CONTAIN_UNMARKED_MEMBER =
            "Please note that attendance of %1$s is already unmarked.";

    private final List<Telegram> telegrams;
    private final Attendance attendance;
    private final List<Telegram> unmatchedTelegrams = new ArrayList<>();
    private final List<Person> membersToUnmarkAttendance = new ArrayList<>();
    private final List<Person> membersAttendanceAlreadyUnmarked = new ArrayList<>();
    private final List<Person> membersAttendanceUnmarkSuccess = new ArrayList<>();

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
        findByTelegram(telegrams, lastShownList);
        if (!unmatchedTelegrams.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TELEGRAM, unmatchedTelegrams));
        }

        unmarkAttendance(model, membersToUnmarkAttendance, this.attendance);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String commandResultMessage = membersAttendanceAlreadyUnmarked.isEmpty()
                ? String.format(MESSAGE_UNMARK_MEMBER_SUCCESS, attendance,
                displayMembers(membersAttendanceUnmarkSuccess))
                : (this.membersAttendanceUnmarkSuccess.isEmpty()
                ? String.format(MESSAGE_CONTAIN_UNMARKED_MEMBER, displayMembers(membersAttendanceAlreadyUnmarked))
                : String.format(MESSAGE_UNMARK_MEMBER_SUCCESS, attendance,
                displayMembers(membersAttendanceUnmarkSuccess))
                + String.format(MESSAGE_CONTAIN_UNMARKED_MEMBER,
                displayMembers(membersAttendanceAlreadyUnmarked)));
        return new CommandResult(commandResultMessage);
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
     * For members that initially don't have an attendance at the specified date,
     * add it to {@code membersAttendanceAlreadyUnmarked}
     */
    private Person removeAttendanceToPerson(Person person, Attendance attendance) throws CommandException {
        if (!person.isMember()) {
            throw new CommandException(MESSAGE_NONMEMBER_ATTENDANCE);
        }
        if (!person.getAttendance().contains(attendance)) {
            this.membersAttendanceAlreadyUnmarked.add(person);
            return person;
        }
        Set<Attendance> newAttendanceList = new HashSet<>(person.getAttendance());
        newAttendanceList.remove(attendance);
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Telegram telegram = person.getTelegram();
        Set<Role> roles = person.getRoles();
        Set<Attendance> updatedAttendances = newAttendanceList;
        FavouriteStatus favouriteStatus = person.getFavouriteStatus();

        Person newPerson = new Person(name, phone, email, telegram, roles, updatedAttendances, favouriteStatus);
        membersAttendanceUnmarkSuccess.add(newPerson);
        return newPerson;
    }

    private void unmarkAttendance(
            Model model, List<Person> peopleToUnmark, Attendance attendance) throws CommandException {
        for (Person p: peopleToUnmark) {
            Person markedPerson = removeAttendanceToPerson(p, attendance);
            model.setPerson(p, markedPerson);
        }
    }

    /**
     * Find person with target telegram handle in current contact list
     * @param telegrams Target telegram handle to search in peopleList
     * @param peopleList All people in current contact list view
     *
     *      Add member to {@code membersToUnmarkAttendance} if their telegram handles is in input list;
     *      Add telegram handles that don't map to a member to {@code unmatchedTelegrams}
     *      Set {@code membersToUnmarkAttendance} and {@code unmatchedTelegrams}
     *                   to object private variables accordingly.
     */
    private void findByTelegram(List<Telegram> telegrams, List<Person> peopleList) {
        List<Telegram> mappedTelegrams = new ArrayList<>();
        for (Person person: peopleList) {
            if (telegrams.contains(person.getTelegram())) {
                membersToUnmarkAttendance.add(person);
                mappedTelegrams.add(person.getTelegram());
            }
        }


        for (Telegram t: telegrams) {
            if (!mappedTelegrams.contains(t)) {
                unmatchedTelegrams.add(t);
            }
        }
    }

}
