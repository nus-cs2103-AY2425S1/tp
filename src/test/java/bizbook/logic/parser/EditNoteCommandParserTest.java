package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CliSyntax.PREFIX_NOTES;
import static bizbook.logic.parser.CliSyntax.PREFIX_NOTES_INDEX;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static bizbook.testutil.TypicalNotes.VALID_NOTE;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.EditNoteCommand;


public class EditNoteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditNoteCommand.MESSAGE_USAGE);

    private EditNotesCommandParser parser = new EditNotesCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // Wrong command format
        assertParseFailure(parser, VALID_NOTE.getNote(), MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_NOTES_INDEX + INDEX_FIRST_NOTE.getOneBased()
                + " " + PREFIX_NOTES + VALID_NOTE.getNote();

        EditNoteCommand expectedCommand = new EditNoteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_NOTE, VALID_NOTE);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
