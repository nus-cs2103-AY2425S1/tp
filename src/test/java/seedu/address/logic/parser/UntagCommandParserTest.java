package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UntagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Tags;

public class UntagCommandParserTest {

    private UntagCommandParser parser = new UntagCommandParser();

    @Test
    public void parse_validArgs_returnsTagCommand() {
        Tag tag = new Tag("tag");
        Set<Tag> tagSet = Set.of(tag);
        Tags tags = new Tags(tagSet);
        assertParseSuccess(parser, "1 t/tag", new UntagCommand(INDEX_FIRST_PERSON, tags));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
    }
}
