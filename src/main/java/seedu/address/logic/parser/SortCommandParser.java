package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortDateCommand;
import seedu.address.logic.commands.SortDepartmentCommand;
import seedu.address.logic.commands.SortNameCommand;
import seedu.address.logic.commands.SortRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        if (args == null || args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] params = args.trim().split(" ", 2);
        boolean isReversed = false;

        if (params.length == 1) {
            args = params[0].trim();
        } else {
            args = params[0].trim();
            if (params[1].trim().equals(SortCommand.ARGUMENT_WORD_DESC)) {
                isReversed = true;
            } else if (params[1].trim().equals(SortCommand.ARGUMENT_WORD_ASC)) {
                isReversed = false;
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }

        if (args.equals(SortNameCommand.ARGUMENT_WORD)) {
            return new SortNameCommand(isReversed);
        } else if (args.equals(SortDateCommand.ARGUMENT_WORD)) {
            return new SortDateCommand(isReversed);
        } else if (args.equals(SortDepartmentCommand.ARGUMENT_WORD)) {
            return new SortDepartmentCommand(isReversed);
        } else if (args.equals(SortRoleCommand.ARGUMENT_WORD)) {
            return new SortRoleCommand(isReversed);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
