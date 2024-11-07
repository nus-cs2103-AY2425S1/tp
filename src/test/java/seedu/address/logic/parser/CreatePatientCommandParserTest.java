package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PATIENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreatePatientCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class CreatePatientCommandParserTest {
    private CreatePatientCommandParser parser = new CreatePatientCommandParser();

    @Test
    public void parse_validArgs_returnsCreatePatientCommand() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY + " " + PREFIX_PHONE + VALID_PHONE_AMY + " "
                + PREFIX_EMAIL + VALID_EMAIL_AMY + " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY + " ";
        CreatePatientCommand expectedCommand = new CreatePatientCommand(PATIENT_AMY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        String userInput = INVALID_NAME_DESC + " " + PREFIX_PHONE + VALID_PHONE_AMY
                + " " + PREFIX_EMAIL + VALID_EMAIL_AMY + " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
        assertParseFailure(parser, userInput, String.format(
                Name.MESSAGE_CONSTRAINTS, CreatePatientCommand.MESSAGE_USAGE
        ));
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY + INVALID_PHONE_DESC
                + " " + PREFIX_EMAIL + VALID_EMAIL_AMY + " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
        assertParseFailure(parser, userInput, String.format(
                Phone.MESSAGE_CONSTRAINTS, CreatePatientCommand.MESSAGE_USAGE
        ));
    }

    @Test
    public void parse_invalidEmail_throwsParseException() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY + " " + PREFIX_PHONE + VALID_PHONE_AMY
                + INVALID_EMAIL_DESC + " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
        assertParseFailure(parser, userInput, String.format(
                Email.MESSAGE_CONSTRAINTS, CreatePatientCommand.MESSAGE_USAGE
        ));
    }

    @Test
    public void parse_invalidAddress_throwsParseException() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY + " " + PREFIX_PHONE + VALID_PHONE_AMY
                + " " + PREFIX_EMAIL + VALID_EMAIL_AMY + INVALID_ADDRESS_DESC;
        assertParseFailure(parser, userInput, String.format(
                Address.MESSAGE_CONSTRAINTS, CreatePatientCommand.MESSAGE_USAGE
        ));
    }

    @Test
    public void parse_missingPhone_throwsParseException() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY + " " + PREFIX_EMAIL + VALID_EMAIL_AMY
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
        assertParseFailure(parser, userInput, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, CreatePatientCommand.MESSAGE_USAGE
        ));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = " " + PREFIX_NAME + VALID_NAME_AMY + " " + PREFIX_NAME + VALID_NAME_BOB
                + " " + PREFIX_PHONE + VALID_PHONE_AMY + " " + PREFIX_EMAIL + VALID_EMAIL_AMY
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY + " ";
        String expectedMessage = "Multiple values specified for the following single-valued field(s): n/";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
