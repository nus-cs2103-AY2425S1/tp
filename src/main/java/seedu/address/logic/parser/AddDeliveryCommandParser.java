package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.delivery.Time;
import seedu.address.model.product.Product;

/**
 * Parses input arguments and creates a new AddDeliveryCommand object
 */
public class AddDeliveryCommandParser implements Parser<AddDeliveryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDeliveryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_SUPPLIER_INDEX,
                        PREFIX_PRODUCT, PREFIX_QUANTITY, PREFIX_COST);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_SUPPLIER_INDEX,
                PREFIX_PRODUCT, PREFIX_QUANTITY, PREFIX_COST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeliveryCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATETIME, PREFIX_SUPPLIER_INDEX,
                PREFIX_PRODUCT, PREFIX_QUANTITY, PREFIX_COST);
        Time deliveryDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        //Pending update from supplier side for name
        //Pending confirmation of use of supplierIndex
        SupplierIndex supplierIndex = ParserUtil.parseSupplierIndex(argMultimap.getValue(PREFIX_SUPPLIER_INDEX).get());
        Product product = ParserUtil.parseProduct(argMultimap.getValue(PREFIX_PRODUCT).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Cost cost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get());
        Delivery delivery = new Delivery(product, null, Status.PENDING, deliveryDateTime, cost, quantity,
                supplierIndex);
        return new AddDeliveryCommand(delivery);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
