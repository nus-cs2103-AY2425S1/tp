package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input and creates a new HelpCommand object
 */
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

        case AddAppointmentCommand.COMMAND_WORD:
            message = AddAppointmentCommand.MESSAGE_USAGE;
            break;

        case EditCommand.COMMAND_WORD:
            message = EditCommand.MESSAGE_USAGE;
            break;

        case DeleteCommand.COMMAND_WORD:
            message = DeleteCommand.MESSAGE_USAGE;
            break;

        case DeleteAppointmentCommand.COMMAND_WORD:
            message = DeleteAppointmentCommand.MESSAGE_USAGE;
            break;

        case ClearCommand.COMMAND_WORD:
            message = ClearCommand.MESSAGE_USAGE;
            break;

        case FindCommand.COMMAND_WORD:
            message = FindCommand.MESSAGE_USAGE;
            break;

        case GetCommand.COMMAND_WORD:
            message = GetCommand.MESSAGE_USAGE;
            break;

        case ListCommand.COMMAND_WORD:
            message = ListCommand.MESSAGE_USAGE;
            break;

        case ListAppointmentCommand.COMMAND_WORD:
            message = ListAppointmentCommand.MESSAGE_USAGE;
            break;

        case UndoCommand.COMMAND_WORD:
            message = UndoCommand.MESSAGE_USAGE;
            break;

        case ExitCommand.COMMAND_WORD:
            message = ExitCommand.MESSAGE_USAGE;
            break;

        case HelpCommand.COMMAND_WORD:
            message = HelpCommand.MESSAGE_USAGE;
            break;

        case StatisticsCommand.COMMAND_WORD:
            message = StatisticsCommand.MESSAGE_USAGE;
            break;

        default:
            throw new ParseException(message);
        }

        return new HelpCommand(message);
    }
}
