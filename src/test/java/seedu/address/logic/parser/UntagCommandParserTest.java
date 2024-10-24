package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class UntagCommandParserTest {

    private final UntagCommandParser parser = new UntagCommandParser();

    @Test
    public void parse_oneTag_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/friends";
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends");
        UntagCommand expectedCommand = new UntagCommand(INDEX_FIRST_PERSON, tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTags_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/friends colleagues";
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "colleagues");
        UntagCommand expectedCommand = new UntagCommand(INDEX_FIRST_PERSON, tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_removeAllTags_success() {
        String userInput = INDEX_SECOND_PERSON.getOneBased() + " t/all";
        UntagCommand expectedCommand = new UntagCommand(INDEX_SECOND_PERSON, null); // null signifies removing all tags
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noPrefix_failure() {
        String userInput = String.valueOf(INDEX_FIRST_PERSON.getOneBased());
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyTagArgument_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/";
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_multiplePrefixes_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/friends t/colleagues";
        String expectedMessage = "Error: More than one 't/' detected. Please use only one 't/' for untagging.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidTagCharacters_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/friends *colleagues";
        String expectedMessage = "Error: Tags names should be alphanumeric.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "untag 999 t/friends";
        String expectedMessage = "Error: 1 or more contact indexes provided is invalid.";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
