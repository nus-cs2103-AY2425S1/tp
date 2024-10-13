package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameAndClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution, n/ input finds for name, c/ input finds
     * for classId
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.contains(PREFIX_NAME.toString()) && trimmedArgs.contains(PREFIX_CLASSID.toString())) {
            String[] nameKeywords = ParserUtil.parseNameClassIdFind(trimmedArgs);
            String[] classIdKeywords = ParserUtil.parseClassIdFind(trimmedArgs);
            return new FindCommand(new NameAndClassIdContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                    Arrays.asList(classIdKeywords)));
        }

        if (trimmedArgs.contains(PREFIX_NAME.toString())) {
            String[] nameKeywords = ParserUtil.parseNameFind(trimmedArgs);
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        String[] classIdKeywords = ParserUtil.parseClassIdFind(trimmedArgs);
        return new FindCommand(new ClassIdContainsKeywordsPredicate(Arrays.asList(classIdKeywords)));

    }

}
