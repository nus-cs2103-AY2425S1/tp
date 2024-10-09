package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.InspectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class InspectCommandParser implements Parser<InspectCommand> {
    public InspectCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InspectCommand.MESSAGE_USAGE), ive);
        }

        return new InspectCommand(index);
    }
}
