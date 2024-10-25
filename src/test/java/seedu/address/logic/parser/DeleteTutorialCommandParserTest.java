package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTutorialCommand;



public class DeleteTutorialCommandParserTest {


    private DeleteTutorialCommandParser parser = new DeleteTutorialCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "T1000", new DeleteTutorialCommand(TUTORIAL1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTutorialCommand.MESSAGE_USAGE));
    }
}
