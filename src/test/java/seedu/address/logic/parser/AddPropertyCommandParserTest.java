package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Unit;
import seedu.address.testutil.PropertyBuilder;

public class AddPropertyCommandParserTest {

    private AddPropertyCommandParser parser = new AddPropertyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder().withPostalCode(VALID_POSTALCODE_ADMIRALTY)
                .withUnit(VALID_UNIT_ADMIRALTY).build();

        // normal input with all fields
        assertParseSuccess(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY,
                new AddPropertyCommand(expectedProperty));
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE);

        // missing PostalCode prefix
        assertParseFailure(parser, UNIT_DESC_ADMIRALTY, expectedMessage);

        // missing unit prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid postal code
        assertParseFailure(parser, INVALID_POSTALCODE_DESC + UNIT_DESC_ADMIRALTY,
                PostalCode.MESSAGE_CONSTRAINTS);

        // invalid unit
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + INVALID_UNIT_DESC,
                Unit.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessagePostalCode = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POSTALCODE);
        String expectedMessageUnit = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_UNITNUMBER);

        // duplicate name prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY,
                expectedMessagePostalCode);

        // duplicate phone prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY,
                expectedMessageUnit);
    }
}
