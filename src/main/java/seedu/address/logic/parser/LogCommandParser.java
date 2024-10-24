package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Log;

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

        // Parse the index
        String indexString = argParts[0];
        Index index;
        try {
            int indexValue = Integer.parseInt(indexString);
            if (indexValue <= 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AppointmentCommand.MESSAGE_USAGE));
            }
            index = Index.fromZeroBased(indexValue - 1); // Adjust to zero-based index
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Parse the date and time
        String dateTimeString = argParts[1] + " " + argParts[2];
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"));
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Join the remaining parts to form the logString
        String logString = Arrays.stream(argParts, 3, argParts.length)
                .collect(Collectors.joining(" "));

        return new LogCommand(index, new Log(logString, dateTime));
    }
}
