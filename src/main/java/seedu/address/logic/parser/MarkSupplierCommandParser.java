package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.supplier.SupplierStatus;

/**
 * Parses input arguments and creates a new MarkSupplierCommand object
 */
public class MarkSupplierCommandParser implements Parser<MarkSupplierCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkSupplierCommand
     * and returns a MarkSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkSupplierCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        SupplierStatus status;
        try {
            String[] parts = args.trim().split("\\s+", 3);
            index = ParserUtil.parseIndex(parts[1]);
            status = ParserUtil.parseSupplierStatus(parts[2]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSupplierCommand.MESSAGE_USAGE));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSupplierCommand.MESSAGE_USAGE));
        }
        return new MarkSupplierCommand(index, status);
    }
}
