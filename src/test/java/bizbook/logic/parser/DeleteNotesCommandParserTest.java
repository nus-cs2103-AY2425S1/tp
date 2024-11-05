package bizbook.logic.parser;

import static bizbook.logic.commands.DeleteNotesCommand.MESSAGE_USAGE;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import bizbook.commons.core.index.Index;
import bizbook.logic.commands.DeleteNotesCommand;

public class DeleteNotesCommandParserTest {

    private static final String DELETENOTE_INVALID_FORMAT = "Invalid command format! \n" + MESSAGE_USAGE;

    private DeleteNotesCommandParser parser = new DeleteNotesCommandParser();

    @Test
    public void parse_validArgs_success() {

        // Valid index and note index
        assertParseSuccess(parser, "1 i/1",
                new DeleteNotesCommand(Index.fromOneBased(1), Index.fromOneBased(1)));

    }

    @Test
    public void parse_invalidArgs_failure() {

        // Invalid index
        assertParseFailure(parser, "-1 i/1", DELETENOTE_INVALID_FORMAT);

        // Invalid note index
        assertParseFailure(parser, "1 i/-1", DELETENOTE_INVALID_FORMAT);

        // No index
        assertParseFailure(parser, "i/1", DELETENOTE_INVALID_FORMAT);

        // No note index
        assertParseFailure(parser, "-1 i/", DELETENOTE_INVALID_FORMAT);

        // No note and no index
        assertParseFailure(parser, "", DELETENOTE_INVALID_FORMAT);

    }
}
