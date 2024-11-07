package seedu.address.logic.parser;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.ViewProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductNameContainsKeywordsPredicate;
import seedu.address.model.product.ProductSupplierNamePredicate;
import seedu.address.model.product.ProductTagsPredicate;

/**
 * Parses input arguments and creates a new ViewProductCommand object
 */
public class ViewProductCommandParser implements Parser<ViewProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewProductCommand
     * and returns a ViewProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewProductCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ViewProductCommand(PREDICATE_SHOW_ALL_PRODUCTS, null);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME,
                    CliSyntax.PREFIX_TAG, CliSyntax.PREFIX_SUPPLIER_NAME, CliSyntax.PREFIX_SORT);

        String nameKeyword = argMultimap.getValue(CliSyntax.PREFIX_NAME).orElse("");
        List<String> nameKeywords = nameKeyword.isEmpty() ? new ArrayList<>() : List.of(nameKeyword);
        List<String> tags = argMultimap.getAllValues(CliSyntax.PREFIX_TAG);
        String supplierName = argMultimap.getValue(CliSyntax.PREFIX_SUPPLIER_NAME).orElse(null);
        Boolean sortAscending = null;

        if (argMultimap.getValue(CliSyntax.PREFIX_SORT).isPresent()) {
            String sortValue = argMultimap.getValue(CliSyntax.PREFIX_SORT).get();
            if (sortValue.equals("i")) {
                sortAscending = true;
            } else if (sortValue.equals("d")) {
                sortAscending = false;
            } else {
                throw new ParseException("Invalid sort value. Use 'i' for increasing or 'd' for decreasing.");
            }
        }

        Predicate<Product> predicate = product -> true;

        if (!nameKeywords.isEmpty()) {
            predicate = predicate.and(new ProductNameContainsKeywordsPredicate(nameKeywords));
        }

        if (!tags.isEmpty()) {
            predicate = predicate.and(new ProductTagsPredicate(tags));
        }

        if (supplierName != null) {
            predicate = predicate.and(new ProductSupplierNamePredicate(supplierName));
        }

        return new ViewProductCommand(predicate, sortAscending);
    }
}
