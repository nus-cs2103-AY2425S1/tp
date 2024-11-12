package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;
import seedu.address.model.scheme.SchemeRetrieval;

/**
 * Displays the statistics of the filtered people in SocialBook.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the general statistics regarding the current people list in SocialBook.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_DISPLAY_STATISTICS_SUCCESS = "Here are the statistics for the current list:\n%s";
    public static final String MESSAGE_DISPLAY_TOTAL_PEOPLE = "Total Number Of People: %s";
    public static final String MESSAGE_DISPLAY_HIGH_PRIORITY = "Number Of HIGH Priority People: %s";
    public static final String MESSAGE_DISPLAY_MEDIUM_PRIORITY = "Number Of MEDIUM Priority People: %s";
    public static final String MESSAGE_DISPLAY_LOW_PRIORITY = "Number Of LOW Priority People: %s";
    public static final String MESSAGE_DISPLAY_APPOINTMENTS_SOON =
            "Number Of Appointments Scheduled Within Next 7 Days: %s";
    public static final String MESSAGE_DISPLAY_ELIGIBLE_PERSONS =
            "Number Of People Eligible For At Least One Scheme: %s";
    private String resultMessage = "";

    /**
     * Displays all the overall statistics to be shown.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Appointment> lastShownAppointmentList = model.getFilteredAppointmentList();
        List<String> allStats = new ArrayList<>();

        allStats.add(nbOfPeople(lastShownPersonList));
        allStats.add(highPriorityPeople(lastShownPersonList));
        allStats.add(mediumPriorityPeople(lastShownPersonList));
        allStats.add(lowPriorityPeople(lastShownPersonList));
        allStats.add(appointmentsSoon(lastShownPersonList, lastShownAppointmentList));
        allStats.add(eligibleForScheme(lastShownPersonList));

        resultMessage = String.join("\n", allStats);

        return new CommandResult(String.format(MESSAGE_DISPLAY_STATISTICS_SUCCESS, resultMessage));
    }

    /**
     * Returns a message with total number of people in current list.
     *
     * @param currList current list.
     * @return string message of total number of people.
     */
    public static String nbOfPeople(List<Person> currList) {
        return String.format(MESSAGE_DISPLAY_TOTAL_PEOPLE, currList.size());
    }

    /**
     * Returns a message with number of high priority people in current list.
     *
     * @param currList current list.
     * @return string message of number of high priority people.
     */
    public static String highPriorityPeople(List<Person> currList) {
        long highPriority = currList.stream()
                .filter(person -> person.getPriority() == Priority.HIGH)
                .count();
        return String.format(MESSAGE_DISPLAY_HIGH_PRIORITY, highPriority);
    }

    /**
     * Returns a message with number of medium priority people in current list.
     *
     * @param currList current list.
     * @return string message of number of medium priority people.
     */
    public static String mediumPriorityPeople(List<Person> currList) {
        long mediumPriority = currList.stream()
                .filter(person -> person.getPriority() == Priority.MEDIUM)
                .count();
        return String.format(MESSAGE_DISPLAY_MEDIUM_PRIORITY, mediumPriority);
    }

    /**
     * Returns a message with number of low priority people in current list.
     *
     * @param currList current list.
     * @return string message of number of low priority people.
     */
    public static String lowPriorityPeople(List<Person> currList) {
        long lowPriority = currList.stream()
                .filter(person -> person.getPriority() == Priority.LOW)
                .count();
        return String.format(MESSAGE_DISPLAY_LOW_PRIORITY, lowPriority);
    }


    /**
     * Returns a message with number of people with appointments within a week or less from the current time.
     *
     * @param currPersonList current list of persons.
     * @param currAppointmentList current list of appointments.
     * @return string message of number of people with appointments within a week or less from current time.
     */
    public static String appointmentsSoon(List<Person> currPersonList, List<Appointment> currAppointmentList) {
        Set<Name> names = currPersonList.stream().map(Person::getName).collect(Collectors.toSet());

        long appointmentsSoon = currAppointmentList.stream()
                .filter(appointment -> names.contains(appointment.name()) // O(1) lookup
                        && isWithinAWeek(appointment.date()))
                .count();
        return String.format(MESSAGE_DISPLAY_APPOINTMENTS_SOON, appointmentsSoon);
    }

    /**
     * Helper command to check whether date is within 7 days from current time.
     *
     * @param date to be checked whether within time range.
     * @return Whether inputted date is within a week from now.
     */
    public static boolean isWithinAWeek(LocalDate date) {
        LocalDate now = LocalDate.now();
        return !date.isBefore(now) && ChronoUnit.DAYS.between(now, date) <= 7;
    }

    /**
     * Returns a message with number of people with eligible for 1 or more schemes.
     *
     * @param currList current list of people.
     * @return string message of number of people with eligible for 1 or more schemes.
     */
    public static String eligibleForScheme(List<Person> currList) {
        long eligiblePersons = currList.stream()
                .filter(person -> new SchemeRetrieval(person).getSchemes().size() > 0)
                .count();
        return String.format(MESSAGE_DISPLAY_ELIGIBLE_PERSONS, eligiblePersons);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatisticsCommand)) {
            return false;
        }

        StatisticsCommand otherStatisticsCommand = (StatisticsCommand) other;
        return resultMessage.equals(otherStatisticsCommand.getResultMessage());
    }

}
