package seedu.address.logic.parser.vendor;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.vendor.UnassignVendorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
            Index index = ParserUtil.parseIndex(args);
            return new UnassignVendorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignVendorCommand.MESSAGE_USAGE), pe);
        }

    }

}
