package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByInterestCommand;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

public class FindByInterestCommandParserTest {

    private FindByInterestCommandParser parser = new FindByInterestCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Test for empty input
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByInterestCommand() {
        // Test with valid single keyword input
        FindByInterestCommand expectedCommand =
                new FindByInterestCommand(new InterestContainsKeywordsPredicate(Arrays.asList("Reading")));
        assertParseSuccess(parser, "i/Reading", expectedCommand); // Valid command with prefix

        // Test with multiple keywords
        expectedCommand =
                new FindByInterestCommand(new InterestContainsKeywordsPredicate(Arrays.asList("Reading", "Writing"))); // Separate keywords
        assertParseSuccess(parser, "i/Reading Writing", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for missing prefix (e.g., 'i/')
        assertParseFailure(parser, "Reading", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSpaces_returnsTrimmedFindByInterestCommand() {
        // Test input with multiple spaces
        FindByInterestCommand expectedCommand =
                new FindByInterestCommand(new InterestContainsKeywordsPredicate
                        (Collections.singletonList("Photography")));
        assertParseSuccess(parser, " i/   Photography   ", expectedCommand);
    }
}
