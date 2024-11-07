package seedu.address.logic.parser.vendor;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.vendor.UnassignVendorCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new UnassignVendorCommand object.
 */
public class UnassignVendorCommandParser implements Parser<UnassignVendorCommand> {

    /**
     * Parses the given String of arguments in the context of the UnassignVendorCommand
     * and returns a UnassignVendorCommand object for execution.
     *
     * @param args the user input string containing the index of the person to be unassigned from vendor
     * @return a new {@code UnassignVendorCommand} object that contains the parsed index
     * @throws ParseException if input does not conform to the expected format (i.e., invalid index)
     */
    public UnassignVendorCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            Index index;
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORCE);
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            if (arePrefixesPresent(argMultimap, PREFIX_FORCE)) {
                return new UnassignVendorCommand(index, true);
            } else {
                return new UnassignVendorCommand(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignVendorCommand.MESSAGE_USAGE), pe);
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
