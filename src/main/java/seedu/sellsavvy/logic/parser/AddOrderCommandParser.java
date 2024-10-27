package seedu.sellsavvy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;

import java.util.stream.Stream;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;

/**
 * Parses input arguments and creates a new AddOrderCommand object.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    private static final Quantity DEFAULT_QUANTITY = new Quantity("1");

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public AddOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM, PREFIX_DATE, PREFIX_QUANTITY);

        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_ITEM, PREFIX_DATE)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddOrderCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ITEM, PREFIX_DATE, PREFIX_QUANTITY);
        Item item = ParserUtil.parseItem(argMultimap.getValue(PREFIX_ITEM).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Quantity quantity = parseQuantityValue(argMultimap);

        Order order = new Order(item, quantity, date, Status.PENDING);

        return new AddOrderCommand(index, order);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given.
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses for the {@code Quantity} value. If the quantity value is not provided,
     * returns a {@code Quantity} with a value of 1.
     */
    private static Quantity parseQuantityValue(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            return DEFAULT_QUANTITY;
        }

        return ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
    }
}
