package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /**
     * Parses the given {@code String} of arguments
     * and returns a SearchCommand object for execution.
     *
     * @param args The input arguments for the search command.
     * @return A SearchCommand instance containing the parsed begin and end times.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BEGIN, PREFIX_END);
        if (!checkValidPrefixCount(argMultimap) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        LocalDateTime begin = strToLocalDateTime(argMultimap.getValue(PREFIX_BEGIN));
        LocalDateTime end = strToLocalDateTime(argMultimap.getValue(PREFIX_END));
        if (begin != null && end != null && end.isBefore(begin)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        return new SearchCommand(begin, end);
    }
    /**
     * Checks if the prefix counts for begin and end time arguments are valid.
     * Ensures that either both begin and end prefixes are provided exactly once, or
     * only one of them is provided exactly once.
     *
     * @param argumentMultimap The ArgumentMultimap containing user arguments.
     * @return True if the argument prefix counts are valid; false otherwise.
     */
    private static boolean checkValidPrefixCount(ArgumentMultimap argumentMultimap) {
        int prefixBeginCount = argumentMultimap.getAllValues(PREFIX_BEGIN).size();
        int prefixEndCount = argumentMultimap.getAllValues(PREFIX_END).size();

        return (prefixBeginCount == 1 && prefixEndCount == 1)
                || (prefixBeginCount == 1 && prefixEndCount == 0)
                || (prefixBeginCount == 0 && prefixEndCount == 1);
    }
    /**
     * Converts a string representation of a date and time to a LocalDateTime object.
     *
     * @param args The optional date and time string in the format "yyyy-MM-dd HH:mm".
     * @return A LocalDateTime object representing the parsed date and time, or null if no date is provided.
     * @throws ParseException If the date format is incorrect or parsing fails.
     */
    private static LocalDateTime strToLocalDateTime(Optional<String> args) throws ParseException {
        if (args.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(args.get(), formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    }

}
