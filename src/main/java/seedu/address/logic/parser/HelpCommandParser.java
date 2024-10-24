package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_HELP_KEYWORD;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.edit.EditCommand;
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
    @Override
    public HelpCommand parse(String keyword) throws ParseException {
        String trimmedLowercaseKeyword = keyword.trim().toLowerCase();
        if (!trimmedLowercaseKeyword.isEmpty()
            && !trimmedLowercaseKeyword.equals(AddCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(DeleteCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(EditCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(FindCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(ListCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(HelpCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(ClearCommand.COMMAND_WORD)
            && !trimmedLowercaseKeyword.equals(ExitCommand.COMMAND_WORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_HELP_KEYWORD,
                trimmedLowercaseKeyword));
        }
        return new HelpCommand(trimmedLowercaseKeyword);
    }
}
