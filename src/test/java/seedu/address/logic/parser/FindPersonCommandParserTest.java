package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPersonCommand;

public class FindPersonCommandParserTest {

    private final FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixes_throwsParseException() {
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "foo " + PREFIX_NAME + "bar",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }

    @Test
    public void parse_inputWithPreamble_throwsParseException() {
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + PREFIX_NAME + " \n Alice \n \t Bob  \t",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindPersonCommand() {
        // no leading and trailing whitespaces
        String userInput1 = PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "Alice Bob";
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // multiple whitespaces between keywords
        String userInput2 = PREAMBLE_WHITESPACE + " " + PREFIX_NAME + " \n Alice \n \t Bob  \t";
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    @Test
    public void parse_validRoleArg_returnsFindPersonCommand() {
        // no leading and trailing whitespaces
        String userInput1 = PREAMBLE_WHITESPACE + " " + PREFIX_ROLE + "organiser";
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // leading and trailing whitespaces
        String userInput2 = PREAMBLE_WHITESPACE + " " + PREFIX_ROLE + "  organiser    ";
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    @Test
    public void parse_validNameAndRoleArgs_returnsFindPersonCommand() {
        String userInput = PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "Alice Bob " + PREFIX_ROLE + "organiser";
        assertDoesNotThrow(() -> parser.parse(userInput), "Parse exception thrown");
    }
}
