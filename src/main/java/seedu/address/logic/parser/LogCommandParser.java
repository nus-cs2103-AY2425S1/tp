package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Log;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new LogCommand object.
 */
public class LogCommandParser implements Parser<LogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LogCommand
     * and returns a LogCommand object for execution.
     *
     * @param args The input arguments.
     * @return A LogCommand object.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public LogCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }
        String[] argParts = trimmedArgs.split("\\s+");

        if (argParts.length < 4) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
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
        String dateTimeString = argParts[1] + " " + argParts[2];
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            throw new ParseException("Date and time provided are invalid.");
        }

        // Join the remaining parts to form the logString
        String logString = Arrays.stream(argParts, 3, argParts.length)
                .collect(Collectors.joining(" "));

        return new LogCommand(nric, new Log(logString, dateTime));
    }
}
