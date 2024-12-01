package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Throw exception if the string is non alphanumeric except spaces or slashes
        if (!trimmedArgs.matches("^[a-zA-Z0-9\\s/]*$")) {
            throw new ParseException("Names or tags cannot contain non-alphanumeric characters");
        }

        // If the user provides a tag search prefix (e.g., "tag/diabetic"), search by tags
        if (trimmedArgs.startsWith("tag/")) {
            String tagKeywords = trimmedArgs.replace("tag/", "").trim();

            if (tagKeywords.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            // Throw exception if string is non alphanumeric except spaces
            if (!tagKeywords.matches("^[a-zA-Z0-9\\s]*$")) {
                throw new ParseException("Names or tags cannot contain non-alphanumeric characters");
            }

            List<String> tagKeywordList = Arrays.asList(tagKeywords.split("\\s+"));
            return new FindCommand(new TagContainsKeywordsPredicate(tagKeywordList));
        }

        // Throw an error if the user tries to search by BOTH name and tag
        if (trimmedArgs.contains("/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Otherwise, search by name
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = Arrays.asList(trimmedArgs.split("\\s+"));
        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords));
    }
}
