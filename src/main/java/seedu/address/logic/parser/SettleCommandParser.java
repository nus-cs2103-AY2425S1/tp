package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.SettleAmount;


/**
 * Parses input arguments and creates a new SettleCommand object
 */
public class SettleCommandParser implements Parser<SettleCommand> {

    private static final Logger logger = LogsCenter.getLogger(SettleCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the SettleCommand
     * and returns a SettleCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SettleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.info("Parsed index: " + index.getOneBased()); // Log the parsed index
        } catch (ParseException pe) {
            // Log a warning when parsing the index fails
            logger.warning("Failed to parse index from arguments: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            logger.warning("No amount value provided in the arguments: " + args); // Log missing amount prefix
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT);

        SettleAmount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        logger.info("Parsed amount: " + amount); // Log the parsed amount

        logger.info("Successfully parsed SettleCommand with index " + index.getOneBased() + " and amount " + amount);
        return new SettleCommand(index, amount);
    }
}
