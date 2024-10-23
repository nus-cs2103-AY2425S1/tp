package seedu.address.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_INVALID_NAME_DISPLAYED = "The name provided is not in the address book.";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED = "The appointment provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! "
            + "Please use the format: yyyy-MM-dd HHmm";
    public static final String MESSAGE_INVALID_REMINDER_FORMAT = "Invalid reminder time! "
            + "Please enter either: n day/hour(s)";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code reminder} for display to the user.
     */
    public static String formatReminder(Person person, String reminder) {
        final StringBuilder builder = new StringBuilder();

        builder.append(person.getName())
                .append(": ")
                .append(reminder)
                .append(" before appointment.\n");

        return builder.toString();
    }


    /**
     * Formats the {@code appointment} for display to the user.
     *
     * @param person The {@code person} to be formatted.
     */
    public static String formatAppointment(Person person) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

        String formmattedSchedules = person.getSchedules().stream()
                .map(schedule -> {
                    LocalDateTime dateTime = LocalDateTime.parse(schedule.getDateTime(), inputFormatter);
                    String formattedDate = dateTime.format(outputFormatter);
                    return String.format("%s \n", formattedDate);
                })
                .collect(Collectors.joining(""));
        return formmattedSchedules;
    }

    /**
     * Formats the {@code appointment} for display to the user.
     *
     * @param schedule The {@code schedule} to be formatted.
     */
    public static String formatAppointment(Schedule schedule) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

        LocalDateTime dateTime = LocalDateTime.parse(schedule.getDateTime(), inputFormatter);
        String formattedDate = dateTime.format(outputFormatter);

        return formattedDate;
    }


    /**
     * Formats the {@code schedule} for display to the user.
     */
    public static String formatSchedule(Person person, Schedule schedule) {
        final StringBuilder builder = new StringBuilder();

        builder.append(person.getName())
                .append(": ")
                .append(formatAppointment(schedule));

        return builder.toString();
    }

}
