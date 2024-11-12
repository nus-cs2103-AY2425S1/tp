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
 * Mark the attendance of people with targeted telegram handle.
 */
public class MarkAttendanceCommand extends AttendanceMarkingCommand {
    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the attendance of member(s) within list of input telegrams on designated date.\n"
            + "Parameters: "
            + PREFIX_TELEGRAM + "TELEGRAM "
            + PREFIX_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TELEGRAM + "alexYeoh "
            + PREFIX_TELEGRAM + "berniceYu " + PREFIX_DATE + "2024-10-21";

    public static final String MESSAGE_MARK_MEMBER_SUCCESS =
            "Successfully marked the attendance on %1$s for member(s): \n%2$s\n";
    public static final String MESSAGE_CONTAIN_MARKED_MEMBER =
            "Please note that attendance of %1$s is already marked.";

    private final List<Telegram> telegrams;
    private final Attendance attendance;
    private final List<Person> membersToMarkAttendance = new ArrayList<>();
    private final List<Telegram> unmatchedTelegrams = new ArrayList<>();
    private final List<Person> membersAttendanceAlreadyMarked = new ArrayList<>();
    private final List<Person> membersAttendanceMarkSuccess = new ArrayList<>();

    /**
     * Constructs a {@code MarkAttendanceCommand} that marks the attendance
     * for the owners of the specified telegrams on the given date.
     *
     * @param telegrams the list of {@code Telegram} objects representing the people
     *                  whose attendance will be marked.
     * @param attendance the {@code Attendance} object representing the date
     *                   of the attendance to be recorded.
     */
    public MarkAttendanceCommand(List<Telegram> telegrams, Attendance attendance) {
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

        markAttendance(model, membersToMarkAttendance, this.attendance);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String commandResultMessage = this.membersAttendanceAlreadyMarked.isEmpty()
                ? String.format(MESSAGE_MARK_MEMBER_SUCCESS, attendance, displayMembers(membersAttendanceMarkSuccess))
                : (this.membersAttendanceMarkSuccess.isEmpty()
                    ? String.format(MESSAGE_CONTAIN_MARKED_MEMBER, displayMembers(membersAttendanceAlreadyMarked))
                    : String.format(MESSAGE_MARK_MEMBER_SUCCESS, attendance,
                displayMembers(membersAttendanceMarkSuccess))
                + String.format(MESSAGE_CONTAIN_MARKED_MEMBER, displayMembers(membersAttendanceAlreadyMarked)));
        return new CommandResult(commandResultMessage);
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
     * Find person with target telegram handle in current contact list
     * @param telegrams Target telegram handle to search in peopleList
     * @param peopleList All people in current contact list view
     *      Add members to {@code membersToMarkAttendance} if their telegram handles are in input list;
     *      add telegram handles that don't map to a member to {@code unmatchedTelegrams}
     *      Set {@code membersToMarkAttendance} and {@code unmatchedTelegrams} to object private variables accordingly.
     */
    private void findByTelegram(List<Telegram> telegrams, List<Person> peopleList) {
        List<Telegram> mappedTelegrams = new ArrayList<>();
        for (Person person: peopleList) {
            if (telegrams.contains(person.getTelegram())) {
                membersToMarkAttendance.add(person);
                mappedTelegrams.add(person.getTelegram());
            }
        }

        for (Telegram t: telegrams) {
            if (!mappedTelegrams.contains(t)) {
                unmatchedTelegrams.add(t);
            }
        }
    }


    /**
     * Creates and returns a {@code Person} with updated attendances that includes the {@code attendance} parameter
     * For members that already have attendance at the specified date, add it to {@code membersAttendanceAlreadyMarked}
     */
    private Person addAttendanceToPerson(Person person, Attendance attendance) throws CommandException {
        if (!person.isMember()) {
            throw new CommandException(MESSAGE_NONMEMBER_ATTENDANCE);
        }
        if (person.getAttendance().contains(attendance)) {
            this.membersAttendanceAlreadyMarked.add(person);
            return person;
        }
        Set<Attendance> newAttendanceList = new HashSet<>(person.getAttendance());
        newAttendanceList.add(attendance);
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Telegram telegram = person.getTelegram();
        Set<Role> roles = person.getRoles();
        Set<Attendance> updatedAttendances = newAttendanceList;
        FavouriteStatus favouriteStatus = person.getFavouriteStatus();

        Person newPerson = new Person(name, phone, email, telegram, roles, updatedAttendances, favouriteStatus);
        membersAttendanceMarkSuccess.add(newPerson);
        return newPerson;
    }

    private void markAttendance(Model model, List<Person> peopleToMark, Attendance attendance) throws CommandException {
        for (Person p: peopleToMark) {
            Person markedPerson = addAttendanceToPerson(p, attendance);
            model.setPerson(p, markedPerson);
        }
    }
}
