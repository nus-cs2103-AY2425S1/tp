package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        args.trim();
        switch (args.trim()) {
        case AddCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_ADD);

        case EditCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_EDIT);

        case DeleteCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_DELETE);

        case ClearCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_CLEAR);

        case FindCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_FIND);

        case ListCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_LIST);

        case "":
            return new HelpCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

    }
}

