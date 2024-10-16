package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.DisplayCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Name;

/**
 * Parses input arguments and creates a new DisplayCommand object
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns a DisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        try {
            if (args.trim().matches("\\d+")) {
                Index index = ParserUtil.parseIndex(args);
                return new DisplayCommand(index);
            } else {
                Name name = ParserUtil.parseName(args);
                return new DisplayCommand(name);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE), pe);
        }
    }
}
