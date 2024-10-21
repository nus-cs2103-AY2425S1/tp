package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PinCommandParser implements Parser<PinCommand> {

    public PinCommand parse(String args) throws ParseException {
        try {
            List<Index> indices = ParserUtil.parseIndexes(args);
            return new PinCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE), pe);
        }
    }

}
