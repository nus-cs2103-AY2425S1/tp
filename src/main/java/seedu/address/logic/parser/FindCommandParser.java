package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AbstractFindCommand;
import seedu.address.logic.commands.FindByEmailCommand;
import seedu.address.logic.commands.FindByNameCommand;
import seedu.address.logic.commands.FindByPhoneCommand;
import seedu.address.logic.commands.FindByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<AbstractFindCommand> {

    public static final Pattern KEYWORD_EXTRACTOR =
            Pattern.compile("^(?<type>[pent]/)\\s*(?<arguments>[\\S\\s]+)$");

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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbstractFindCommand.MESSAGE_USAGE));
        }

        // extract tag and search argument
        String tag = m.group("type");
        String[] searchTerms = m.group("arguments").split("\\s+");
        List<String> searchTermArray = Arrays.asList(searchTerms);

        // return appropriate FindCommand class depending on tag
        switch (tag) {
        case "n/":
            return new FindByNameCommand(
                    new NameContainsKeywordsPredicate(searchTermArray));
        case "p/":
            return new FindByPhoneCommand(
                    new PhoneContainsKeywordsPredicate(searchTermArray));
        case "e/":
            return new FindByEmailCommand(
                    new EmailContainsKeywordsPredicate(searchTermArray));
        case "t/":
            return new FindByTagCommand(
                    new TagContainsKeywordsPredicate(searchTermArray));
        default:
            return null; // temporary value, this should not occur due to regex
        }
    }
}
