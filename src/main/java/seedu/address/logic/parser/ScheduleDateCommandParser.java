package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.ScheduleDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleDateCommand object.
 */
public class ScheduleDateCommandParser implements Parser<ScheduleDateCommand> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleDateCommand
     * and returns a ScheduleDateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleDateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        // Try to parse the date, otherwise throw ParseException
        LocalDate date;
        try {
            date = LocalDate.parse(trimmedArgs, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleDateCommand.MESSAGE_USAGE), e);
        }

        return new ScheduleDateCommand(date);
    }
}
