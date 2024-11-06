package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.logic.commands.HelpCommand;
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
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, " There shouldn't' be any words after "
                            + "the list command word except for a few special cases. "
                            + String.format(
                                    MESSAGE_HELP_PROMPT, HelpCommand.COMMAND_WORD + " " + ListCommand.COMMAND_WORD)));
        }
        return new ListCommand();
    }

    private boolean validateArgument(String argument) {
        boolean isArgumentEmpty = argument.isEmpty();
        boolean isArgumentPresentAndValid = false;
        if (!isArgumentEmpty) {
            List<String> commandDescription = List.of(argument.split("\\s+"));
            isArgumentPresentAndValid =
                    commandDescription.stream()
                            .allMatch(word -> ACCEPTED_ARGUMENTS.contains(word));
        }
        return isArgumentEmpty || isArgumentPresentAndValid;
    }
}
