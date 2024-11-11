package spleetwaise.transaction.logic.parser;

import static spleetwaise.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static spleetwaise.transaction.logic.commands.AddCommand.MESSAGE_USAGE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_STATUS;
import static spleetwaise.transaction.model.transaction.Date.getNowDate;
import static spleetwaise.transaction.model.transaction.Status.NOT_DONE_STATUS;

import java.util.Set;

import spleetwaise.address.logic.parser.ArgumentMultimap;
import spleetwaise.address.logic.parser.ArgumentTokenizer;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.Parser;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Parses input arguments and creates a new transaction AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} argument in the context of the transaction AddCommand and returns an AddCommand
     * object for execution.
     *
     * @param args The string argument to be parsed.
     * @return The AddCommand object to execute.
     * @throws ParseException string argument contains invalid arguments.
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_CATEGORY, PREFIX_STATUS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), e);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS);

        Person person = ParserUtil.getPersonByFilteredPersonListIndex(index);
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(getNowDate()));
        Set<Category> categories = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).orElse(NOT_DONE_STATUS));

        Transaction transaction = new Transaction(person, amount, description, date, categories, status);

        return new AddCommand(transaction);
    }
}
