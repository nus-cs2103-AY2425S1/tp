package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NotesCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;

public class NotesCommandParserTest {

    private NotesCommandParser parser = new NotesCommandParser();

    @Test
    public void parse_viewValidArgs_returnsNotesCommand() {
        // view command
        String validName = "Alice Pauline";
        NotesCommand expectedViewCommand =
            new NotesCommand(new Name(validName), NotesCommand.Mode.VIEW);
        assertParseSuccess(parser, " " + PREFIX_VIEW + validName, expectedViewCommand);
    }

    @Test
    public void parse_deleteValidArgs_returnsNotesCommand() {
        // delete command
        String validName = "Alice Pauline";
        NotesCommand expectedDeleteCommand =
            new NotesCommand(new Name(validName), NotesCommand.Mode.DELETE);
        assertParseSuccess(parser, " " + PREFIX_DELETE + validName, expectedDeleteCommand);
    }

    @Test
    public void parse_addValidArgs_returnsNotesCommand() {
        // add command
        String validName = "Alice Pauline";
        String validNotes = "Test notes";
        NotesCommand expectedAddCommand =
            new NotesCommand(new Name(validName), NotesCommand.Mode.ADD, new Notes(validNotes));
        assertParseSuccess(parser, " " + PREFIX_ADD + validName + " " + PREFIX_NOTES + validNotes,
            expectedAddCommand);
    }

    @Test
    public void parse_missingPrefixName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // missing prefix
        assertParseFailure(parser, "Alice Pauline", expectedMessage);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // invalid prefix
        assertParseFailure(parser, " x/Alice", expectedMessage);
    }

    @Test
    public void parse_addCommandMissingNotes_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // missing notes for add command
        assertParseFailure(parser, " " + PREFIX_ADD + "Alice", expectedMessage);
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // multiple command prefixes
        assertParseFailure(parser, " " + PREFIX_VIEW + "Alice " + PREFIX_DELETE + "Alice", expectedMessage);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // empty argument
        assertParseFailure(parser, "", expectedMessage);
    }
}
