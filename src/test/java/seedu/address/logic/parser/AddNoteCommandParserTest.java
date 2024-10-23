package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;

public class AddNoteCommandParserTest {

    private AddNoteCommandParser parser = new AddNoteCommandParser();

    @Test
    public void parse_validArgs_returnsAddNoteCommand() {
        Note note = new Note(VALID_NOTE);
        Nric nric = new Nric(VALID_NRIC_AMY);
        AddNoteCommand command = new AddNoteCommand(nric, note);
        assertParseSuccess(parser, NRIC_DESC_AMY + VALID_NOTE_DESC, command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nricMissing_failure() {
        // Missing NRIC
        assertParseFailure(parser, VALID_NOTE_DESC,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noteMissing_failure() {
        // Missing note
        assertParseFailure(parser, NRIC_DESC_AMY,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNote_failure() {
        // Invalid note
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_NOTE_DESC, String.format(
            Note.MESSAGE_CONSTRAINTS));
    }

}
