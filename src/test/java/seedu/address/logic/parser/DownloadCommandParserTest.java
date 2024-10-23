package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DownloadCommand;
import seedu.address.model.tag.Tag;

class DownloadCommandParserTest {

    private final DownloadCommandParser parser = new DownloadCommandParser();

    @Test
    public void parse_validTags_returnsDownloadCommand() {
        Set<Tag> expectedTags = createTagSet("tag1", "tag2");
        assertParseSuccess(parser, " " + CliSyntax.PREFIX_TAG + "tag1 " + CliSyntax.PREFIX_TAG + "tag2",
                new DownloadCommand(expectedTags));
    }

    @Test
    public void parse_noTags_returnsDownloadCommand() {
        Set<Tag> emptyTagSet = new HashSet<>();
        assertParseSuccess(parser, "", new DownloadCommand(emptyTagSet));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertParseFailure(parser, "invalid input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, CliSyntax.PREFIX_TAG + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, "extra text " + CliSyntax.PREFIX_TAG + "tag1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        assertParseFailure(parser,  " " + CliSyntax.PREFIX_TAG,
                Tag.MESSAGE_CONSTRAINTS);
    }

    private Set<Tag> createTagSet(String... tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tags) {
            tagSet.add(new Tag(tag));
        }
        return tagSet;
    }

}
