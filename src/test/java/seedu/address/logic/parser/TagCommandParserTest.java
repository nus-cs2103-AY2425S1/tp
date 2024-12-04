package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_INVALID_INDEX_OR_STRING;
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
    public void parse_validAllArgs_returnsAddTagCommand() {
        String userInput = "all t/friend t/colleague";
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friend"));
        expectedTags.add(new Tag("colleague"));

        TagCommand expectedCommand = new TagCommand(expectedTags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing tags or index should throw format error
        String missingTagPrefix = "1 e";
        String missingIndexInput = "t/friend";
        String emptyInput = " ";
        String onlyTagPrefix = "t/";

        assertParseFailure(parser, missingTagPrefix,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, missingIndexInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, emptyInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, onlyTagPrefix,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Index is 0 or signed or non-integer
        assertParseFailure(parser, "0 t/friend", MESSAGE_INVALID_INDEX_OR_STRING);
        assertParseFailure(parser, "-1 t/friend", MESSAGE_INVALID_INDEX_OR_STRING);
        assertParseFailure(parser, "e t/friend", MESSAGE_INVALID_INDEX_OR_STRING);

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
