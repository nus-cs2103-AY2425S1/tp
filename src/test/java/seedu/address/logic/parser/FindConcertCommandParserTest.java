package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindConcertCommand;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.NameContainsKeywordsPredicate;


public class FindConcertCommandParserTest {

    private final FindConcertCommandParser parser = new FindConcertCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixes_throwsParseException() {
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "foo " + PREFIX_NAME + "bar",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }

    @Test
    public void parse_inputWithPreamble_success() {
        FindConcertCommand expecteFindConcertCommand =
                new FindConcertCommand(new NameContainsKeywordsPredicate<>(List.of("missing")));
        assertParseSuccess(parser,
                PREAMBLE_NON_EMPTY + " " + PREFIX_NAME + " \n missing \t",
                expecteFindConcertCommand);
    }

    @Test
    public void parse_invalidNameArgs_throwsParseException() {
        // empty name field
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "",
                String.format(Name.MESSAGE_CONSTRAINTS));

        // non-alphanumeric characters in name field
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "foo*",
                String.format(Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validNameArgs_returnsFindConcertCommand() {
        FindConcertCommand expectedFindCommand = new FindConcertCommand(
                new NameContainsKeywordsPredicate<>(List.of("Alice", "Bob")));
        // no leading and trailing whitespaces
        String userInput1 = PREAMBLE_WHITESPACE + " " + PREFIX_NAME + "Alice Bob";
        assertParseSuccess(parser,
                userInput1,
                expectedFindCommand);

        // multiple whitespaces between keywords
        String userInput2 = PREAMBLE_WHITESPACE + " " + PREFIX_NAME + " \n Alice \n \t Bob  \t";
        assertParseSuccess(parser,
                userInput1,
                expectedFindCommand);
    }
}

