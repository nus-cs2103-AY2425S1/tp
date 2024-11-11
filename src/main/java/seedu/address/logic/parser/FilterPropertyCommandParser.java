package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
        if (hasExcessToken(argMultimap, args, PREFIX_TYPE, PREFIX_LTE, PREFIX_GTE)) {
            logger.warning("Excess prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterPropertyCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_LTE, PREFIX_GTE)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Wrong prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterPropertyCommand.MESSAGE_USAGE));
        }

        String type = argMultimap.getValue(PREFIX_TYPE).orElse("");
        String lte = argMultimap.getValue(PREFIX_LTE).orElse("");
        String gte = argMultimap.getValue(PREFIX_GTE).orElse("");

        List<String> typeLst = argMultimap.getAllValues(PREFIX_TYPE);
        List<String> lteLst = argMultimap.getAllValues(PREFIX_LTE);
        List<String> gteLst = argMultimap.getAllValues(PREFIX_GTE);

        boolean isTypeEmpty = typeLst.size() == 0;
        boolean isLteEmpty = lteLst.size() == 0;
        boolean isGteEmpty = gteLst.size() == 0;
        if (isTypeEmpty && isLteEmpty && isGteEmpty) {
            logger.warning("Command to filter property contains no boolean expression to evaluate");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));
        }
        if (!isTypeEmpty && !PropertyType.isValidEnumValue(type)) {
            logger.warning(String.format("Invalid Type: %s", type));
            throw new ParseException(
                    String.format(Type.MESSAGE_CONSTRAINTS));
        }
        if (!isLteEmpty && !MatchingPrice.isValidMatchingPrice(lte)) {
            logger.warning(String.format("Invalid upper bound price: %s", lte));
            throw new ParseException(
                    String.format(MatchingPrice.MESSAGE_CONSTRAINTS));
        }
        if (!isGteEmpty && !MatchingPrice.isValidMatchingPrice(gte)) {
            logger.warning(String.format("Invalid lower bound price: %s", gte));
            throw new ParseException(
                    String.format(MatchingPrice.MESSAGE_CONSTRAINTS));
        }

        Type typeObj = null;
        MatchingPrice lteObj = null;
        MatchingPrice gteObj = null;
        if (!isTypeEmpty) {
            typeObj = new Type(typeLst.get(0));
        }
        if (!isLteEmpty) {
            lteObj = new MatchingPrice(lteLst.get(0));
        }
        if (!isGteEmpty) {
            gteObj = new MatchingPrice(gteLst.get(0));
        }
        logger.info(String.format("Successfully parsed filter property command: %s. Sending for execution.", args));
        return new FilterPropertyCommand(typeObj, lteObj, gteObj);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if number of tokens in args string exceeds specified prefixes.
     */
    private boolean hasExcessToken(ArgumentMultimap argumentMultimap, String args, Prefix... prefixes) {
        int prefixPresentCounter = 0;
        for (int i = 0; i < prefixes.length; i++) {
            if (argumentMultimap.getAllValues(prefixes[i]).size() > 0) {
                prefixPresentCounter++;
            }
        }
        String[] splits = args.trim().split("\\s(?=\\S+/)");
        if (splits[0].equals("/")) {
            return false;
        }
        return splits.length > prefixPresentCounter;
    }
}
