package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ASCENDING;
import static seedu.address.logic.commands.SortCommand.DESCENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SortCommand} object.
 */

public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SCHEDULE);
        if (!onePrefixPresent(argMultimap)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        try {
            if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
                String formattedArgs = formatArgument(argMultimap.getValue(PREFIX_SCHEDULE).get());
                return new SortCommand(formattedArgs, true);
            } else {
                String formattedArgs = formatArgument(argMultimap.getValue(PREFIX_NAME).get());
                return new SortCommand(formattedArgs, false);
            }
        } catch (ParseException pe) {
            throw pe;
        }
    }
    /**
     * Checks if exactly one prefix is provided.
     * Ensures that either {@code PREFIX_NAME} or {@code PREFIX_SCHEDULE} is provided, but not both.
     *
     * @param argumentMultimap The multimap containing parsed arguments and prefixes.
     * @return true if exactly one of the prefixes is present, false otherwise.
     */
    private static boolean onePrefixPresent(ArgumentMultimap argumentMultimap) {
        boolean isSinglePrefixName = argumentMultimap.getAllValues(PREFIX_NAME).size() == 1;
        boolean isSinglePrefixSchedule = argumentMultimap.getAllValues(PREFIX_SCHEDULE).size() == 1;
        //XOR to ensure only there is only one prefix provided
        return isSinglePrefixName ^ isSinglePrefixSchedule;
    }
    /**
     * Formats the input argument for sorting.
     * If an empty string is provided as an argument, it will be changed to "asc".
     * Arguments are converted to lower casing.
     * Converts "ascending" to "asc" and "descending" to "desc", or throws an exception if the input is invalid.
     * If no argument is provided, it defaults to ascending order.
     *
     * @param args The input argument.
     * @return The formatted argument as "asc" or "desc".
     * @throws ParseException if the input argument is not a valid argument.
     */
    private String formatArgument(String args) throws ParseException {
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

