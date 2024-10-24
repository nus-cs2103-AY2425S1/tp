package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        assertParseFailure(parser, " r/123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RoomNumber.MESSAGE_CONSTRAINTS));

        assertParseFailure(parser, " p/47", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Phone.MESSAGE_CONSTRAINTS));
    }

}
