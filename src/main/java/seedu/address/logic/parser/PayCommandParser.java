package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.PayCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;

import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayCommand object.
 */
public class PayCommandParser implements Parser<PayCommand> {

    private static final Logger logger = Logger.getLogger(PayCommandParser.class.getName());

    /**
     * Parses input arguments and creates a new PayCommand object.
     */
    public PayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing PayCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HOUR);

        Index index;
        double hour;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.warning("Failed to parse index. Invalid command format.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOUR);

        if (argMultimap.getValue(PREFIX_HOUR).isPresent()) {
            hour = ParserUtil.parseHour(argMultimap.getValue(PREFIX_HOUR).get());
        } else {
            logger.warning("Failed to parse hours paid. Invalid parameters.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        logger.info("Successfully parsed PayCommand.");
        return new PayCommand(index, hour);
    }


}
