package seedu.address.logic.parser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();;

    @Test
    void parse_validInputBothBeginAndEnd_returnsSearchCommand() throws ParseException {
        String input = " " + PREFIX_BEGIN + "2024-10-10 00:00 " + PREFIX_END + "2024-10-12 00:00";
        LocalDateTime expectedBegin = LocalDateTime.of(2024, 10, 10, 0, 0);
        LocalDateTime expectedEnd = LocalDateTime.of(2024, 10, 12, 0, 0);
        SearchCommand expectedSearchCommand = new SearchCommand(expectedBegin, expectedEnd);
        assertParseSuccess(parser, input, expectedSearchCommand);
    }

    @Test
    void parse_validInputOnlyBegin_returnsSearchCommand() throws ParseException {
        String input = " " + PREFIX_BEGIN + "2024-10-10 00:00";
        LocalDateTime expectedBegin = LocalDateTime.of(2024, 10, 10, 0, 0);
        SearchCommand expectedSearchCommand = new SearchCommand(expectedBegin, null);
        assertParseSuccess(parser, input, expectedSearchCommand);
    }

    @Test
    void parse_validInputOnlyEnd_returnsSearchCommand() throws ParseException {
        String input = " " + PREFIX_END + "2024-10-12 00:00";
        LocalDateTime expectedEnd = LocalDateTime.of(2024, 10, 12, 0, 0);
        SearchCommand expectedSearchCommand = new SearchCommand(null, expectedEnd);
        assertParseSuccess(parser, input, expectedSearchCommand);
    }

    @Test
    void parse_missingBothBeginAndEnd_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        String inputBegin = " " + PREFIX_BEGIN + "10-10-2024 00:00";
        String inputEnd = " " + PREFIX_END + "10-10-2024 00:00";
        String inputBeginEnd = " " + PREFIX_BEGIN + "10-10-2024 00:00" + " " + PREFIX_END + "10-10-2024 01:00";
        assertParseFailure(parser, inputBegin, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, inputEnd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, inputBeginEnd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_endTimeBeforeBegin_throwsParseException() {
        String input = " " + PREFIX_BEGIN + "2024-10-10 00:00 " + PREFIX_END + "2024-10-08 00:00";
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidInputFormat_throwsParseException() {
        String input1 = " " + PREFIX_NAME + "2024-10-10 00:00 " + PREFIX_END + "2024-10-08 00:00";
        String input2 = "abc " + PREFIX_BEGIN + "2024-10-10 00:00 " + PREFIX_END + "2024-10-08 00:00";
        assertParseFailure(parser, input1, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, input2, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_wrongNumberBeginOrEndPrefixes_throwsParseException() {
        String input1 = " " + PREFIX_BEGIN + "2024-10-10 00:00 "
                + PREFIX_BEGIN + "2024-10-11 00:00 "
                + PREFIX_END + "2024-10-12 00:00";

        String input2 = " " + PREFIX_BEGIN + "2024-10-10 00:00 "
                + PREFIX_END + "2024-10-11 00:00 "
                + PREFIX_END + "2024-10-12 00:00";

        assertParseFailure(parser, input1, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, input2, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }
}
