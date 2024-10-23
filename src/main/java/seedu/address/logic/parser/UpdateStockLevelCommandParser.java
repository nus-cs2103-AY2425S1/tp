package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;

import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateStockLevelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class UpdateStockLevelCommandParser implements Parser<UpdateStockLevelCommand> {

    public static final String MESSAGE_INVALID_STOCK_LEVEL = "Stock Level should be a positive integer";
    public static final String MESSAGE_INVALID_STOCK = "Names should only contain alphanumeric characters and spaces,"
            + " and it should not be blank";
    /**
     * Parses the given {@code String} of arguments in the context of the SetThresholdCommand
     * and returns an SetThresholdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateStockLevelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateStockLevelCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL);

        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        int currentStockLevel = 0;

        try {
            currentStockLevel = Integer.parseInt(argMultimap.getValue(PREFIX_STOCK_LEVEL).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_STOCK);
        }
        if (currentStockLevel < 0) {
            throw new ParseException(MESSAGE_INVALID_STOCK_LEVEL);
        }
        return new UpdateStockLevelCommand(productName, currentStockLevel);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

