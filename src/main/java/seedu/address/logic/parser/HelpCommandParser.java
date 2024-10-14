package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new HelpCommand();
        }

        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);

        switch (trimmedArgs) {

        case AddCommand.COMMAND_WORD:
            message = AddCommand.MESSAGE_USAGE;
            break;

        case EditCommand.COMMAND_WORD:
            message = EditCommand.MESSAGE_USAGE;
            break;

        case DeleteCommand.COMMAND_WORD:
            message = DeleteCommand.MESSAGE_USAGE;
            break;

        case ClearCommand.COMMAND_WORD:
            message = ClearCommand.MESSAGE_USAGE;
            break;

        case FindCommand.COMMAND_WORD:
            message = FindCommand.MESSAGE_USAGE;
            break;

        case ListCommand.COMMAND_WORD:
            message = ListCommand.MESSAGE_USAGE;
            break;

        case ExitCommand.COMMAND_WORD:
            message = ExitCommand.MESSAGE_USAGE;
            break;

        case HelpCommand.COMMAND_WORD:
            message = HelpCommand.MESSAGE_USAGE;
            break;

        default:
            throw new ParseException(message);
        }

        return new HelpCommand(message);
    }
}
