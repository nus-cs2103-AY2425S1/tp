package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Message to be displayed when invalid keyword is given.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "Keywords should only contain alphanumeric characters and spaces, and it should not be blank";
    private static final String VALIDATION_REGEX = "^.+$";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_KEYWORD);

        if (!isPrefixPresent(argMultimap, PREFIX_KEYWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        //Keep as list now for future keywords
        List<String> keywordList = new ArrayList<>();

        if (isKeywordPresent(argMultimap, PREFIX_KEYWORD)) {
            String keyword = argMultimap.getValue(PREFIX_KEYWORD).get();
            requireNonNull(keyword);
            String trimmedKeyword = keyword.trim();

            if (!isValidKeyword(trimmedKeyword)) {
                throw new ParseException(MESSAGE_CONSTRAINTS);
            }

            keywordList.add(trimmedKeyword);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(keywordList),
                new PhoneContainsKeywordsPredicate(keywordList),
                new EmailContainsKeywordsPredicate(keywordList),
                new RentalInformationContainsKeywordsPredicate(keywordList));
    }

    private boolean isValidKeyword(String keyword) {
        return keyword.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if no prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean isKeywordPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
