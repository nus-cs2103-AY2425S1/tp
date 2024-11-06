package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;

import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;

/**
 * Parses input arguments and creates a new SetThresholdCommand object.
 */
public class SetThresholdCommandParser implements Parser<SetThresholdCommand> {

    public static final String MESSAGE_INVALID_THRESHOLD = "Threshold levels should be positive integers.";
    public static final String MESSAGE_MISSING_THRESHOLD = "Either minimum or maximum stock levels must be provided.";
    public static final String MESSAGE_INVALID_STOCK_VALUE = "Stock level is empty, please provide a value.";

    /**
     * Parses the given {@code String} of arguments in the context of the SetThresholdCommand
     * and returns a SetThresholdCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public SetThresholdCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_PRODUCT_NAME,
                        PREFIX_MIN_STOCK_LEVEL,
                        PREFIX_MAX_STOCK_LEVEL);

        ParserUtil.verifyInput(argMultimap, new Prefix[]{PREFIX_PRODUCT_NAME},
                SetThresholdCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PRODUCT_NAME,
                PREFIX_MIN_STOCK_LEVEL,
                PREFIX_MAX_STOCK_LEVEL);

        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());

        Integer minStockLevel = null;
        Integer maxStockLevel = null;

        // Parse minimum stock level if present
        if (argMultimap.getValue(PREFIX_MIN_STOCK_LEVEL).isPresent()) {
            if (argMultimap.getValue(PREFIX_MIN_STOCK_LEVEL).get().isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_STOCK_VALUE);
            }
            minStockLevel = parsePositiveInteger(
                    argMultimap.getValue(PREFIX_MIN_STOCK_LEVEL).get(), MESSAGE_INVALID_THRESHOLD);
        }

        // Parse maximum stock level if present
        if (argMultimap.getValue(PREFIX_MAX_STOCK_LEVEL).isPresent()) {
            if (argMultimap.getValue(PREFIX_MAX_STOCK_LEVEL).get().isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_STOCK_VALUE);
            }
            maxStockLevel = parsePositiveInteger(
                    argMultimap.getValue(PREFIX_MAX_STOCK_LEVEL).get(), MESSAGE_INVALID_THRESHOLD);
        }

        // Ensure at least one threshold is provided
        if (minStockLevel == null && maxStockLevel == null) {
            throw new ParseException(MESSAGE_MISSING_THRESHOLD);
        }

        return new SetThresholdCommand(productName, minStockLevel, maxStockLevel);
    }

    /**
     * Parses a positive integer from the given string.
     *
     * @param value The string to parse.
     * @param errorMessage The error message to use if parsing fails.
     * @return The parsed positive integer.
     * @throws ParseException If the string is not a positive integer.
     */
    private Integer parsePositiveInteger(String value, String errorMessage) throws ParseException {
        try {
            int intValue = Integer.parseInt(value);
            if (intValue <= 0) {
                throw new ParseException(errorMessage);
            }
            return intValue;
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid stock level: " + errorMessage);
        }
    }
}
