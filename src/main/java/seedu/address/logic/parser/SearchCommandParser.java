package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            List<String> keywordArguments = argMultimap.getAllValues(PREFIX_ADDRESS);
            List<String> keywords = ParserUtil.parseSearchKeywords(keywordArguments);
            return new SearchCommand(new AddressContainsKeywordsPredicate(keywords));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> keywordArguments = argMultimap.getAllValues(PREFIX_EMAIL);
            List<String> keywords = ParserUtil.parseSearchKeywords(keywordArguments);
            return new SearchCommand(new EmailContainsKeywordsPredicate(keywords));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> keywordArguments = argMultimap.getAllValues(PREFIX_NAME);
            List<String> keywords = ParserUtil.parseSearchKeywords(keywordArguments);
            return new SearchCommand(new NameContainsKeywordsPredicate(keywords));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> keywordArguments = argMultimap.getAllValues(PREFIX_PHONE);
            List<String> keywords = ParserUtil.parseSearchKeywords(keywordArguments);
            return new SearchCommand(new PhoneContainsKeywordsPredicate(keywords));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> keywordArguments = argMultimap.getAllValues(PREFIX_TAG);
            List<String> keywords = ParserUtil.parseSearchKeywords(keywordArguments);
            return new SearchCommand(new TagContainsKeywordsPredicate(keywords));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }
}
