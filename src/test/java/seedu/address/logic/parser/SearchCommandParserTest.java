package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BEGIN_DATETIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BEGIN_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_EARLIER_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;


class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();
    private LocalDateTime expectedBegin;
    private LocalDateTime expectedEnd;
    @BeforeEach
    void setUp() {
        expectedBegin = LocalDateTime.of(2024, 10, 10, 0, 0);
        expectedEnd = LocalDateTime.of(2024, 10, 12, 0, 0);
    }
    @Test
    void parse_validInputBothBeginAndEnd_returnsSearchCommand() {
        SearchCommand expectedSearchCommand = new SearchCommand(expectedBegin, expectedEnd);
        assertParseSuccess(parser, BEGIN_DATETIME_INPUT + END_DATETIME_INPUT, expectedSearchCommand);
    }

    @Test
    void parse_validInputOnlyBegin_returnsSearchCommand() {
        SearchCommand expectedSearchCommand = new SearchCommand(expectedBegin, null);
        assertParseSuccess(parser, BEGIN_DATETIME_INPUT, expectedSearchCommand);
    }

    @Test
    void parse_validInputOnlyEnd_returnsSearchCommand() {
        SearchCommand expectedSearchCommand = new SearchCommand(null, expectedEnd);
        assertParseSuccess(parser, END_DATETIME_INPUT, expectedSearchCommand);
    }

    @Test
    void parse_missingBothBeginAndEnd_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, INVALID_BEGIN_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, INVALID_END_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, INVALID_BEGIN_INPUT + INVALID_END_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_endTimeBeforeBegin_throwsParseException() {
        assertParseFailure(parser, INVALID_END_EARLIER_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidInputFormat_throwsParseException() {
        String input1 = NAME_DESC_AMY + END_DATETIME_INPUT;
        String input2 = "abc " + BEGIN_DATETIME_INPUT + END_DATETIME_INPUT;
        assertParseFailure(parser, input1, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, input2, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_wrongNumberBeginOrEndPrefixes_throwsParseException() {
        String input1 = BEGIN_DATETIME_INPUT + BEGIN_DATETIME_INPUT
                + END_DATETIME_INPUT;

        String input2 = BEGIN_DATETIME_INPUT + END_DATETIME_INPUT
                + END_DATETIME_INPUT;

        assertParseFailure(parser, input1, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, input2, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }
}
