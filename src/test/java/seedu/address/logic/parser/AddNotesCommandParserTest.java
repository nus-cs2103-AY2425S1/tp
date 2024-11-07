package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.model.person.Notes;
public class AddNotesCommandParserTest {
    private AddNotesCommandParser parser = new AddNotesCommandParser();
    private final String nonEmptyNotes = "Some notes.";
    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NOTES + nonEmptyNotes;
        AddNotesCommand expectedCommand = new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(nonEmptyNotes));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, AddNotesCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, AddNotesCommand.COMMAND_WORD + " " + nonEmptyNotes, expectedMessage);
    }
}
