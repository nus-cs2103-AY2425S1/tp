package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;

import seedu.address.logic.commands.UpdateStockLevelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;

/**
 * Parses input arguments and creates a new UpdateStockLevelCommand object
 */
public class UpdateStockLevelCommandParser implements Parser<UpdateStockLevelCommand> {

    public static final String MESSAGE_INVALID_STOCK_LEVEL = "Stock Level should be a non-negative integer.";

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateStockLevelCommand
     * and returns an UpdateStockLevelCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateStockLevelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL);

        ParserUtil.verifyInput(argMultimap, new Prefix[]{PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL},
                UpdateStockLevelCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL);

        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());

        UpdateStockLevelCommand resultCurrentStock;
        resultCurrentStock = parseCurrentStockLevelCommand(argMultimap, productName);

        return resultCurrentStock;
    }

    private UpdateStockLevelCommand parseCurrentStockLevelCommand(ArgumentMultimap argMap, ProductName productName)
            throws ParseException {
        try {
            String stockLevelString = argMap.getValue(PREFIX_STOCK_LEVEL).get().trim();

            if (stockLevelString.isEmpty()) {
                throw new ParseException("Stock Level not provided!");
            }

            int currentStockLevel = Integer.parseInt(stockLevelString);

            if (currentStockLevel < 0) {
                throw new ParseException(MESSAGE_INVALID_STOCK_LEVEL);
            }

            return new UpdateStockLevelCommand(productName, currentStockLevel);
        } catch (NumberFormatException e) {
            throw new ParseException("Value for stock level is Invalid" + "\nKindly enter a valid number");
        }
    }

}
