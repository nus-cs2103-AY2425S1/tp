package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        if (isNumber(trimmedArgs)) {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new ViewCommand(index);
        }

        if (Name.isValidName(trimmedArgs)) {
            Name name = ParserUtil.parseName(trimmedArgs);
            return new ViewCommand(name);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

    }

    private boolean isNumber(String index) {
        return index.matches("-?\\d+");
    }
}
