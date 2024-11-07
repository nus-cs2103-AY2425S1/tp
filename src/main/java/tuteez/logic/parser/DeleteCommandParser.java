package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddLessonCommand;
import tuteez.logic.commands.DeleteCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        validateBasicCommandFormat(args);
        if (args.trim().matches("-?\\d+")) {
            Index index = parsePersonIndex(args);
            return new DeleteCommand(index);
        } else {
            Name name = ParserUtil.parseName(args);
            return new DeleteCommand(name);
        }
    }

    private void validateBasicCommandFormat(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLessonCommand.MESSAGE_USAGE));
        }
    }

    private Index parsePersonIndex(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, args)));
        }
        return index;
    }
}
