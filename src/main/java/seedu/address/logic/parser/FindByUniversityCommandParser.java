package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindByUniversityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.UniversityContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new {@code FindByUniversityCommand} object.
 */
public class FindByUniversityCommandParser implements Parser<FindByUniversityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code FindByUniversityCommand}
     * and returns a {@code FindByUniversityCommand} object for execution.
     *
     * @param args The arguments provided by the user.
     * @return A new {@code FindByUniversityCommand} object based on the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public FindByUniversityCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check if the input is empty or doesn't start with the correct format ("u/")
        if (trimmedArgs.isEmpty() || !trimmedArgs.startsWith("u/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByUniversityCommand.MESSAGE_USAGE));
        }

        // Extract the university keyword after "u/" and trim any unnecessary spaces
        String universityKeyword = trimmedArgs.substring(2).trim();

        // Ensure that the university keyword is not empty after trimming
        if (universityKeyword.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByUniversityCommand.MESSAGE_USAGE));
        }

        // Return a new FindByUniversityCommand with the parsed university keyword
        return new FindByUniversityCommand(
                new UniversityContainsKeywordsPredicate(universityKeyword)
        );
    }


}

