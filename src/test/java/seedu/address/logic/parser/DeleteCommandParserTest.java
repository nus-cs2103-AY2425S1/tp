package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Id;

public class DeleteCommandParserTest {
    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        String userInput = " " + PREFIX_ID + "2";
        DeleteCommand expectedCommand = new DeleteCommand(2);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        String userInput = " " + PREFIX_ID + "222#";
        assertParseFailure(parser, userInput, Id.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_blankId_throwsParseException() {
        String userInput = " " + PREFIX_ID + "";
        assertParseFailure(parser, userInput, Id.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingId_throwsParseException() {
        String userInput = " ";
        assertParseFailure(parser, userInput, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE
        ));
    }

}
