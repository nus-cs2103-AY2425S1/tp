package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SchemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
public class SchemeCommandParser implements Parser<SchemeCommand> {

    public SchemeCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty() || !SchemeCommandParser.isNumber(trimmedArgs)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new SchemeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE), pe);
        }
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }
}
