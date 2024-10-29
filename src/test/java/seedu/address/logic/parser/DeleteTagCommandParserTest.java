package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalTags.FRIENDS_TAG;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("test"));
        assertParseSuccess(parser, "1 t/test", new DeleteTagCommand(INDEX_FIRST_PERSON, tags));
    }

    @Test
    public void parse_validArgsWithMultipleTags_returnsDeleteTagCommand() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("senior"));
        tags.add(FRIENDS_TAG);
        assertParseSuccess(parser, "1 t/senior t/friends", new DeleteTagCommand(INDEX_FIRST_PERSON, tags));
    }

    @Test
    public void parse_missingTags_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "t/happy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingInput_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
    }
}
