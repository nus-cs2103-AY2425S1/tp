package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        // Filtering by multiple fields/flags has not been implemented yet
        long numberOfFiltersUsed = countPrefixesUsed(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS);
        if (numberOfFiltersUsed > 1) {
            throw new ParseException(FilterCommand.MULTIPLE_FILTERS_NOT_IMPLEMENTED);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String keyword = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            return new FilterCommand(new NameContainsKeywordsPredicate(keyword));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String keyword = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()).value;
            return new FilterCommand(new PhoneContainsKeywordsPredicate(keyword));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String keyword = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()).value;
            return new FilterCommand(new EmailContainsKeywordsPredicate(keyword));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String keyword = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()).value;
            return new FilterCommand(new AddressContainsKeywordsPredicate(keyword));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Counts the number of prefixes used in the given {@code ArgumentMultimap}.
     *
     * @param argMultimap The ArgumentMultimap containing the parsed arguments.
     * @param prefixes The prefixes to check in the argument map.
     * @return The number of prefixes that are present in the argument map.
     */
    private long countPrefixesUsed(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .filter(prefix -> argMultimap.getValue(prefix).isPresent())
                .count();
    }
}
