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
        assertParseFailure(parser, " 1 id 5 level 2 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDeletePriority_success() {
        String userInput = "deletelevel 1";
        PriorityCommand expectedCommand = new PriorityCommand(1, 3, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "abc /level 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1 /level 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingLevel_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_incorrectCommandSyntax_throwsParseException() {
        assertParseFailure(parser, "1 /level notANumber",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
    }
}
