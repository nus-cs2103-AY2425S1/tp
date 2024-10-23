package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalTags.VALID_TAG_BRIDES_FRIEND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.TagCommand;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG + VALID_TAG_BRIDES_FRIEND.getTagName();
        System.out.println(userInput);
        TagCommand expectedCommand = new TagCommand(INDEX_FIRST_PERSON, VALID_TAG_BRIDES_FRIEND);
        System.out.println(expectedCommand);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingTag_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_BRIDES_FRIEND.getTagName();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String userInput = " " + PREFIX_TAG + VALID_TAG_BRIDES_FRIEND;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG + " ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_tooLongTag_throwsParseException() {
        String longTag = "veryveryveryveryveryverylongtagthatshouldexceedthecharacterlimit";
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG + longTag;
        String expectedMessage = Messages.MESSAGE_INPUT_LENGTH_EXCEEDED;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_TAG + VALID_TAG_BRIDES_FRIEND;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
