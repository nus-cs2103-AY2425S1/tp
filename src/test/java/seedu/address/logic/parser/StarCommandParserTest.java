package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_SPECIAL_CHARACTERS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.StarCommand;
import seedu.address.model.person.Name;

public class StarCommandParserTest {
    private StarCommandParser parser = new StarCommandParser();

    @Test
    public void parse_validName_returnsStarCommand() {
        String userInput = VALID_NAME_AMY;
        StarCommand expectedCommand = new StarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = VALID_NAME_BOB;
        expectedCommand = new StarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validSingleCharacterName_returnsStarCommand() {
        // one char name
        String userInput = "A";
        StarCommand expectedCommand = new StarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMixedCaseName_returnsStarCommand() {
        // name with uppercase and lowercase letters
        String userInput = "aLiCe pAuLiNe";
        StarCommand expectedCommand = new StarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validLongName_returnsStarCommand() {
        // long name
        String userInput = "A".repeat(100);
        StarCommand expectedCommand = new StarCommand(new Name(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndex_returnsStarCommand() {
        String userInput = "1"; // Assuming 1 is a valid index
        StarCommand expectedCommand = new StarCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndexWithSpaces_returnsStarCommand() {
        String userInput = "  1  "; // Assuming 1 is a valid index
        StarCommand expectedCommand = new StarCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidEmptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidWhiteSpaceArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // Assuming names cannot contain special characters, like '@'
        assertParseFailure(parser, "John @ Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_SPECIAL_CHARACTERS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test with a negative index
        assertParseFailure(parser, "-1",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Test with zero index
        assertParseFailure(parser, "0",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Test with minimum integer index
        assertParseFailure(parser, String.valueOf(Integer.MIN_VALUE),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }
}
