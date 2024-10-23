package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDatePredicate;
import seedu.address.model.delivery.DeliveryProductPredicate;
import seedu.address.model.delivery.DeliveryStatusPredicate;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.product.Product;

/**
 * Parses input arguments and creates a new FindDeliveryCommand object.
 */
public class FindDeliveryCommandParser implements Parser<FindDeliveryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDeliveryCommand
     * and returns a FindDeliveryCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindDeliveryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_STATUS, PREFIX_PRODUCT, PREFIX_SUPPLIER_INDEX);

        // Ensure that at least one filter is provided
        if (!argMultimap.getValue(PREFIX_DATETIME).isPresent()
                && !argMultimap.getValue(PREFIX_STATUS).isPresent()
                && !argMultimap.getValue(PREFIX_PRODUCT).isPresent()
                && !argMultimap.getValue(PREFIX_SUPPLIER_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
        }

        List<Predicate<Delivery>> predicates = new ArrayList<>();

        // Parse the date if present
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            String dateString = argMultimap.getValue(PREFIX_DATETIME).get();
            DateTime dateTime;
            try {
                dateTime = ParserUtil.parseDateTime(dateString);
            } catch (IllegalArgumentException e) {
                throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
            }
            predicates.add(new DeliveryDatePredicate(dateTime));
        }

        // Parse the status if present
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String statusString = argMultimap.getValue(PREFIX_STATUS).get();
            Status status;
            try {
                status = Status.valueOf(statusString.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSupplierCommand.MESSAGE_USAGE));
            }
            predicates.add(new DeliveryStatusPredicate(status));
        }

        // Parse the product if present
        if (argMultimap.getValue(PREFIX_PRODUCT).isPresent()) {
            String productString = argMultimap.getValue(PREFIX_PRODUCT).get();
            Product product;
            try {
                product = ParserUtil.parseProduct(productString);
            } catch (IllegalArgumentException e) {
                throw new ParseException(Product.MESSAGE_CONSTRAINTS);
            }

            predicates.add(new DeliveryProductPredicate(product));
        }

        // Parse the supplier index if present
        Optional<SupplierIndex> supplierIndex = Optional.empty();
        if (argMultimap.getValue(PREFIX_SUPPLIER_INDEX).isPresent()) {
            String supplierIndexString = argMultimap.getValue(PREFIX_SUPPLIER_INDEX).get();
            try {
                supplierIndex = Optional.of(ParserUtil.parseSupplierIndex(supplierIndexString));
            } catch (IllegalArgumentException e) {
                throw new ParseException(SupplierIndex.MESSAGE_CONSTRAINTS);
            }
        }

        // Combine all predicates into one using Predicate.and()
        Predicate<Delivery> combinedPredicate = predicates.stream()
                .reduce(Predicate::and)
                .orElse(x -> true); // If no predicates are found, fallback to a true predicate

        return new FindDeliveryCommand(combinedPredicate, supplierIndex);
    }
}

