package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

/**
 * Parses input arguments and creates a new SettleCommand object
 */
public class SettleCommandParser implements Parser<SettleCommand>{

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
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT);

        double amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        return new SettleCommand(index, amount);
    }
}
