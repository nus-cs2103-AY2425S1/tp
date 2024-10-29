package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByMajorCommand;
import seedu.address.model.person.MajorContainsKeywordsPredicate;

public class FindByMajorCommandParserTest {

    private FindByMajorCommandParser parser = new FindByMajorCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Test for empty input
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByMajorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByMajorCommand() {
        // Test with valid keyword input
        FindByMajorCommand expectedCommand =
                new FindByMajorCommand(new MajorContainsKeywordsPredicate("Computer Science"));

        assertParseSuccess(parser, "m/Computer Science", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for missing prefix (e.g., 'm/')
        assertParseFailure(parser, "Computer Science", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByMajorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSpaces_returnsTrimmedFindByMajorCommand() {
        // Test input with multiple spaces
        FindByMajorCommand expectedCommand =
                new FindByMajorCommand(new MajorContainsKeywordsPredicate("Computer Science"));

        assertParseSuccess(parser, "  m/Computer Science   ", expectedCommand);
    }

    @Test
    public void parse_leadingSpaces_returnsTrimmedFindByMajorCommand() {
        // Test input with leading spaces
        FindByMajorCommand expectedCommand =
                new FindByMajorCommand(new MajorContainsKeywordsPredicate("Computer Science"));

        assertParseSuccess(parser, " m/Computer Science", expectedCommand);
    }

    @Test
    public void parse_trailingSpaces_returnsTrimmedFindByMajorCommand() {
        // Test input with trailing spaces
        FindByMajorCommand expectedCommand =
                new FindByMajorCommand(new MajorContainsKeywordsPredicate("Computer Science"));

        assertParseSuccess(parser, "m/Computer Science ", expectedCommand);
    }
}
