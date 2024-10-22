package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * @param args String of arguments
     * @return a ShowCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] inputs = trimmedArgs.split(" ");
            if (inputs.length != 1 || !trimmedArgs.matches("-?\\d+")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
            }
            return new ShowCommand(new GroupContainsKeywordsPredicate(Arrays.asList(trimmedArgs)));
        } catch (ParseException pe) {
            String errorMessage = String.format("%s \n%s",
                    pe.getMessage(),
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
            throw new ParseException(
                    String.format(errorMessage), pe);
        }
    }
}
