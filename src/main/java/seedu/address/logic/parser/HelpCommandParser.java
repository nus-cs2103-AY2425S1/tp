package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
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
        final String emptyString = "";

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

        case ExitCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_EXIT);

        case emptyString:
            return new HelpCommand();

        default:
            throw new ParseException(HelpCommand.MESSAGE_HELP_NOT_COMMAND_WORD);
        }

    }
}

