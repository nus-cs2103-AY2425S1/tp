package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_multipleTags_returnsFilterCommand() {
        Set<Tag> expectedTags = new HashSet<>(Arrays.asList(new Tag("friend"), new Tag("colleague")));
        Set<String> expectedNames = new HashSet<>(List.of("Alice"));
        FilterCommand expectedFilterCommand = new FilterCommand(expectedNames, expectedTags);
        assertParseSuccess(parser, " n\\Alice t\\friend t\\colleague", expectedFilterCommand);
    }

    @Test
    public void parse_duplicateTags_returnsFilterCommandWithUniqueTagSet() {
        Set<Tag> expectedTags = new HashSet<>(List.of(new Tag("friend")));
        Set<String> expectedNames = new HashSet<>(List.of("Alice"));
        FilterCommand expectedFilterCommand = new FilterCommand(expectedNames, expectedTags);
        assertParseSuccess(parser, " n\\Alice t\\friend t\\friend", expectedFilterCommand);
    }

    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        assertParseFailure(parser, " n\\Alice t\\fr!end",
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, " n\\Alice t\\",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleNames_returnsFilterCommand() {
        Set<String> expectedNames = new HashSet<>(Arrays.asList("ali", "bo"));
        FilterCommand expectedFilterCommand = new FilterCommand(expectedNames, new HashSet<>());
        assertParseSuccess(parser, " n\\ali n\\bo", expectedFilterCommand);
    }

    @Test
    public void parse_duplicateNames_returnsFilterCommandWithUniqueNameSet() {
        Set<String> expectedNames = new HashSet<>(List.of("Alice"));
        FilterCommand expectedFilterCommand = new FilterCommand(expectedNames, new HashSet<>());
        assertParseSuccess(parser, " n\\Alice n\\Alice", expectedFilterCommand);
    }

    @Test
    public void parse_nameWithTrailingSpaces_returnsFilterCommand() {
        Set<String> expectedNames = new HashSet<>(List.of("Alice"));
        FilterCommand expectedFilterCommand = new FilterCommand(expectedNames, new HashSet<>());
        assertParseSuccess(parser, " n\\Alice  ", expectedFilterCommand);
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " x\\Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
