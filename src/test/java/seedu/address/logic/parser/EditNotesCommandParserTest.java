package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalNotes.VALID_NOTE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditNotesCommand;


public class EditNotesCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditNotesCommand.MESSAGE_USAGE);

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

}
