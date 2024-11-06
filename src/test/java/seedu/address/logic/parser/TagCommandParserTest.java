package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tag.TagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsTagCommand() {
        Index targetIndex = Index.fromOneBased(1);

        // Expected tags
        Tag tag1 = new Tag(new TagName("colleague"));
        Tag tag2 = new Tag(new TagName("gym"));

        TagCommand expectedCommand = new TagCommand(targetIndex, new HashSet<>(Arrays.asList(tag1, tag2)));

        String userInput = "1 t/colleague t/gym";

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index (non-numeric)
        assertParseFailure(parser, "a t/colleague", MESSAGE_INVALID_INDEX);

        // Index missing
        assertParseFailure(parser, "t/colleague t/gym", MESSAGE_INVALID_INDEX);

        // Missing tags (no tags specified)
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TagCommand.MESSAGE_USAGE));

        // Tag descriptions contain non-alphanumeric or space characters
        assertParseFailure(parser, "1 t/colleague_", TagName.MESSAGE_CONSTRAINTS);
    }
}
