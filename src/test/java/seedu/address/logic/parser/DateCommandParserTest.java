package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DateCommand;


public class DateCommandParserTest {
    private DateCommandParser parser = new DateCommandParser();
    private final String nonEmptyDate = "Some date.";

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DateCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }
}
