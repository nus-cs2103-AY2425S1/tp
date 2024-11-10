package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StarCommand;
import seedu.address.logic.commands.UnstarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new StarCommand object.
 */
public class StarCommandParser implements Parser<StarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StarCommand
     * and returns a StarCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public StarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
        }

        if (isNumber(trimmedArgs)) {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new StarCommand(index);
        }

        if (Name.isValidName(trimmedArgs)) {
            Name name = ParserUtil.parseName(trimmedArgs);
            return new StarCommand(name);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
    }

    private boolean isNumber(String index) {
        return index.matches("-?\\d+");
    }
}

