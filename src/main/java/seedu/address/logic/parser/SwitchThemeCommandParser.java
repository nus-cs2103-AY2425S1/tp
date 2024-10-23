package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchThemeCommand object
 */
public class SwitchThemeCommandParser implements Parser<SwitchThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SwitchThemeCommand
     * and returns a SwitchThemeCommand object for execution
     * @param args
     * @return new SwitchThemeCommand
     * @throws ParseException is the user input does not conform to the expected format
     */
    public SwitchThemeCommand parse(String args) throws ParseException {
        try {
            String newTheme = ParserUtil.parseTheme(args);
            return new SwitchThemeCommand(newTheme);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SwitchThemeCommand.MESSAGE_USAGE), pe);
        }
    }
}
