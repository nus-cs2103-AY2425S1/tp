package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Filter;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FilterCommandParser implements Parser<FilterCommand> {
    public FilterCommand parse(String args) throws ParseException {
        return new FilterCommand();
    }
}
