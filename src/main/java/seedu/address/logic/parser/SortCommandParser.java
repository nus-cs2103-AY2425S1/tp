package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ASCENDING;
import static seedu.address.logic.commands.SortCommand.DESCENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SCHEDULE);
        if (!onePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_SCHEDULE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MULTIPLE_PREFIX_ERROR));
        }
        try {
            if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
                String formattedArgs = formatArgument(argMultimap.getValue(PREFIX_SCHEDULE).get());
                return new SortCommand(formattedArgs, true);
            }
            else {
                String formattedArgs = formatArgument(argMultimap.getValue(PREFIX_NAME).get());
                return new SortCommand(formattedArgs, false);
            }
        } catch (ParseException pe) {
            throw pe;
        }
    }
    private static boolean onePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        long count = Stream.of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .count();
        return count == 1;
    }

    private String formatArgument(String args) throws ParseException{
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            trimmedArgs = ASCENDING;
        }
        if (trimmedArgs.equals("ascending")) {
            trimmedArgs = ASCENDING;
        }
        if (trimmedArgs.equals("descending")) {
            trimmedArgs = DESCENDING;
        }
        if (!(trimmedArgs.equals(ASCENDING) || trimmedArgs.equals(DESCENDING))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return trimmedArgs;
    }
}

