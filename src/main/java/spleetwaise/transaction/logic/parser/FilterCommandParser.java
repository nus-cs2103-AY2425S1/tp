package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.commands.FilterCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import spleetwaise.address.logic.parser.ArgumentMultimap;
import spleetwaise.address.logic.parser.ArgumentTokenizer;
import spleetwaise.address.logic.parser.Prefix;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.Parser;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.model.FilterCommandPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;

/**
 * Parses input arguments and creates a new transaction FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Returns true if any of the prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} argument in the context of the transaction FilterCommand and returns an
     * FilterCommand object for execution.
     *
     * @param args The string argument to be parsed.
     * @return The FilterCommand object to execute.
     * @throws ParseException string argument contains invalid arguments.
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS);
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS)
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS);

        Person person = null;
        if (!argMultimap.getPreamble().isEmpty()) {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            person = ParserUtil.getPersonByFilteredPersonListIndex(index);
        }

        Amount amount = null;
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        }

        Description description = null;
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }

        Date date = null;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }

        Status status = null;
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        }

        assert person != null || amount != null || description != null || date != null || status != null;

        FilterCommandPredicate filterPredicate = new FilterCommandPredicate(person, amount, description, date, status);

        return new FilterCommand(filterPredicate);
    }
}
