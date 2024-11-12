package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


public class TagCommandParserTest {
    @Test
    public void parse_oneTag_success() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/testTag1";
        Set<Tag> addedTags = SampleDataUtil.getTagSet("testTag1");
        TagCommand expectedCommand = new TagCommand(INDEX_FIRST_PERSON, addedTags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_threeTags_success() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/ testTag1 testTag2 testTag3";
        Set<Tag> addedTags = SampleDataUtil.getTagSet("testTag1", "testTag2", "testTag3");
        TagCommand expectedCommand = new TagCommand(INDEX_FIRST_PERSON, addedTags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noPrefix_failure() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = String.valueOf(INDEX_FIRST_PERSON.getOneBased());
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/tag1 t/tag2 t/tag3";
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                "Please only include one prefix t/ !");

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyTagArgument_failure() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/";
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "No tags specified!");

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_nonAlphanumericArgs_failure() {
        TagCommandParser parser = new TagCommandParser();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " t/bad*arg__!";
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                "Tags names should be alphanumeric");

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
