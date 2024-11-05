package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static bizbook.logic.parser.CliSyntax.PREFIX_NAME;
import static bizbook.logic.parser.CliSyntax.PREFIX_TAG;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.DeleteTagCommand;
import bizbook.model.tag.Tag;

public class DeleteTagCommandParserTest {
    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        assertParseSuccess(parser, " 1 " + PREFIX_TAG + VALID_TAG_FRIEND,
                new DeleteTagCommand(INDEX_FIRST_PERSON, new Tag(VALID_TAG_FRIEND)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagCommand.MESSAGE_USAGE));

        // Invalid prefix
        assertParseFailure(parser, " 1 " + PREFIX_NAME + "Burnice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagCommand.MESSAGE_USAGE));

        // Invalid tag
        assertParseFailure(parser, " 1 " + PREFIX_TAG + "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagCommand.MESSAGE_USAGE));
    }
}
