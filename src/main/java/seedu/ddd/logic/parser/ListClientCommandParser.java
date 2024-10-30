package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ddd.logic.commands.ListClientCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.predicate.ClientPredicateBuilder;
import seedu.ddd.model.contact.common.predicate.ContactPredicateBuilder;

/**
 * Parses input arguments and creates a new ListClientCommand object
 */
public class ListClientCommandParser implements Parser<ListClientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListClientCommand
     * and returns a ListClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListClientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DESC, FLAG_CLIENT);
        ContactPredicateBuilder predicateBuilder = new ClientPredicateBuilder(argMultimap);
        return new ListClientCommand(predicateBuilder.build());
    }
}
