package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_INPUT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.logic.commands.AssignProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;

/**
 * Parses input arguments and creates a new AssignProductCommand object
 */
public class AssignProductCommandParser implements Parser<AssignProductCommand> {
    private static final Logger logger = Logger.getLogger(AssignProductCommandParser.class.getName());
    private static final Set<Prefix> ALLOWED_PREFIXES = Set.of(PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);
    /**
     * Parses the given {@code String} of arguments in the context of the AssignProductCommand
     * and returns a AssignProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignProductCommand parse(String args) throws ParseException {

        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_INPUT);
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);
        logAllPrefixes(argMultimap);
        verifyInput(argMultimap);
        String productNameStr = argMultimap.getValue(PREFIX_PRODUCT_NAME).get();
        String supplierNameStr = argMultimap.getValue(PREFIX_SUPPLIER_NAME).get();

        ProductName productName = ParserUtil.parseProductName(productNameStr);
        Name supplierName = ParserUtil.parseName(supplierNameStr);

        return new AssignProductCommand(productName, supplierName);
    }

    private void verifyInput(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);
        // Unified error message for preamble content, missing prefixes, and extra prefixes
        if (!argMultimap.getPreamble().isEmpty()
                || !arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME)
                || hasExtraPrefixes(argMultimap, ALLOWED_PREFIXES)) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProductCommand.MESSAGE_USAGE));
        }
    }

    private boolean hasExtraPrefixes(ArgumentMultimap argMultimap, Set<Prefix> allowedPrefixes) {
        Set<Prefix> presentPrefixes = argMultimap.getAllPrefixes();

        for (Prefix prefix : presentPrefixes) {
            List<String> values = argMultimap.getAllValues(prefix);

            // Check if any value contains an extra "/" which may indicate an extra prefix
            for (String value : values) {
                if (value.contains("/")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Logs all prefixes found in the ArgumentMultimap along with their values.
     */
    private void logAllPrefixes(ArgumentMultimap argMultimap) {
        Set<Prefix> prefixes = argMultimap.getAllPrefixes();
        for (Prefix prefix : prefixes) {
            String values = argMultimap.getAllValues(prefix).toString();
            logger.log(Level.INFO, "Prefix: " + prefix.getPrefix() + " - Values: " + values);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
