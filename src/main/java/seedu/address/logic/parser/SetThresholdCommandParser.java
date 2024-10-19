package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;
import java.util.stream.Stream;

import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SetThresholdCommandParser implements Parser<SetThresholdCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetThresholdCommand
     * and returns an SetThresholdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetThresholdCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetThresholdCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL);

        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        int StockLevel = Integer.parseInt(argMultimap.getValue(PREFIX_STOCK_LEVEL).get());

        return new SetThresholdCommand(productName, StockLevel);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

