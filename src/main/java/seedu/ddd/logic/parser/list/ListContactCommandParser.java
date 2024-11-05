package seedu.ddd.logic.parser.list;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.*;

import seedu.ddd.logic.commands.list.ListContactCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.ArgumentTokenizer;
import seedu.ddd.logic.parser.Parser;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.predicate.ContactPredicateBuilder;

/**
 * Parses input arguments and creates a new ListContactCommand object
 */
public class ListContactCommandParser implements Parser<ListContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListContactCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DESC);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_ID, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        ContactPredicateBuilder combinedPredicate = new ContactPredicateBuilder(argMultimap);
        return new ListContactCommand(combinedPredicate.build());
    }
}
