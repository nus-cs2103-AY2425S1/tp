package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import java.util.Arrays;
import seedu.address.logic.commands.FindByInterestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByInterestCommand object.
 */
public class FindByInterestCommandParser implements Parser<FindByInterestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindByInterestCommand
     * and returns a FindByInterestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindByInterestCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check if the input is empty or doesn't start with the correct format ("i/")
        if (trimmedArgs.isEmpty() || !trimmedArgs.startsWith("i/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByInterestCommand.MESSAGE_USAGE));
        }

        // Extract the interest keyword after "i/" and trim any unnecessary spaces
        String interestKeyword = trimmedArgs.substring(2).trim();

        // Ensure that the interest keyword is not empty after trimming
        if (interestKeyword.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByInterestCommand.MESSAGE_USAGE));
        }

        // Split the interest keywords by spaces, if there are multiple keywords
        String[] interestKeywords = interestKeyword.split("\\s+");

        // Return a new FindByInterestCommand with the parsed interest keywords
        return new FindByInterestCommand(new InterestContainsKeywordsPredicate(Arrays.asList(interestKeywords)));
    }
}
