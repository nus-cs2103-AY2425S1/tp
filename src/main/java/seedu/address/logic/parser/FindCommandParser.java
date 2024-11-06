package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TelContainsNumberPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // instead of tokenizing with prefix for both name and number
        // split by num/ to allow quick search with name
        boolean hasSearchTelArg = trimmedArgs.contains("p/");
        if (hasSearchTelArg) {
            String searchTel = trimmedArgs.replace("p/", "").trim();
            boolean hasInvalidNumberOfInput = searchTel.contains(" ");
            if (hasInvalidNumberOfInput) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.ARG_USAGE));
            }
            boolean isNumeric = searchTel.chars().allMatch(Character::isDigit);
            if (searchTel.isEmpty() || !isNumeric) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.NUM_USAGE));
            }
            return new FindCommand(null,
                    new TelContainsNumberPredicate(searchTel));
        } else {
            // only has name arg
            String[] nameKeywords = trimmedArgs.split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)), null);
        }

    }

}
