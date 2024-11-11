package seedu.eventtory.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.commands.AssignCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignCommand object.
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    @Override
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            Index index = ParserUtil.parseIndex(args);
            return new AssignCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), pe);
        }
    }
}
