package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.NameMatchesKeywordPredicate;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validName_returnsViewCommand() {
        // no leading and trailing whitespaces
        ViewCommand expectedViewCommand =
                new ViewCommand(null, new NameMatchesKeywordPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedViewCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice Bob  \t", expectedViewCommand);

        // excessive whitespaces between keywords
        assertParseSuccess(parser, " \n Alice       Bob  \t", expectedViewCommand);

        // single keyword
        ViewCommand expectedSingleCommand =
                new ViewCommand(null, new NameMatchesKeywordPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "Alice", expectedSingleCommand);
    }

    @Test
    public void parse_validIndex_returnsViewCommand() {
        // valid one-based index
        ViewCommand expectedCommand = new ViewCommand(INDEX_FIRST_PERSON, null);
        assertParseSuccess(parser, "1", expectedCommand);

        // leading whitespace
        assertParseSuccess(parser, "  1  ", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        // negative index
        assertParseFailure(parser, "-1", expectedMessage);

        // zero index
        assertParseFailure(parser, "0", expectedMessage);

        // decimal number
        assertParseFailure(parser, "1.5", expectedMessage);

        // index with alphabet
        assertParseFailure(parser, "1a", expectedMessage);

        // non-numeric string
        assertParseFailure(parser, "abc1", expectedMessage);
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        // invalid characters in name
        assertParseFailure(parser, "@#$%", expectedMessage);
        assertParseFailure(parser, "Alice123", expectedMessage);
        assertParseFailure(parser, "12Alice", expectedMessage);
        assertParseFailure(parser, "Alice@home", expectedMessage);
    }

    @Test
    public void parse_nullArguments_throwsNullPointerException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, null, expectedMessage);
    }

    @Test
    public void parse_specialCharacters_returnsViewCommand() {
        // name with hyphen
        ViewCommand expectedCommand =
                new ViewCommand(null, new NameMatchesKeywordPredicate(Arrays.asList("Alice-Marie")));
        assertParseSuccess(parser, "Alice-Marie", expectedCommand);

        // name with apostrophe
        ViewCommand expectedApostropheCommand =
                new ViewCommand(null, new NameMatchesKeywordPredicate(Arrays.asList("Bob's")));
        assertParseSuccess(parser, "Bob's", expectedApostropheCommand);

        // multiple names with special characters
        ViewCommand expectedMultiCommand =
                new ViewCommand(null, new NameMatchesKeywordPredicate(Arrays.asList("Alice-Marie", "Bob's")));
        assertParseSuccess(parser, "Alice-Marie Bob's", expectedMultiCommand);
    }
}
