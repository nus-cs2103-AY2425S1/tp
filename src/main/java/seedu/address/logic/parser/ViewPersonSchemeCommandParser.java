package seedu.address.logic.parser;

import seedu.address.logic.commands.ViewPersonSchemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.commons.core.index.Index;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ViewPersonSchemeCommandParser implements Parser<ViewPersonSchemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewPersonSchemeCommand
     * and returns a ViewPersonSchemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewPersonSchemeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewPersonSchemeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPersonSchemeCommand.MESSAGE_USAGE), pe);
        }
    }
}
