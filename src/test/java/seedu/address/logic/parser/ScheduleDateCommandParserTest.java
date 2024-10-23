package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleDateCommand;


public class ScheduleDateCommandParserTest {

    private ScheduleDateCommandParser parser = new ScheduleDateCommandParser();

    @Test
    public void parse_validArgs_returnsScheduleDateCommand() {
        // Test with valid date format
        LocalDate expectedDate = LocalDate.of(2024, 10, 12);
        assertParseSuccess(parser, "12-10-2024", new ScheduleDateCommand(expectedDate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test with invalid date format
        assertParseFailure(parser, "2024-10-12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleDateCommand.MESSAGE_USAGE));

        // Test with completely invalid input
        assertParseFailure(parser, "invalidDate", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleDateCommand.MESSAGE_USAGE));

        // Test with missing input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleDateCommand.MESSAGE_USAGE));
    }
}

