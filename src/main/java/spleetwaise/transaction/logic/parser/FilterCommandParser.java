package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.commands.FilterCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT_SIGN;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.function.Predicate;

import spleetwaise.address.logic.parser.ArgumentMultimap;
import spleetwaise.address.logic.parser.ArgumentTokenizer;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.Parser;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.model.FilterCommandPredicate;
import spleetwaise.transaction.model.filterpredicate.AmountFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DateFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.DescriptionFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.PersonFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.StatusFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

//@@author rollingpencil
/**
 * Parses input arguments and creates a new transaction FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    private static void parsePersonFilter(
            ArgumentMultimap argMultimap,
            ArrayList<Predicate<Transaction>> filterSubPredicates
    )
            throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Person person = ParserUtil.getPersonByFilteredPersonListIndex(index);
            filterSubPredicates.add(new PersonFilterPredicate(person));
        }
    }

    private static void parseAmountFilter(
            ArgumentMultimap argMultimap,
            ArrayList<Predicate<Transaction>> filterSubPredicates
    )
            throws ParseException {
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            filterSubPredicates.add(new AmountFilterPredicate(amount));
        }
    }

    private static void parseDescriptionFilter(
            ArgumentMultimap argMultimap,
            ArrayList<Predicate<Transaction>> filterSubPredicates
    )
            throws ParseException {
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            filterSubPredicates.add(new DescriptionFilterPredicate(description));
        }
    }

    private static void parseDateFilter(
            ArgumentMultimap argMultimap,
            ArrayList<Predicate<Transaction>> filterSubPredicates
    ) throws ParseException {
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            filterSubPredicates.add(new DateFilterPredicate(date));
        }
    }

    private static void parseStatusFilter(
            ArgumentMultimap argMultimap,
            ArrayList<Predicate<Transaction>> filterSubPredicates
    )
            throws ParseException {
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
            filterSubPredicates.add(new StatusFilterPredicate(status));
        }
    }

    private static void parseAmountSignFilter(
            ArgumentMultimap argMultimap,
            ArrayList<Predicate<Transaction>> filterSubPredicates
    )
            throws ParseException {
        if (argMultimap.getValue(PREFIX_AMOUNT_SIGN).isPresent()) {
            AmountSignFilterPredicate amtSignPred = ParserUtil.parseAmountSign(
                    argMultimap.getValue(PREFIX_AMOUNT_SIGN).get());
            filterSubPredicates.add(amtSignPred);
        }
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
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS,
                        PREFIX_AMOUNT_SIGN
                );
        if (!ParserUtil.areAnyPrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE,
                PREFIX_STATUS,
                PREFIX_AMOUNT_SIGN
        )
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS,
                PREFIX_AMOUNT_SIGN
        );

        ArrayList<Predicate<Transaction>> filterSubPredicates = new ArrayList<>();

        parsePersonFilter(argMultimap, filterSubPredicates);

        parseAmountFilter(argMultimap, filterSubPredicates);

        parseDescriptionFilter(argMultimap, filterSubPredicates);

        parseDateFilter(argMultimap, filterSubPredicates);

        parseStatusFilter(argMultimap, filterSubPredicates);

        parseAmountSignFilter(argMultimap, filterSubPredicates);

        assert !filterSubPredicates.isEmpty();

        FilterCommandPredicate filterPredicate = new FilterCommandPredicate(filterSubPredicates);

        return new FilterCommand(filterPredicate);
    }
}
