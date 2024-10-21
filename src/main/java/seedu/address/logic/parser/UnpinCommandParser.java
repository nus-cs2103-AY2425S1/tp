package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnpinCommandParser implements Parser<UnpinCommand> {

    public UnpinCommand parse(String args) throws ParseException {
        try {
            List<Index> indices = ParserUtil.parseIndexes(args);
            return new UnpinCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE), pe);
        }
    }

}
