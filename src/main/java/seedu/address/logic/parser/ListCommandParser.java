package seedu.address.logic.parser;

import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    private static final List<String> ACCEPTED_ARGUMENTS =
            List.<String>of("all", "contacts", "allcontacts");

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution after validation.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedLowerArgs = args.trim().toLowerCase();
        boolean isValidArgument = validateArgument(trimmedLowerArgs);
        if (!isValidArgument) {
            throw new ParseException(ListCommand.MESSAGE_WRONG_ARGUMENTS);
        }
        return new ListCommand();
    }

    private boolean validateArgument(String argument) {
        boolean isArgumentEmpty = argument.isEmpty();
        boolean isArgumentPresentAndValid = checkifArgumentPresentAndValid(argument, isArgumentEmpty);
        return isArgumentEmpty || isArgumentPresentAndValid;
    }

    private static boolean checkifArgumentPresentAndValid(String argument, boolean isArgumentEmpty) {
        if (isArgumentEmpty) {
            return false;
        }
        assert !argument.isEmpty();
        final String regexWhitespaceWithRepeats = "\\s+";

        List<String> commandDescription = List.of(argument.split(regexWhitespaceWithRepeats));
        return commandDescription.stream()
                .allMatch(word -> ACCEPTED_ARGUMENTS.contains(word));
    }
}
