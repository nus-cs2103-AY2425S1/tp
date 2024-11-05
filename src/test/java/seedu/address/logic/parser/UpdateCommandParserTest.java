package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_noIndex_throwsParseException() {
        assertParseFailure(parser, "update -n NewEvent123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "update -i 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "update -i 1 -sd 2023-1-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDate_returnsParseCommand() {
        assertParseSuccess(parser, "update -i 1 -sd 2025-01-01",
                new UpdateCommand("", LocalDate.of(2025, 1, 1), null,
                        null, new HashSet<>(), new HashSet<>(), INDEX_FIRST_EVENT));
    }

}
