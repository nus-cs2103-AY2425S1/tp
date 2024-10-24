package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_SUPPLIER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.product.exceptions.InvalidMaxStockLevelException;
import seedu.address.model.product.exceptions.InvalidMinStockLevelException;
import seedu.address.model.product.exceptions.InvalidStockLevelException;
import seedu.address.model.product.exceptions.StockLevelOutOfBoundsException;
import seedu.address.model.supplier.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddProductCommand object.
 */
public class AddProductCommandParser implements Parser<AddProductCommand> {

    public static final String MESSAGE_INVALID_STOCK_LEVEL = "Stock levels should be positive integers.";
    public static final String MESSAGE_INVALID_STOCK_RANGE = "Min stock level can't be more than max stock level.";

    /**
     * Parses the given {@code String} of arguments in the context of the AddProductCommand
     * and returns an AddProductCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME,
                PREFIX_STOCK_LEVEL,
                PREFIX_MIN_STOCK_LEVEL,
                PREFIX_MAX_STOCK_LEVEL,
                PREFIX_PRODUCT_SUPPLIER_NAME,
                PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddProductCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes are used
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME,
                PREFIX_STOCK_LEVEL,
                PREFIX_MIN_STOCK_LEVEL,
                PREFIX_MAX_STOCK_LEVEL,
                PREFIX_PRODUCT_SUPPLIER_NAME);

        ProductName name = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // Parse stock levels with default values
        Integer stockLevel = 0; // default stock level
        Integer minStockLevel = 0; // default minimum stock level
        Integer maxStockLevel = Integer.MAX_VALUE; // default maximum stock level

        // Parse stock level if present
        if (argMultimap.getValue(PREFIX_STOCK_LEVEL).isPresent()) {
            stockLevel = parsePositiveInteger(argMultimap.getValue(PREFIX_STOCK_LEVEL).get());
        }

        // Parse minimum stock level if present
        if (argMultimap.getValue(PREFIX_MIN_STOCK_LEVEL).isPresent()) {
            minStockLevel = parsePositiveInteger(argMultimap.getValue(PREFIX_MIN_STOCK_LEVEL).get());
        }

        // Parse maximum stock level if present
        if (argMultimap.getValue(PREFIX_MAX_STOCK_LEVEL).isPresent()) {
            maxStockLevel = parsePositiveInteger(argMultimap.getValue(PREFIX_MAX_STOCK_LEVEL).get());
        }

        // Create StockLevel object
        StockLevel stockLevelObj;
        try {
            stockLevelObj = new StockLevel(stockLevel, minStockLevel, maxStockLevel);
        } catch (InvalidStockLevelException | InvalidMinStockLevelException
                | InvalidMaxStockLevelException | StockLevelOutOfBoundsException e) {
            throw new ParseException(e.getMessage());
        }

        // Create Product with name, stockLevel, and tags
        Product product = new Product(name, stockLevelObj, tagList);

        // Set supplier name if present
        if (argMultimap.getValue(PREFIX_PRODUCT_SUPPLIER_NAME).isPresent()) {
            Name supplierName = ParserUtil.parseName(
                    argMultimap.getValue(PREFIX_PRODUCT_SUPPLIER_NAME).get());
            product.setSupplierName(supplierName);
        }

        return new AddProductCommand(product);
    }

    /**
     * Parses a positive integer from the given string.
     *
     * @param value The string to parse.
     * @return The parsed positive integer.
     * @throws ParseException If the string is not a positive integer.
     */
    private Integer parsePositiveInteger(String value) throws ParseException {
        try {
            int intValue = Integer.parseInt(value);
            if (intValue < 0) {
                throw new ParseException(MESSAGE_INVALID_STOCK_LEVEL);
            }
            return intValue;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_STOCK_LEVEL);
        }
    }

    /**
     * Returns true if the specified prefixes contain non-empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(
            ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
