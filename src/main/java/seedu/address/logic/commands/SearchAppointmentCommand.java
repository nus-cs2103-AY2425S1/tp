package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATETIMERANGE_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATETIME_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_START_DATETIME_AFTER_END_DATETIME;
import static seedu.address.logic.Messages.MESSAGE_SUCCESS_SEARCH_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_APPOINTMENT;

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
    public static final String COMMAND_WORD = "search " + PREFIX_SEARCH_APPOINTMENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for clients who have appointments on the specified date "
            + "and time or within a specified date time range.\n"
            + "Parameters: DATE TIME (must be in yyyy-MM-dd HH:mm format)\n"
            + "Example: " + COMMAND_WORD + " 2023-12-31 14:30\n"
            + "Example: " + COMMAND_WORD + "2023-12-31 14:30 to 2024-01-01 16:00";

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

        if (dateTimeInput.contains("to")) {
            String[] dateTimes = dateTimeInput.split("to");
            if (dateTimes.length != 2) {
                throw new CommandException(MESSAGE_INVALID_DATETIMERANGE_FORMAT);
            }
            startDateTime = dateTimes[0].trim();
            endDateTime = dateTimes[1].trim();

            if (!isValidDateTime(startDateTime) || !isValidDateTime(endDateTime)) {
                throw new CommandException(MESSAGE_INVALID_DATETIME_FORMAT);
            }

            LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);

            if (start.isAfter(end)) {
                throw new CommandException(MESSAGE_START_DATETIME_AFTER_END_DATETIME);
            }

        } else {
            String dateTimeStr = dateTimeInput.trim();
            if (!isValidDateTime(dateTimeStr)) {
                throw new CommandException(MESSAGE_INVALID_DATETIME_FORMAT);
            }
            startDateTime = dateTimeInput;
            endDateTime = startDateTime;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // This execute method relied on GPT to help check the correctness of the code.
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
        return new CommandResult(String.format(MESSAGE_SUCCESS_SEARCH_APPOINTMENT, dateTimeRangeMessage));
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
        // This method made use of ChatGPT to ensure its correctness when comparing the Command objects
        if (other == this) {
            return true;
        }

        if (!(other instanceof SearchAppointmentCommand)) {
            return false;
        }

        SearchAppointmentCommand otherCommand = (SearchAppointmentCommand) other;
        return this.startDateTime.equals(otherCommand.startDateTime)
                && this.endDateTime.equals(otherCommand.endDateTime);
    }
}
