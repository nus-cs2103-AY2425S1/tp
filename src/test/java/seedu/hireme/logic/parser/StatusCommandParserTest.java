package seedu.hireme.logic.parser;

import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hireme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.commands.StatusCommand;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Test for the StatusCommandParser.
 */
public class StatusCommandParserTest {

    @Test
    public void parse_validArgs_returnsStatusCommand() {
        // Create the parser with the valid status 'ACCEPTED'
        StatusCommandParser parser = new StatusCommandParser(Status.ACCEPTED);

        // Test valid index with valid status
        assertParseSuccess(parser, "1",
                new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED));
    }

    @Test
    public void parse_validArgsDifferentIndex_returnsStatusCommand() {
        // Create the parser with the valid status 'REJECTED'
        StatusCommandParser parser = new StatusCommandParser(Status.REJECTED);

        // Test valid index with different valid status
        assertParseSuccess(parser, "2",
                new StatusCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, Status.REJECTED));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Create the parser with the valid status 'ACCEPTED'
        StatusCommandParser parser = new StatusCommandParser(Status.ACCEPTED);

        // Test invalid index, but valid status
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", expectedMessage);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // Create the parser with the valid status 'PENDING'
        StatusCommandParser parser = new StatusCommandParser(Status.PENDING);

        // Test missing index, but valid status
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // Create the parser with the valid status 'REJECTED'
        StatusCommandParser parser = new StatusCommandParser(Status.REJECTED);

        // Test negative index with valid status
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "-1", expectedMessage);
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        // Create the parser with the valid status 'PENDING'
        StatusCommandParser parser = new StatusCommandParser(Status.PENDING);

        // Test zero index with valid status
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "0", expectedMessage);
    }

    @Test
    public void parse_extraWhitespaceValidArgs_returnsStatusCommand() {
        // Create the parser with the valid status 'ACCEPTED'
        StatusCommandParser parser = new StatusCommandParser(Status.ACCEPTED);

        // Test valid index with extra leading/trailing whitespace
        assertParseSuccess(parser, "   1   ",
                new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED));
    }
}
