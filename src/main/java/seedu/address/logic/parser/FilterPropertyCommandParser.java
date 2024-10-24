package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Ask;
import seedu.address.model.property.MatchingPrice;
import seedu.address.model.property.PropertyType;
import seedu.address.model.property.Type;

/**
 * Parses input arguments and creates a new {@code FilterPropertyCommand} object
 */
public class FilterPropertyCommandParser implements Parser<FilterPropertyCommand> {

    private static final Logger logger = LogsCenter.getLogger(FilterPropertyCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code FilterPropertyCommand}
     * and returns a {@code FilterPropertyCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterPropertyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing filter property command: " + args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_LTE, PREFIX_GTE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TYPE, PREFIX_LTE, PREFIX_GTE);

        String type = argMultimap.getValue(PREFIX_TYPE).orElse("");
        String lte = argMultimap.getValue(PREFIX_LTE).orElse("");
        String gte = argMultimap.getValue(PREFIX_GTE).orElse("");
        if (type.isEmpty() && lte.isEmpty() && gte.isEmpty()) {
            logger.warning("Command to filter property contains no boolean expression to evaluate");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }
        if (!type.isEmpty() && !PropertyType.isValidEnumValue(type)) {
            logger.warning(String.format("Invalid Type: %s", type));
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Type.MESSAGE_CONSTRAINTS));
        }
        if (!lte.isEmpty() && !MatchingPrice.isValidMatchingPrice(lte)) {
            logger.warning(String.format("Invalid upper bound price: %s", lte));
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchingPrice.MESSAGE_CONSTRAINTS));
        }
        if (!gte.isEmpty() && !MatchingPrice.isValidMatchingPrice(gte)) {
            logger.warning(String.format("Invalid lower bound price: %s", gte));
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchingPrice.MESSAGE_CONSTRAINTS));
        }

        Type typeObj = null;
        MatchingPrice lteObj = null;
        MatchingPrice gteObj = null;
        if (!type.isEmpty()) {
            typeObj = new Type(type);
        }
        if (!lte.isEmpty()) {
            lteObj = new MatchingPrice(lte);
        }
        if (!gte.isEmpty()) {
            gteObj = new MatchingPrice(gte);
        }
        logger.info(String.format("Successfully parsed filter property command: %s. Sending for execution.", args));
        return new FilterPropertyCommand(typeObj, lteObj, gteObj);
    }
}
