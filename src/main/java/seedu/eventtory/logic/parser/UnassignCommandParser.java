package seedu.eventtory.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.commands.UnassignCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnassignCommand object.
 */
public class UnassignCommandParser implements Parser<UnassignCommand> {

    @Override
    public UnassignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnassignCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE), pe);
        }
    }
}
