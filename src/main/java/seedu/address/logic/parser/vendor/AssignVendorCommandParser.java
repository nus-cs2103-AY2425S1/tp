package seedu.address.logic.parser.vendor;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.vendor.AssignVendorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignVendorCommand object
 */
public class AssignVendorCommandParser implements Parser<AssignVendorCommand> {

    /**
     * Parses the given String of arguments in the context of the AssignVendorCommand
     * and returns a AssignVendorCommand object for execution.
     *
     * @param args the user input string containing the index of the person to be assigned a vendor
     * @return a new {@code AssignVendorCommand} object that contains the parsed index
     * @throws ParseException if input does not conform to the expected format (i.e., invalid index)
     */
    public AssignVendorCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            Index index = ParserUtil.parseIndex(args);
            return new AssignVendorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignVendorCommand.MESSAGE_USAGE), pe);
        }

    }
}
