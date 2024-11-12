package bizbook.logic.parser;

import static bizbook.logic.commands.AddNoteCommand.MESSAGE_USAGE;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import bizbook.commons.core.index.Index;
import bizbook.logic.commands.AddNoteCommand;
import bizbook.model.person.Note;

public class AddNoteCommandParserTest {

    private static final String ADDNOTE_INVALID_FORMAT = "Invalid command format! \n" + MESSAGE_USAGE;

    private AddNotesCommandParser parser = new AddNotesCommandParser();


    @Test
    public void parse_validArgs_success() {

        // Note with index
        assertParseSuccess(parser, "1 n/note1",
                new AddNoteCommand(Index.fromOneBased(1), new Note("note1")));

    }

    @Test
    public void parse_invalidArgs_failure() {

        // Non alphanumerical note
        assertParseFailure(parser, "1 n/note!", ADDNOTE_INVALID_FORMAT);

        // Invalid index
        assertParseFailure(parser, "-1 n/note1", ADDNOTE_INVALID_FORMAT);

        // No index
        assertParseFailure(parser, "n/note!", ADDNOTE_INVALID_FORMAT);

        // No note
        assertParseFailure(parser, "1 n/", ADDNOTE_INVALID_FORMAT);

        // No note and no index
        assertParseFailure(parser, "", ADDNOTE_INVALID_FORMAT);

    }
}
