package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ddd.logic.commands.ListEventCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.event.common.predicate.EventPredicateBuilder;

/**
 * Parses input arguments and creates a new ListEventCommand object
 */
public class ListEventCommandParser implements Parser<ListEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListEventCommand
     * and returns a ListEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DESC);
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        return new ListEventCommand(predicateBuilder.build());
    }
}
