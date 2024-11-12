package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PriorityCommand;

public class PriorityCommandParserTest {

    private PriorityCommandParser parser = new PriorityCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        assertParseFailure(parser, "1 l/2 extra",
                String.format(PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL));
    }

    @Test
    public void parse_validResetPriority_success() {
        String userInput = "1 l/reset";
        PriorityCommand expectedCommand = new PriorityCommand(1, 3, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nonIntegerIndex_throwsParseException() {
        // Test with a non-integer index, expecting a ParseException
        assertParseFailure(parser, "abc l/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1 l/2", PriorityCommand.MESSAGE_INVALID_PATIENT_ID);
    }

    @Test
    public void parse_missingLevel_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPriorityPrefix_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraWhitespaces_success() {
        String userInput = "   1    l/2   ";
        PriorityCommand expectedCommand = new PriorityCommand(1, 2, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_incorrectCommandSyntax_throwsParseException() {
        assertParseFailure(parser, "1 l/notANumber",
                PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
    }

    @Test
    public void parse_invalidPriorityLevel_throwsParseException() {
        // Expecting MESSAGE_INVALID_PRIORITY_LEVEL due to invalid level
        assertParseFailure(parser, "1 l/4", PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
        assertParseFailure(parser, "1 l/0", PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
        assertParseFailure(parser, "1 l/abc", PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
        assertParseFailure(parser, "1 l/", PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
    }

    @Test
    public void parse_nonNumericLevel_throwsParseException() {
        assertParseFailure(parser, "1 l/abc",
                PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
    }

    @Test
    public void parse_missingLevelArgument_throwsParseException() {
        assertParseFailure(parser, "1 l/",
                PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL);
    }

    @Test
    public void parse_validPriorityLevelOne_success() {
        String userInput = "1 l/1";
        PriorityCommand expectedCommand = new PriorityCommand(1, 1, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPriorityLevelThree_success() {
        String userInput = "1 l/3";
        PriorityCommand expectedCommand = new PriorityCommand(1, 3, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndexWithReset_success() {
        String userInput = "2 l/reset";
        PriorityCommand expectedCommand = new PriorityCommand(2, 3, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraPrefix_throwsParseException() {
        assertParseFailure(parser, "1 l/2 p/test",
                String.format(PriorityCommandParser.MESSAGE_INVALID_PRIORITY_LEVEL));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "l/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleIndexes_throwsParseException() {
        assertParseFailure(parser, "1 2 l/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePriorityPrefixes_failure() {
        String input = "1 l/2 l/3";

        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

}
