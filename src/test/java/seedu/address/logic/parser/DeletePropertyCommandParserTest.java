package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;
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
        // Test for valid postal code and unit number
        String userInput = " " + PREFIX_POSTALCODE + "118420 " + PREFIX_UNITNUMBER + "08-08";
        PostalCode validPostalCode = new PostalCode("118420");
        Unit validUnitNumber = new Unit("08-08");
        DeletePropertyCommand expectedCommand = new DeletePropertyCommand(validPostalCode, validUnitNumber);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidPostalCodeFormat_failure() {
        // Test for invalid postal code format
        String userInput = " " + PREFIX_POSTALCODE + "abc123" + " " + PREFIX_UNITNUMBER + "08-08";
        String expectedMessage = PostalCode.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidUnitNumberFormat_failure() {
        // Test for invalid unit number format
        String userInput = " " + PREFIX_POSTALCODE + "118420" + " " + PREFIX_UNITNUMBER + "808";
        String expectedMessage = Unit.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
