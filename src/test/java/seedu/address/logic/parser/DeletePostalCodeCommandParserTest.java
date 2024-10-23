package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeletePostalCodeCommand;
import seedu.address.model.person.PostalCode;

public class DeletePostalCodeCommandParserTest {
    private DeletePostalCodeCommandParser parser = new DeletePostalCodeCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePostalCodeCommand() {
        // Test with a valid postal code
        PostalCode validPostalCode = new PostalCode("123456");
        DeletePostalCodeCommand expectedCommand = new DeletePostalCodeCommand(validPostalCode);
        assertParseSuccess(parser, validPostalCode.toString(), expectedCommand);
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "abc",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePostalCodeCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "12345",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePostalCodeCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1234567",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePostalCodeCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePostalCodeCommand.MESSAGE_USAGE));
    }
}
