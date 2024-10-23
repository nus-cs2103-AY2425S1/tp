package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;

/**
 * Searches for clients who have appointments on the specified date and time.
 */
public class SearchAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "search a/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for clients who have appointments on the specified date and time.\n"
            + "Parameters: DATE TIME (must be in yyyy-MM-dd HH:mm format)\n"
            + "Example: " + COMMAND_WORD + " 2023-12-31 14:30\n"
            + "Example: " + COMMAND_WORD + "2023-12-31 14:30 to 2024-01-01 16:00";

    public static final String MESSAGE_SUCCESS = "Listed all clients with appointments %s";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "The date format is invalid. "
            + "Please use yyyy-MM-dd HH:mm format.";

    public static final String MESSAGE_INVALID_DATERANGE_FORMAT = "The date-time range format is invalid. "
            + "Please use 'yyyy-MM-dd HH:mm to yyyy-MM-dd HH:mm' format.";
    public static final String MESSAGE_START_DATE_AFTER_END_DATE = "Start date-time cannot be after end date-time.";

    private final String startDateTime;
    private final String endDateTime;

    /**
     * Creates a {@code SearchAppointmentCommand} to search for clients with appointments on the specified dateTime.
     *
     * @param dateTimeInput The date and time in string format used to search for client appointments.
     * @throws CommandException if the {@code dateTime} format is invalid.
     */
    public SearchAppointmentCommand(String dateTimeInput) throws CommandException {
        requireNonNull(dateTimeInput);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // check if the command has "to" in the command
        if (dateTimeInput.contains("to")) {
            String[] dateTimes = dateTimeInput.split("to");
            // Ensure there are exactly two date-times
            if (dateTimes.length != 2) {
                throw new CommandException(MESSAGE_INVALID_DATERANGE_FORMAT);
            }
            startDateTime = dateTimes[0].trim();
            endDateTime = dateTimes[1].trim();

            if (!isValidDateTime(startDateTime) || !isValidDateTime(endDateTime)) {
                throw new CommandException(MESSAGE_INVALID_DATE_FORMAT);
            }

            LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);

            if (start.isAfter(end)) {
                throw new CommandException(MESSAGE_START_DATE_AFTER_END_DATE);
            }

        } else {
            String dateTimeStr = dateTimeInput.trim();
            if (!isValidDateTime(dateTimeStr)) {
                throw new CommandException(MESSAGE_INVALID_DATE_FORMAT);
            }
            startDateTime = dateTimeInput;
            endDateTime = startDateTime;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Predicate<Person> predicate = person -> {
            Appointment appointment = person.getAppointment();

            if (appointment == null || appointment.value.isEmpty()) {
                return false;
            }
            String appointmentFormatted = appointment.toString();
            LocalDateTime personAppointment;
            try {
                personAppointment = LocalDateTime.parse(appointmentFormatted, formatter);
            } catch (DateTimeParseException e) {
                return false;
            }
            LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
            return (!personAppointment.isBefore(start) && !personAppointment.isAfter(end));
        };
        model.updateFilteredPersonList(predicate);

        String dateTimeRangeMessage;
        if (startDateTime.equals(endDateTime)) {
            dateTimeRangeMessage = "on " + startDateTime;
        } else {
            dateTimeRangeMessage = "from " + startDateTime + " to " + endDateTime;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, dateTimeRangeMessage));
    }

    /**
     * Checks if the given dateTime string is in the yyyy-MM-dd HH:mm format.
     *
     * @param dateTime The dateTime string to check.
     * @return True if the dateTime is in yyyy-MM-dd HH:mm format, false otherwise.
     */
    private boolean isValidDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime.parse(dateTime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if the same object
        if (other == this) {
            return true;
        }

        // Instance of handles nulls and type check
        if (!(other instanceof SearchAppointmentCommand)) {
            return false;
        }

        // Cast and compare the dateTime attribute
        SearchAppointmentCommand otherCommand = (SearchAppointmentCommand) other;
        return this.startDateTime.equals(otherCommand.startDateTime)
                && this.endDateTime.equals(otherCommand.endDateTime);
    }
}
