package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.commons.util.NumericUtil;
import seedu.edulog.logic.commands.MarkCommand;
import seedu.edulog.logic.commands.MarkIndexCommand;
import seedu.edulog.logic.commands.MarkNameCommand;
import seedu.edulog.logic.parser.exceptions.ParseException;
import seedu.edulog.model.student.Name;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        try {
            if (NumericUtil.isNumeric(args)) {
                Index index = ParserUtil.parseIndex(args);
                return new MarkIndexCommand(index);
            } else {
                Name name = ParserUtil.parseName(args);
                return new MarkNameCommand(name);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE), pe);
        }
    }

}
