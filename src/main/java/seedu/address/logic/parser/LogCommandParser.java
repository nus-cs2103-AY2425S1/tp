package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LogCommand object
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
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE), pe);
        }

        // Parse date (if provided)
        Optional<String> dateString = argMultimap.getValue(PREFIX_DATE);
        Optional<LocalDate> date = Optional.empty();
        if (dateString.isPresent()) {
            try {
                date = Optional.of(ParserUtil.parseDate(dateString.get()));
            } catch (DateTimeParseException dtpe) {
                throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
            }
        }

        // Parse log message
        String logMessage = argMultimap.getValue(PREFIX_LOG).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE)));

        // Use today's date if no date was provided
        LocalDate finalDate = date.orElse(LocalDate.now());
        if (logMessage.trim().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_LOG_MESSAGE_EMPTY));
        }
        return new LogCommand(index, finalDate, logMessage);
    }
}
