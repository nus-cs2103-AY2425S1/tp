package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.PayCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;

import java.util.logging.Logger;
import java.util.logging.Level;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayCommand object.
 */
public class PayCommandParser implements Parser<PayCommand> {

    private static final Logger logger = Logger.getLogger(PayCommandParser.class.getName());

    public PayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.log(Level.INFO, "Parsing PayCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HOUR);

        Index index;
        double hour;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Failed to parse index. Invalid command format.", pe);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOUR);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Duplicate hour prefix found in the input.", e);
        }

        if (argMultimap.getValue(PREFIX_HOUR).isPresent()) {
            try {
                hour = ParserUtil.parseHoursPaid(argMultimap.getValue(PREFIX_HOUR).get());
            } catch (ParseException pe) {
                logger.log(Level.WARNING, "Failed to parse hours paid. Invalid value.", pe);
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
            }
        } else {
            logger.log(Level.WARNING, "Failed to parse hours paid. Invalid parameters.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        logger.log(Level.INFO,"Successfully parsed PayCommand.");
        return new PayCommand(index, hour);
    }


}
