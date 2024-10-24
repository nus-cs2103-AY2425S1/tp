package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * Schedules an appointment for a person in the address book.
 * The command ensures that the appointment is within valid working hours
 * (weekdays from 9 AM to 5 PM) and that the selected time slot is available.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule appointments for client. "
            + "Parameters: "
            + "NAME "
            + PREFIX_DATE + "DATE_AND_TIME "
            + "[" + PREFIX_NOTE + "NOTE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "John Doe "
            + PREFIX_DATE + "2024-10-17 1200 "
            + PREFIX_NOTE + "first appointment ";

    public static final String MESSAGE_SUCCESS = "Scheduled %s for %s";
    public static final String MESSAGE_INVALID_TIME = "Scheduled time must be a weekday and"
            + "on the hour between 0900 and 1700";
    public static final String MESSAGE_SLOT_TAKEN = "The selected time slot is already taken.";
    public static final String MESSAGE_INVALID_NAME = "Person not found";
    public static final String MESSAGE_INVALID_DATE = "Format of the date must be in YYYY-MM-DD";
    private String name;
    private Set<Schedule> scheduleSet;

    /**
     * Constructs a ScheduleCommand to schedule an appointment for the specified person.
     *
     * @param name The name of the person.
     * @param scheduleSet The set of schedule date and time and optional notes.
     */
    public ScheduleCommand(String name, Set<Schedule> scheduleSet) {
        this.name = name;
        this.scheduleSet = scheduleSet;
    }


    /**
     * Executes the schedule command.
     * Schedules an appointment for a person, ensuring that the time slot is available,
     * and the appointment is within valid working hours.
     *
     * @param model The model containing the list of persons.
     * @return CommandResult indicating success of scheduling.
     * @throws CommandException If the person's name is not found, the time slot is already taken,
     *                          or the scheduled time is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        int index = -1;
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getName().toString().equals(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        }

        for (Schedule schedule : scheduleSet) {
            // Check if the schedule time is valid (on the hour and within weekday working hours)
            if (!isOnTheHour(schedule.getDateTime()) || !isWithinWorkingHours(schedule.getDateTime())) {
                throw new CommandException(MESSAGE_INVALID_TIME);
            }

            // Check if the time slot is already taken
            if (isTimeSlotTaken(lastShownList, schedule.getDateTime())) {
                throw new CommandException(MESSAGE_SLOT_TAKEN);
            }
        }

        Person personToEdit = lastShownList.get(index);
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), scheduleSet, personToEdit.getReminder(), personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.scheduleSet, name));
    }

    /**
     * Checks if the given LocalDateTime is on the hour (i.e., minutes == 0).
     */
    private boolean isOnTheHour(String dateTime) {
        LocalDateTime localDateTime = getLocalDateTime(dateTime);
        return localDateTime.getMinute() == 0;
    }

    /**
     * Checks if the given LocalDateTime falls on a weekday (Monday to Friday) between 9 AM and 5 PM.
     */
    private boolean isWithinWorkingHours(String dateTime) {
        LocalDateTime localDateTime = getLocalDateTime(dateTime);

        // Check if the day is a weekday
        DayOfWeek day = localDateTime.getDayOfWeek();
        boolean isWeekday = day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;

        // Check if the time is between 9 AM and 5 PM
        int hour = localDateTime.getHour();
        boolean isWorkingHours = hour >= 9 && hour < 17;

        return isWeekday && isWorkingHours;
    }

    private static LocalDateTime getLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Checks if the time slot is already taken by another person.
     */
    private boolean isTimeSlotTaken(List<Person> personList, String dateTime) {
        for (Person person : personList) {
            Set<Schedule> schedules = person.getSchedules();
            if (schedules.stream().map(Schedule::getDateTime).anyMatch(x -> x.equals(dateTime))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        // State check
        ScheduleCommand otherCommand = (ScheduleCommand) other;
        return name.equals(otherCommand.name)
                && scheduleSet.equals(otherCommand.scheduleSet);
    }
}
