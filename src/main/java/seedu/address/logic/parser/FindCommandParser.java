package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
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
        List<String> tempList;

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            tempList = argMultimap.getAllValues(PREFIX_NAME);
            return new FindByNameCommand(new NameContainsKeywordsPredicate(tempList));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            tempList = argMultimap.getAllValues(PREFIX_PHONE);
            return new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(tempList));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tempList = argMultimap.getAllValues(PREFIX_TAG);
            return new FindByTagCommand(new TagContainsKeywordsPredicate(tempList));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            tempList = argMultimap.getAllValues(PREFIX_EMAIL);
            return new FindByEmailCommand(new EmailContainsKeywordsPredicate(tempList));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbstractFindCommand.MESSAGE_USAGE));
    }
}
