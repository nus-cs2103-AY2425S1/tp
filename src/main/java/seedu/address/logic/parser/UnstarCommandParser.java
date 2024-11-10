package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnstarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new UnstarCommand object.
 */
public class UnstarCommandParser implements Parser<UnstarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnstarCommand
     * and returns a UnstarCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnstarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstarCommand.MESSAGE_USAGE));
        }


        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new UnstarCommand(index);
        } catch (ParseException pe) {

            if (trimmedArgs.matches("[^\\d]*")) {
                try {
                    Name name = ParserUtil.parseName(trimmedArgs);
                    return new UnstarCommand(name);
                } catch (ParseException pe2) {
                    throw new ParseException(pe2.getMessage(), pe2);
                }
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }
}


