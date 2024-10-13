package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_multipleTags_returnsFilterCommand() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friend"));
        expectedTags.add(new Tag("colleague"));
        FilterCommand expectedFilterCommand = new FilterCommand("Alice", expectedTags);
        assertParseSuccess(parser, " n/Alice t/friend t/colleague", expectedFilterCommand);
    }

    @Test
    public void parse_duplicateTags_returnsFilterCommandWithUniqueTagSet() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friend"));
        FilterCommand expectedFilterCommand = new FilterCommand("Alice", expectedTags);
        assertParseSuccess(parser, " n/Alice t/friend", expectedFilterCommand);
    }

    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        assertParseFailure(parser, " n/Alice t/fr!end",
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, " n/Alice t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateNamePrefix_throwsParseException() {
        assertParseFailure(parser, " n/Alice n/Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_nameWithTrailingSpaces_returnsFilterCommand() {
        FilterCommand expectedFilterCommand = new FilterCommand("Alice", new HashSet<>());
        assertParseSuccess(parser, " n/Alice  ", expectedFilterCommand);
    }
}
