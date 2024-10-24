package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "1", new ViewCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "3", new ViewCommand(INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsViewCommand() {
        assertParseSuccess(parser, "     1", new ViewCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "3    ", new ViewCommand(INDEX_THIRD_PERSON));
        assertParseSuccess(parser, " \n 2 \t ", new ViewCommand(INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_nonIntegerArgs_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "hello", expectedErrorMessage);
        assertParseFailure(parser, " n/Alex Yeoh ", expectedErrorMessage);
        assertParseFailure(parser, " 1.2 ", expectedErrorMessage);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedErrorMessage);
        assertParseFailure(parser, "   ", expectedErrorMessage);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 2", expectedErrorMessage);
        assertParseFailure(parser, " 1 1 ", expectedErrorMessage);
    }
}
