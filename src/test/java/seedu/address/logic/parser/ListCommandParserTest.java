package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_returnsListCommand() {
        try {
            Command result = parser.parse("list");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void parse_throwsParseException() {
        assertParseFailure(parser, "list all", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}