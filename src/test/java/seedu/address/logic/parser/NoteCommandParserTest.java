package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.person.Note;

public class NoteCommandParserTest {
    private NoteCommandParser parser = new NoteCommandParser();
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have note
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NOTE + nonEmptyRemark;
        NoteCommand expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no note
        userInput = targetIndex.getOneBased() + " " + PREFIX_NOTE;
        expectedCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, NoteCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, NoteCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
