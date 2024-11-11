package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * Schedules an appointment for a person in the address book.
 * The command ensures that the appointment is within valid working hours
 * (weekdays from 9 AM to 4 PM inclusive) and that the selected time slot is available.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule appointments for client. "
            + "Parameters: "
            + "NAME "
            + PREFIX_DATE + "DATE_AND_TIME... "
            + PREFIX_NOTE + "NOTE...\n"
            + "Example: " + COMMAND_WORD + " "
            + "John Doe "
            + PREFIX_DATE + "2024-10-17 1200 "
            + PREFIX_NOTE + "first appointment ";

    public static final String MESSAGE_UNEQUAL_NOTES = "The number of notes must match the number of dates provided";

    public static final String MESSAGE_SUCCESS = "Scheduled %s for %s";
    public static final String MESSAGE_INVALID_TIME = "Scheduled time must be a weekday and "
            + "on the hour (e.g., 0900, 1000) between 0900 and 1600, inclusive.";
    public static final String MESSAGE_SLOT_TAKEN = "The selected time slot is already taken.";
    public static final String MESSAGE_INVALID_NAME = "Person not found.";
    private Name name;
    private Set<Schedule> scheduleSet;

    /**
     * Constructs a ScheduleCommand to schedule an appointment for the specified person.
     *
     * @param name The name of the person.
     * @param scheduleSet The set of schedule date and time and optional notes.
     */
    public ScheduleCommand(Name name, Set<Schedule> scheduleSet) {
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

        Person personToEdit = findPersonByName(model.getFilteredPersonList(), name)
                .orElseThrow(() -> new CommandException(MESSAGE_INVALID_NAME));

        validateSchedules(model.getFilteredPersonList(), scheduleSet);

        assert personToEdit != null : "Person to edit should not be null";
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), scheduleSet, personToEdit.getReminder(), personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.scheduleSet, name));
    }

    /**
     * Finds a person in the given list by their name.
     * This method searches the provided list of persons for a person whose name matches
     * the specified name.
     *
     * @param personList The list of persons to search.
     * @param name The name of the person to find.
     * @return An {@code Optional<Person>} containing the found person, or an empty Optional if no person is found.
     * @throws NullPointerException If the provided personList or name is null.
     */
    private Optional<Person> findPersonByName(List<Person> personList, Name name) {
        requireNonNull(personList);
        return personList.stream().filter(person -> person.getName().equals(name)).findFirst();
    }

    /**
     * Validates a set of schedules to ensure that each scheduled time is valid and available.
     * The method performs the following checks:
     * 1. Ensures the time is on the hour (e.g., 0900, 1000).
     * 2. Ensures the time is within working hours (weekdays between 9 AM and 4 PM inclusive).
     * 3. Checks that the time slot is not already taken by another person.
     *
     * @param personList The list of persons to check for conflicting schedules.
     * @param schedules The set of schedules to validate.
     * @throws CommandException If any schedule has an invalid time or a conflicting time slot.
     */
    private void validateSchedules(List<Person> personList, Set<Schedule> schedules) throws CommandException {
        for (Schedule schedule : schedules) {
            // Check if the schedule time is valid (on the hour and within weekday working hours)
            if (!isOnTheHour(schedule.getDateTime()) || !isWithinWorkingHours(schedule.getDateTime())) {
                throw new CommandException(MESSAGE_INVALID_TIME);
            }

            // Check if the time slot is already taken
            if (isTimeSlotTaken(personList, schedule.getDateTime())) {
                throw new CommandException(MESSAGE_SLOT_TAKEN + " (" + schedule.getDateTime() + ")");
            }
        }
    }

    /**
     * Checks if the given LocalDateTime is on the hour (i.e., minutes == 0).
     */
    private boolean isOnTheHour(String dateTime) {
        assert dateTime != null : "DateTime string should not be null";
        LocalDateTime localDateTime = getLocalDateTime(dateTime);
        return localDateTime.getMinute() == 0;
    }

    /**
     * Checks if the given LocalDateTime falls on a weekday (Monday to Friday) between 9 AM and 5 PM.
     */
    private boolean isWithinWorkingHours(String dateTime) {
        assert dateTime != null : "DateTime string should not be null";
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
        assert personList != null : "Person list should not be null";
        assert dateTime != null : "DateTime string should not be null";
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
