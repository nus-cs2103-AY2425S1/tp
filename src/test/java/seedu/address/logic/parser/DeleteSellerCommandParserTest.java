package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteSellerCommand;
import seedu.address.model.client.Phone;

public class DeleteSellerCommandParserTest {

    private DeleteSellerCommandParser parser = new DeleteSellerCommandParser();
    private final String nonEmptyphoneNumber = "92345678";
    @Test
    public void parse_phoneNumberSpecified_success() {
        // Test for valid phone number
        String userInput = String.format(" %s%s", PREFIX_PHONE, nonEmptyphoneNumber);
        assertParseSuccess(parser, userInput, new DeleteSellerCommand(new Phone(nonEmptyphoneNumber)));
    }

    @Test
    public void parse_emptyPhoneNumber_failure() {
        // Test for empty phone number
        String userInput = DeleteSellerCommand.COMMAND_WORD + " " + PREFIX_PHONE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSellerCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSellerCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DeleteSellerCommand.COMMAND_WORD, expectedMessage);
    }
}
