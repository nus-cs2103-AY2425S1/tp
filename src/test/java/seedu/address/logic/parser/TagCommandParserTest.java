package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsAddTagCommand() {
        String userInput = "1 t/friend t/colleague";
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friend"));
        expectedTags.add(new Tag("colleague"));

        TagCommand expectedCommand = new TagCommand(INDEX_FIRST_PERSON, expectedTags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "1 invalidTag";

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTags_returnsOptionalEmpty() {
        // No tags provided after t/
        String userInput = "1";

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankTag_throwsParseException() {
        // Tag is just a space
        String userInput = "1 t/ ";

        assertParseFailure(parser, userInput, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_noTag_throwsParseException() {
        // No tag after the prefix
        String userInput = "1 t/";

        assertParseFailure(parser, userInput, Tag.MESSAGE_CONSTRAINTS);
    }
}
