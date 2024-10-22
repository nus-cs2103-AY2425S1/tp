package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CountCommand object.
 */
public class CountCommandParser implements Parser<CountCommand> {

    private static final String PREFIX_NAME = "name/";
    private static final String PREFIX_TAG = "tag/";

    @Override
    public CountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Handle case where no arguments are given
        if (trimmedArgs.isEmpty()) {
            return new CountCommand(Optional.empty(), Optional.empty());
        }

        // Validate and parse filters
        Optional<String> namePrefix = Optional.empty();
        Optional<String> tag = Optional.empty();

        if (trimmedArgs.startsWith(PREFIX_NAME)) {
            namePrefix = Optional.of(parsePrefixArgument(trimmedArgs, PREFIX_NAME));
        } else if (trimmedArgs.startsWith(PREFIX_TAG)) {
            tag = Optional.of(parsePrefixArgument(trimmedArgs, PREFIX_TAG));
        } else {
            throw new ParseException("Invalid filter format! Use 'name/<prefix>' or 'tag/<tag>'.");
        }

        return new CountCommand(namePrefix, tag);
    }

    /**
     * Extracts the argument after the given prefix.
     *
     * @param args Full input string.
     * @param prefix The prefix to be stripped from the input.
     * @return The argument after the prefix.
     * @throws ParseException If the argument is empty.
     */
    private String parsePrefixArgument(String args, String prefix) throws ParseException {
        String argument = args.substring(prefix.length()).trim();
        if (argument.isEmpty()) {
            throw new ParseException("Argument after '" + prefix + "' cannot be empty!");
        }
        return argument;
    }
}

