package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Unit;

public class DeletePropertyCommandParserTest {

    private DeletePropertyCommandParser parser = new DeletePropertyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = POSTALCODE_DESC_ADMIRALTY + " " + UNIT_DESC_ADMIRALTY;
        PostalCode validPostalCode = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        Unit validUnitNumber = new Unit(VALID_UNIT_ADMIRALTY);
        DeletePropertyCommand expectedCommand = new DeletePropertyCommand(validPostalCode, validUnitNumber);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsPresentWithExtraPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);
        String userInput = POSTALCODE_DESC_ADMIRALTY + " " + UNIT_DESC_ADMIRALTY + NAME_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidPostalCodeFormat_failure() {
        String userInput = INVALID_POSTALCODE_DESC + " " + UNIT_DESC_ADMIRALTY;
        String expectedMessage = PostalCode.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidPostalCodeAndUnit_failure() {
        String userInput = INVALID_POSTALCODE_DESC + " " + INVALID_UNIT_DESC;
        String expectedMessage = PostalCode.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidUnitNumberFormat_failure() {
        String userInput = POSTALCODE_DESC_ADMIRALTY + " " + INVALID_UNIT_DESC;
        String expectedMessage = Unit.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingPostalCode_failure() {
        String userInput = UNIT_DESC_ADMIRALTY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingUnitNumber_failure() {
        String userInput = POSTALCODE_DESC_ADMIRALTY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingPostalCodePrefix_failure() {
        String userInput = VALID_POSTALCODE_ADMIRALTY + " " + UNIT_DESC_ADMIRALTY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingUnitNumberPrefix_failure() {
        String userInput = POSTALCODE_DESC_ADMIRALTY + " " + VALID_UNIT_ADMIRALTY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_extraSpaces_success() {
        String userInput = "   " + POSTALCODE_DESC_ADMIRALTY + "    " + UNIT_DESC_ADMIRALTY + "   ";
        PostalCode validPostalCode = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        Unit validUnitNumber = new Unit(VALID_UNIT_ADMIRALTY);
        DeletePropertyCommand expectedCommand = new DeletePropertyCommand(validPostalCode, validUnitNumber);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_onlySpaces_failure() {
        String userInput = "     ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_upperCasePrefixes_failure() {
        String userInput = "C/" + VALID_POSTALCODE_ADMIRALTY + " U/" + VALID_UNIT_ADMIRALTY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_preamblePresent_failure() {
        String userInput = "randomPreambleText " + POSTALCODE_DESC_ADMIRALTY + " " + UNIT_DESC_ADMIRALTY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
