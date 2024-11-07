package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_ARGUMENTS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NUMBER_OF_ARGS;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    private static final String VALIDATION_REGEX = "[^\\s].*";

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * @param args String of arguments
     * @return a ShowCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        try {
            // Trim and prepare arguments
            String trimmedArgs = args.trim();
            String[] inputs = trimmedArgs.split("\\s+");
            List<String> keywords = Arrays.asList(inputs);
            // Check if arguments are empty
            if (inputs[0].isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_NUMBER_OF_ARGS);
            }
            if (!trimmedArgs.matches(VALIDATION_REGEX)) {
                throw new ParseException(MESSAGE_INVALID_ARGUMENTS);
            }
            return new ShowCommand(new GroupContainsKeywordsPredicate(keywords));
        } catch (ParseException pe) {
            String errorMessage = String.format("%s \n%s",
                    pe.getMessage(),
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
            throw new ParseException(
                    String.format(errorMessage), pe);
        }
    }
}
