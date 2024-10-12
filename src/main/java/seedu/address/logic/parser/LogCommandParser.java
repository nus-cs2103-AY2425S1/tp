package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LogCommand object.
 */
public class LogCommandParser implements Parser<LogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LogCommand
     * and returns a LogCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public LogCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_LOG);

        // Parse index
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE), ive);
        }

        // Parse date (if provided)
        Optional<String> dateString = argMultimap.getValue(PREFIX_DATE);
        LocalDate date = null;
        if (dateString.isPresent()) {
            try {
                date = ParserUtil.parseDate(dateString.get());
            } catch (DateTimeParseException dtpe) {
                throw new ParseException("Invalid date format! Please use yyyy-mm-dd.");
            }
        }

        // Parse log message
        String logMessage = argMultimap.getValue(PREFIX_LOG).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE)));

        // If date is null, use today's date as default
        if (date == null) {
            return new LogCommand(index, logMessage);
        }

        return new LogCommand(index, date, logMessage);
    }
}
