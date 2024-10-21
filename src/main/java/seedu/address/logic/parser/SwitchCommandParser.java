package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SwitchCommandParser implements Parser<SwitchCommand> {
    public SwitchCommand parse(String args) throws ParseException {
        try {
            String profileName = ParserUtil.parseProfileName(args);
            return new SwitchCommand(profileName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }
    }
}
