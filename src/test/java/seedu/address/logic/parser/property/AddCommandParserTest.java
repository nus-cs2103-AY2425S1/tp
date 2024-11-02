package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.ASKING_PRICE_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASKING_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LANDLORD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROPERTY_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LANDLORD_NAME_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROPERTY_TYPE_DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LANDLORD_NAME_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BRENDA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASKING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.property.TypicalProperties.BRENDA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.property.AddCommand;
import seedu.address.model.property.Address;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;
import seedu.address.testutil.property.PropertyBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder(BRENDA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA
                + ADDRESS_DESC_BRENDA + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA,
                new AddCommand(expectedProperty));
    }

    @Test
    public void parse_repeatedFields_failure() {
        String validExpectedPropertyString = LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA;

        // multiple names
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_BRENDA + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_BRENDA + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple asking prices
        assertParseFailure(parser, ASKING_PRICE_DESC_BRENDA + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASKING_PRICE));

        // multiple property types
        assertParseFailure(parser, PROPERTY_TYPE_DESC_BRENDA + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedPropertyString + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS,
                        PREFIX_ASKING_PRICE, PREFIX_TYPE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_LANDLORD_NAME_DESC + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid asking price
        assertParseFailure(parser, INVALID_ASKING_PRICE_DESC + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASKING_PRICE));

        // invalid property type
        assertParseFailure(parser, INVALID_PROPERTY_TYPE_DESC + validExpectedPropertyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPropertyString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, validExpectedPropertyString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPropertyString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid asking price
        assertParseFailure(parser, validExpectedPropertyString + INVALID_ASKING_PRICE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASKING_PRICE));

        // invalid property type
        assertParseFailure(parser, validExpectedPropertyString + INVALID_PROPERTY_TYPE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_LANDLORD_NAME_BRENDA + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + VALID_PHONE_BRENDA + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + VALID_ADDRESS_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, expectedMessage);

        // missing asking price prefix
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + VALID_ASKING_PRICE_BRENDA + PROPERTY_TYPE_DESC_BRENDA, expectedMessage);

        // missing property type prefix
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + VALID_PROPERTY_TYPE_BRENDA, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_LANDLORD_NAME_BRENDA + VALID_PHONE_BRENDA + VALID_ADDRESS_BRENDA
                + VALID_ASKING_PRICE_BRENDA + VALID_PROPERTY_TYPE_BRENDA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_LANDLORD_NAME_DESC + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, LandlordName.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + INVALID_PHONE_DESC + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + INVALID_ADDRESS_DESC
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, Address.MESSAGE_CONSTRAINTS);

        // invalid asking price
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + INVALID_ASKING_PRICE_DESC + PROPERTY_TYPE_DESC_BRENDA, AskingPrice.MESSAGE_CONSTRAINTS);

        // invalid property type
        assertParseFailure(parser, LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + INVALID_PROPERTY_TYPE_DESC, PropertyType.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_LANDLORD_NAME_DESC + INVALID_PHONE_DESC + ADDRESS_DESC_BRENDA
                + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA, LandlordName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LANDLORD_NAME_DESC_BRENDA + PHONE_DESC_BRENDA
                + ADDRESS_DESC_BRENDA + ASKING_PRICE_DESC_BRENDA + PROPERTY_TYPE_DESC_BRENDA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
