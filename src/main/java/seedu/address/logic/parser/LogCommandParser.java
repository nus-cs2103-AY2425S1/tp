package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Log;

public class LogCommandParser implements Parser<LogCommand> {

    public LogCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }
        String[] argParts = trimmedArgs.split("\\s+");

        if (argParts.length != 4) {
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

        String dateTimeString = argParts[1] + " " + argParts[2];
        String logString = argParts[3];
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"));
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        return new LogCommand(index, new Log(logString, dateTime));
    }
}
