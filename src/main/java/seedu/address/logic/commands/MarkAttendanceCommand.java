package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
            + ": Mark the attendance of people with list of input telegrams on designated date.\n"
            + "Parameters: "
            + PREFIX_TELEGRAM + "TELEGRAM "
            + PREFIX_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TELEGRAM + "alexYeoh "
            + PREFIX_TELEGRAM + "berniceYu " + PREFIX_DATE + "2024-10-21";

    public static final String MESSAGE_MARK_PERSON_SUCCESS =
            "Successfully mark the attendance on %1$s for these people: %2$s";

    private final List<Telegram> telegrams;
    private final Attendance attendance;

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
        List<Person> peopleToMarkAttendance = findByTelegram(telegrams, lastShownList);
        List<String> peopleNames = peopleToMarkAttendance.stream().map(p -> p.getName().toString()).toList();
        if (peopleToMarkAttendance.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TELEGRAM, telegrams));
        }

        markAttendance(model, peopleToMarkAttendance, this.attendance);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, attendance, peopleNames));
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
     * @return Person with {@code telegram} if it exists, else return person not found
     */
    protected static List<Person> findByTelegram(List<Telegram> telegrams, List<Person> peopleList) {
        List<Person> peopleToMark = new ArrayList<>();
        for (Person person: peopleList) {
            if (telegrams.contains(person.getTelegram())) {
                peopleToMark.add(person);
            }
        }
        return peopleToMark;
    }


    /**
     * Creates and returns a {@code Person} with updated attendances that includes the {@code attendance} parameter
     * The attendance list will automatically keep the same if the added attendance already exists.
     */
    private Person addAttendanceToPerson(Person person, Attendance attendance) {
        Set<Attendance> newAttendanceList = new HashSet<>(person.getAttendance());
        newAttendanceList.add(attendance);
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Telegram telegram = person.getTelegram();
        Set<Role> roles = person.getRoles();
        Set<Attendance> updatedAttendances = newAttendanceList;
        FavouriteStatus favouriteStatus = person.getFavouriteStatus();

        return new Person(name, phone, email, telegram, roles, updatedAttendances, favouriteStatus);
    }

    private void markAttendance(Model model, List<Person> peopleToMark, Attendance attendance) {
        for (Person p: peopleToMark) {
            Person markedPerson = addAttendanceToPerson(p, attendance);
            model.setPerson(p, markedPerson);
        }
    }
}
