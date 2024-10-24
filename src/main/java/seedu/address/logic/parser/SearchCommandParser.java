package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SearchCommandParser implements Parser<SearchCommand> {

    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BEGIN, PREFIX_END);
        if (!checkValidPrefixCount(argMultimap)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        LocalDateTime begin = strToLocalDateTime(argMultimap.getValue(PREFIX_BEGIN));
        LocalDateTime end = strToLocalDateTime(argMultimap.getValue(PREFIX_END));
        return new SearchCommand(begin, end);
    }

    private static boolean checkValidPrefixCount(ArgumentMultimap argumentMultimap) {
        int prefixBeginCount = argumentMultimap.getAllValues(PREFIX_BEGIN).size();
        int prefixEndCount = argumentMultimap.getAllValues(PREFIX_END).size();

        return (prefixBeginCount == 1 && prefixEndCount == 1) || // both are present exactly once
                (prefixBeginCount == 1 && prefixEndCount == 0) || // only PREFIX_BEGIN
                (prefixBeginCount == 0 && prefixEndCount == 1);   // only PREFIX_END
    }

    private static LocalDateTime strToLocalDateTime(Optional<String> args) throws ParseException{
        if (args.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(args.get(), formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(
                    String.format("herrrrrrrrrreeeeeeee", MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    }

}