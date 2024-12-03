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
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryWrapper;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.product.Product;

/**
 * Parses input arguments and creates a new AddDeliveryCommand object.
 */
public class AddDeliveryCommandParser implements Parser<AddDeliveryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddDeliveryCommand
     * and returns an AddDeliveryCommand object for execution.
     *
     * @param args Input String parameters provided by user.
     * @return AddDeliveryCommand object.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddDeliveryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_SUPPLIER_INDEX,
                        PREFIX_PRODUCT, PREFIX_QUANTITY, PREFIX_COST);

        if (!hasPrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_SUPPLIER_INDEX,
                PREFIX_PRODUCT, PREFIX_QUANTITY, PREFIX_COST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeliveryCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATETIME, PREFIX_SUPPLIER_INDEX,
                PREFIX_PRODUCT, PREFIX_QUANTITY, PREFIX_COST);

        SupplierIndex supplierIndex = ParserUtil.parseSupplierIndex(argMultimap.getValue(PREFIX_SUPPLIER_INDEX).get());
        Delivery delivery = createDelivery(argMultimap);
        assert delivery != null;

        DeliveryWrapper deliveryWrapper = new DeliveryWrapper(delivery, supplierIndex);
        return new AddDeliveryCommand(deliveryWrapper);
    }

    /**
     * Creates a Delivery object based on the parsed inputs provided by user.
     *
     * @param argMultimap A map that stores mapping of prefixes to their respective arguments.
     * @return A Delivery object.
     * @throws ParseException If any of the parameters provided by the user is invalid.
     */
    public Delivery createDelivery(ArgumentMultimap argMultimap) throws ParseException {
        DateTime deliveryDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        Product product = ParserUtil.parseProduct(argMultimap.getValue(PREFIX_PRODUCT).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Cost cost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get());
        Delivery delivery = new Delivery(product, null, Status.PENDING, deliveryDateTime, cost, quantity);
        return delivery;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values.
     *
     * @param argumentMultimap A map that stores mapping of prefixes to their respective arguments.
     * @param prefixes Prefix that marks the beginning of an argument in an arguments string.
     * @return True if all compulsory parameters are provided by user.
     */
    private static boolean hasPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
