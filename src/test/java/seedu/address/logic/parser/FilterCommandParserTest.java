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

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friends"));
        FilterCommand expectedFilterCommand =
                new FilterCommand(expectedTags);
        assertParseSuccess(parser, " t/friends", expectedFilterCommand);

        expectedTags.add(new Tag("classmates"));
        FilterCommand expectedFilterCommandDouble =
                new FilterCommand(expectedTags);
        assertParseSuccess(parser, " t/friends t/classmates", expectedFilterCommandDouble);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String[] invalidTags = {
            "best-friends",
            "best friends"
        };
        for (String invalidTag : invalidTags) {
            assertParseFailure(parser, " t/" + invalidTag,
                    Tag.MESSAGE_CONSTRAINTS);
        }
        assertParseFailure(parser, " n/ t/friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

}
