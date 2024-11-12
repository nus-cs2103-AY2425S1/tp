package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEmergencyContactCommand;

public class DeleteEmergencyContactCommandParserTest {
    private DeleteEmergencyContactCommandParser parser = new DeleteEmergencyContactCommandParser();
    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased());
        DeleteEmergencyContactCommand expectedCommand = new DeleteEmergencyContactCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEmergencyContactCommand.MESSAGE_USAGE);
        // no index
        assertParseFailure(parser, DeleteEmergencyContactCommand.COMMAND_WORD, expectedMessage);
        // invalid index
        assertParseFailure(parser, DeleteEmergencyContactCommand.COMMAND_WORD + " a", expectedMessage);
    }
}
