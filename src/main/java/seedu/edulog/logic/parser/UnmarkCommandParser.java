package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.edulog.commons.core.index.Index;
import seedu.edulog.logic.commands.UnmarkCommand;
import seedu.edulog.logic.commands.UnmarkIndexCommand;
import seedu.edulog.logic.commands.UnmarkNameCommand;
import seedu.edulog.logic.parser.exceptions.ParseException;
import seedu.edulog.model.student.Name;

/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns a UnmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        try {
            if (ParserUtil.isNumeric(args)) {
                Index index = ParserUtil.parseIndex(args);
                return new UnmarkIndexCommand(index);
            } else {
                Name name = ParserUtil.parseName(args);
                return new UnmarkNameCommand(name);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE), pe);
        }
    }

}
