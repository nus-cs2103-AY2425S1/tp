package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByUniversityCommand;
import seedu.address.model.person.UniversityContainsKeywordsPredicate;

public class FindByUniversityCommandParserTest {

    private FindByUniversityCommandParser parser = new FindByUniversityCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Test for empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByUniversityCommand.MESSAGE_USAGE));

        // Test for missing 'u/' prefix
        assertParseFailure(parser, "NUS", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByUniversityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByUniversityCommand() {
        // Test with valid single keyword input
        FindByUniversityCommand expectedCommand =
                new FindByUniversityCommand(new UniversityContainsKeywordsPredicate("NUS"));
        assertParseSuccess(parser, "u/NUS", expectedCommand);

        // Test with leading and trailing spaces
        assertParseSuccess(parser, " u/NUS ", expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // Test with invalid prefix (does not start with 'u/')
        assertParseFailure(parser, "n/NUS", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByUniversityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSpaces_returnsTrimmedFindByUniversityCommand() {
        // Test input with multiple spaces
        FindByUniversityCommand expectedCommand =
                new FindByUniversityCommand(new UniversityContainsKeywordsPredicate("NUS"));
        assertParseSuccess(parser, " u/   NUS   ", expectedCommand);
    }
}
