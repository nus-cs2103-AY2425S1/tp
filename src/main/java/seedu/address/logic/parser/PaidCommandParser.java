package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaidCommand;
import seedu.address.logic.commands.PaidCommand.PaidPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new PaidCommand object
 */
public class PaidCommandParser implements Parser<PaidCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PaidCommand
     * and returns a PaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PaidCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FREQUENCY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_FREQUENCY).isPresent()) {
            throw new ParseException(PaidCommand.MESSAGE_NO_FREQUENCY);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FREQUENCY);
        PaidPersonDescriptor paidPersonDescriptor = new PaidPersonDescriptor();
        paidPersonDescriptor.setHasPaid();
        paidPersonDescriptor.setFrequency(ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get()));
        return new PaidCommand(index, paidPersonDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
