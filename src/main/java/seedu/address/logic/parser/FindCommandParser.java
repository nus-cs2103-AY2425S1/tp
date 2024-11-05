package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameOrPhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    public static final String MESSAGE_CONSTRAINTS =
            "Use only letters, numbers, and spaces in keywords to match names or phone numbers.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

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
        if (!isValidKeyword(trimmedArgs)) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameOrPhoneContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Returns true if the keywords contains only alphanumeric characters or spaces.
     */
    public boolean isValidKeyword(String keywords) {
        return keywords.matches(VALIDATION_REGEX);
    }

}
