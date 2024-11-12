package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_NAME_FORMAT;

import java.util.Arrays;

import seedu.academyassist.logic.commands.FindCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String SPECIAL_CHARACTERS = "-/'";
    private static final String VALIDATION_REGEX = "[a-zA-Z" + SPECIAL_CHARACTERS + "\\s]{1,255}$";

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

        boolean isValidNameFormat = trimmedArgs.matches(VALIDATION_REGEX);

        if (!isValidNameFormat) {
            throw new ParseException(MESSAGE_INVALID_NAME_FORMAT);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
