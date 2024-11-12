package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Tags;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsTagCommand() {
        Tag tag = new Tag("tag");
        Set<Tag> tagSet = Set.of(tag);
        Tags tags = new Tags(tagSet);
        assertParseSuccess(parser, "1 t/tag", new TagCommand(INDEX_FIRST_PERSON, tags));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }
}
