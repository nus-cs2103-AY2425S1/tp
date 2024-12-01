package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AppointmentCommand object.
 */
public class AppointmentCommandParser implements Parser<AppointmentCommand> {

    private static final String APPOINTMENT_PREFIX = "app/";

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentCommand
     * and returns an AppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AppointmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Split the arguments into components
        String[] argParts = trimmedArgs.split("\\s+");

        // Check if the input has exactly 3 parts: NRIC, app/DATE, TIME
        if (argParts.length != 3 || !argParts[1].startsWith(APPOINTMENT_PREFIX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        //Parse the nric
        String nricString = argParts[0];
        Nric nric;

        try {
            nric = new Nric(nricString);
        } catch (IllegalArgumentException e) {
            throw new ParseException("NRIC provided is invalid.");
        }

        // Parse the date and time
        String[] appointmentPrefixDate = argParts[1].split("app/");
        if (appointmentPrefixDate.length != 2) {
            throw new ParseException("Date and time provided are invalid.");
        }
        String appointmentDateTime = appointmentPrefixDate[1] + " " + argParts[2];
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(appointmentDateTime, DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            throw new ParseException("Date and time provided are invalid.");
        }

        // Return the constructed AppointmentCommand
        return new AppointmentCommand(nric, appointmentDateTime);
    }
}
