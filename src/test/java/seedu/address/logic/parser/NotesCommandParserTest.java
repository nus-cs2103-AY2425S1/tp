package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NotesCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;

public class NotesCommandParserTest {

    private NotesCommandParser parser = new NotesCommandParser();

    @Test
    public void parse_viewValidNameArgs_returnsNotesCommand() {
        // view command with name
        String validName = "Alice Pauline";
        NotesCommand expectedViewCommand =
            new NotesCommand(new Name(validName), NotesCommand.Mode.VIEW);
        assertParseSuccess(parser, " " + PREFIX_VIEW + validName, expectedViewCommand);
    }

    @Test
    public void parse_viewValidIndexArgs_returnsNotesCommand() {
        // view command with index
        NotesCommand expectedViewCommand =
            new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.VIEW);
        assertParseSuccess(parser, " " + PREFIX_VIEW + "1", expectedViewCommand);
    }

    @Test
    public void parse_deleteValidNameArgs_returnsNotesCommand() {
        // delete command with name
        String validName = "Alice Pauline";
        NotesCommand expectedDeleteCommand =
            new NotesCommand(new Name(validName), NotesCommand.Mode.DELETE);
        assertParseSuccess(parser, " " + PREFIX_DELETE + validName, expectedDeleteCommand);
    }

    @Test
    public void parse_deleteValidIndexArgs_returnsNotesCommand() {
        // delete command with index
        NotesCommand expectedDeleteCommand =
            new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.DELETE);
        assertParseSuccess(parser, " " + PREFIX_DELETE + "1", expectedDeleteCommand);
    }

    @Test
    public void parse_addValidNameArgs_returnsNotesCommand() {
        // add command with name
        String validName = "Alice Pauline";
        String validNotes = "Test notes";
        NotesCommand expectedAddCommand =
            new NotesCommand(new Name(validName), NotesCommand.Mode.ADD, new Notes(validNotes));
        assertParseSuccess(parser, " " + PREFIX_ADD + validName + " " + PREFIX_NOTES + validNotes,
            expectedAddCommand);
    }

    @Test
    public void parse_addValidIndexArgs_returnsNotesCommand() {
        // add command with index
        String validNotes = "Test notes";
        NotesCommand expectedAddCommand =
            new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.ADD, new Notes(validNotes));
        assertParseSuccess(parser, " " + PREFIX_ADD + "1 " + PREFIX_NOTES + validNotes,
            expectedAddCommand);
    }

    @Test
    public void parse_missingPrefixIdentifier_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // missing prefix
        assertParseFailure(parser, "Alice Pauline", expectedMessage);
        assertParseFailure(parser, "1", expectedMessage);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // invalid prefix
        assertParseFailure(parser, " x/Alice", expectedMessage);
        assertParseFailure(parser, " x/1", expectedMessage);
    }

    @Test
    public void parse_addCommandMissingNotes_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // missing notes for add command with name
        assertParseFailure(parser, " " + PREFIX_ADD + "Alice", expectedMessage);

        // missing notes for add command with index
        assertParseFailure(parser, " " + PREFIX_ADD + "1", expectedMessage);
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // multiple command prefixes with names
        assertParseFailure(parser, " " + PREFIX_VIEW + "Alice " + PREFIX_DELETE + "Bob", expectedMessage);

        // multiple command prefixes with indices
        assertParseFailure(parser, " " + PREFIX_VIEW + "1 " + PREFIX_DELETE + "2", expectedMessage);

        // mixed name and index
        assertParseFailure(parser, " " + PREFIX_VIEW + "Alice " + PREFIX_DELETE + "1", expectedMessage);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

        // empty argument
        assertParseFailure(parser, "", expectedMessage);
    }
}
