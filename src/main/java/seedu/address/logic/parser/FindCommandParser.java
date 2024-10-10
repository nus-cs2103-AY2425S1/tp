package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AbstractFindCommand;
import seedu.address.logic.commands.FindByNameCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<AbstractFindCommand> {

    public static final Pattern KEYWORD_EXTRACTOR =
            Pattern.compile("^(?<tag>/[cen])\\s*(?<arguments>[\\S\\s]+)$");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbstractFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim(); // trim space
        Matcher m = KEYWORD_EXTRACTOR.matcher(trimmedArgs); // find tag and search words

        // will throw exception if no args/command format not correct
        if (trimmedArgs.isEmpty() || !m.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // extract tag and search argument
        String tag = m.group("tag");
        String searchTerms = m.group("arguments");
        String[] searchTermArray = searchTerms.split("\\s+");

        // return approppriate FindCommand class depending on tag
        switch (tag) {
        case "/n":
            return new FindByNameCommand(
                    new NameContainsKeywordsPredicate(Arrays.asList(searchTermArray)));
        default:
            return null; // temporary value, this should not occur due to regex
        }
    }
}
