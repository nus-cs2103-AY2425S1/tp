package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewClientCommand object
 */
public class ViewClientCommandParser implements Parser<ViewClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewClientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        // check if args is null
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewClientCommand.MESSAGE_USAGE));
        }
        Name clientName = ParserUtil.parseName(trimmedArgs);
        return new ViewClientCommand(clientName);
    }

}
