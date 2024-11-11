package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_AMOUNT;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_DATE;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_DESCRIPTION;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_OTHER_PARTY;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_PARTY;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Transaction;

/**
 * Parses input arguments and creates a new AddTransactionCommand object.
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddTransactionCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddCommandTransaction object for execution.
     * @throws ParseException If the user input does not conform to the expected format and validity checks.
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_AMOUNT,
                        PREFIX_OTHER_PARTY, PREFIX_DATE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.fine("ParseException caused by missing or invalid index.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_AMOUNT,
                PREFIX_OTHER_PARTY, PREFIX_DATE)) {
            logger.fine("ParseException caused by missing command parameters.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DESCRIPTION, PREFIX_AMOUNT,
                PREFIX_OTHER_PARTY, PREFIX_DATE);
        verifyNoBlankValues(argMultimap);

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get().trim();
        double amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get().trim());
        String otherParty = argMultimap.getValue(PREFIX_OTHER_PARTY).get().trim();
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());

        Transaction transaction = new Transaction(description, amount, otherParty, date);

        return new AddTransactionCommand(index, transaction);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Throws a {@code ParseException} If any of the description, amount, other party and date values are blank.
     */
    private static void verifyNoBlankValues(ArgumentMultimap argumentMultimap) throws ParseException {

        String description = argumentMultimap.getValue(PREFIX_DESCRIPTION).get().trim();
        String amount = argumentMultimap.getValue(PREFIX_AMOUNT).get().trim();
        String otherParty = argumentMultimap.getValue(PREFIX_OTHER_PARTY).get().trim();
        String date = argumentMultimap.getValue(PREFIX_DATE).get().trim();

        if (description.isBlank()) {
            logger.fine("ParseException caused by blank description.");
            throw new ParseException(MESSAGE_EMPTY_DESCRIPTION);
        }

        if (amount.isBlank()) {
            logger.fine("ParseException caused by blank amount.");
            throw new ParseException(MESSAGE_EMPTY_AMOUNT);
        }

        if (otherParty.isBlank()) {
            logger.fine("ParseException caused by blank other party.");
            throw new ParseException(MESSAGE_EMPTY_OTHER_PARTY);
        }

        if (date.isBlank()) {
            logger.fine("ParseException caused by blank date.");
            throw new ParseException(MESSAGE_EMPTY_DATE);
        }
    }

}
