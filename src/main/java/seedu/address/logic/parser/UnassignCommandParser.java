package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnassignCommand object.
 */
public class UnassignCommandParser implements Parser<UnassignCommand> {

    @Override
    public UnassignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        // Check if both vendor and event prefixes are present
        if (!arePrefixesPresent(argMultimap, PREFIX_VENDOR, PREFIX_EVENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);
        Index vendorIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VENDOR).get());
        Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());

        return new UnassignCommand(vendorIndex, eventIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
