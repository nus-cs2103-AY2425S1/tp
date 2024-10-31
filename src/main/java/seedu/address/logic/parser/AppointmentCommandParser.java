package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AppointmentCommand object.
 */
public class AppointmentCommandParser implements Parser<AppointmentCommand> {

    private static final String APPOINTMENT_PREFIX = "a/";

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentCommand
     * and returns an AppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AppointmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Split the arguments into components
        String[] argParts = trimmedArgs.split("\\s+");

        // Check if the input has exactly 3 parts: INDEX, a/DATE, TIME
        if (argParts.length != 3 || !argParts[1].startsWith(APPOINTMENT_PREFIX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        String dayString;
        String dayPart;
        String monthPart;
        String yearPart;

        try {
            // Argument parts validation
            dayString = argParts[1].substring(2, 12);

            // Extract day, month, and year
            dayPart = dayString.substring(0, 2);
            monthPart = dayString.substring(3, 5);
            yearPart = dayString.substring(6, 10);

        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }


        // Check if the string is in the correct format (with '-' at the right positions)
        if (!dayString.substring(2, 3).equals("-") || !dayString.substring(5, 6).equals("-")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        int day;
        int month;
        int year;
        try {
            day = Integer.parseInt(dayPart);
            month = Integer.parseInt(monthPart);
            year = Integer.parseInt(yearPart);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid date format. Day, month, and year must be numbers.");
        }

        // Validate day and month ranges
        if (day < 1 || day > 31 || month < 1 || month > 12) {
            throw new ParseException("Day and months must be valid.");
        }

        // Time validation
        String timeString;
        String hourPart;
        String minutePart;

        try {
            timeString = argParts[2];
            hourPart = timeString.substring(0, 2);
            minutePart = timeString.substring(3, 5);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        if (!timeString.substring(2, 3).equals(":")) {
            throw new ParseException("Invalid time format. Please use HH:MM.");
        }
        int hour;
        int minute;
        try {
            hour = Integer.parseInt(hourPart);
            minute = Integer.parseInt(minutePart);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid time format. Hour and minute must be numbers.");
        }
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new ParseException("Hour and minute must be valid.");
        }

        String nricString = argParts[0];
        Nric nric;

        try {
            nric = new Nric(nricString);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Extract and validate the appointment date and time
        String appointmentDateTime = trimmedArgs.substring(trimmedArgs.indexOf(APPOINTMENT_PREFIX) + 2).trim();

        // If appointmentDateTime is not valid, throw an error
        if (appointmentDateTime.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Return the constructed AppointmentCommand
        return new AppointmentCommand(nric, appointmentDateTime);
    }
}
