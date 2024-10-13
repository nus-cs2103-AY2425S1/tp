package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_PRIORITY, PREFIX_REMARK, PREFIX_TAG);
        // Check for filtering by invalid prefixes
        if (hasPrefixes(argMultimap, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_REMARK, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            System.out.println("Invalid use here");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            // Note: Here, should return message not to use disallowed prefixes, and how to use allowed ones
        }

        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        List<String> addresses = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> priorities = argMultimap.getAllValues(PREFIX_PRIORITY);

        if (!names.isEmpty()) {
            names = splitStrings(names);
        }

        if (!priorities.isEmpty()) {
            priorities = splitStrings(priorities);
        }

        return new FindCommand(names, addresses, priorities);
    }

    /**
     * Returns true if any of the prefixes are used in the given
     * {@code ArgumentMultimap}.
     */
    private boolean hasPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Splits each string in {@code list} into multiple strings if there is whitespace between characters.
     *
     * @return New list of all strings without any whitespace.
     */
    private List<String> splitStrings(List<String> list) {
        List<String> newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            String currentString = list.get(i);
            String[] separatedStrings = currentString.split("\\s+");
            newList.addAll(Arrays.asList(separatedStrings));
        }
        return newList;
    }
}
