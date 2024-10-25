package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.CLIENT_FLAG;
import static seedu.ddd.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_SERVICE_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ddd.logic.commands.CommandTestUtil.SERVICE_DESC_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.ddd.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ddd.logic.commands.CommandTestUtil.VENDOR_FLAG;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ddd.testutil.TypicalContacts.AMY;
import static seedu.ddd.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.AddCommand;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.tag.Tag;
import seedu.ddd.testutil.ClientBuilder;
import seedu.ddd.testutil.VendorBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_clientAllFieldsPresent_success() {

        Client expectedClient = new ClientBuilder(AMY).build();
        assertParseSuccess(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedClient));


        // multiple tags - all accepted
        Client expectedPersonMultipleTags = new ClientBuilder(AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_vendorAllFieldsPresent_success() {

        Vendor expectedVendor = new VendorBuilder(BOB).build();
        assertParseSuccess(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedVendor));


        // multiple tags - all accepted
        Contact expectedPersonMultipleTags = new VendorBuilder(BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_clientValidRepeatedNonTagValue_failure() {
        String validExpectedContactString = CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, validExpectedContactString + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, validExpectedContactString + PHONE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, validExpectedContactString + EMAIL_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, validExpectedContactString + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedContactString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));
    }

    @Test
    public void parse_vendorValidRepeatedNonTagValue_failure() {
        String validExpectedContactString = VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, validExpectedContactString + NAME_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, validExpectedContactString + PHONE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, validExpectedContactString + EMAIL_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, validExpectedContactString + ADDRESS_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedContactString + PHONE_DESC_BOB + EMAIL_DESC_BOB + NAME_DESC_BOB + ADDRESS_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));
    }

    @Test
    public void parse_clientInvalidRepeatedNonTagValue_failure() {

        String validExpectedContactString = CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedContactString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedContactString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedContactString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedContactString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

    }

    @Test
    public void parse_vendorInvalidRepeatedNonTagValue_failure() {

        String validExpectedContactString = VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_FRIEND;

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedContactString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedContactString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedContactString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedContactString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

    }

    @Test
    public void parse_clientOptionalFieldsMissing_success() {
        // zero tags
        Contact expectedClient = new ClientBuilder(AMY).withTags().build();
        assertParseSuccess(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, new AddCommand(expectedClient));
    }

    @Test
    public void parse_vendorOptionalFieldsMissing_success() {
        // zero tags
        Contact expectedVendor = new VendorBuilder(BOB).withTags().build();
        assertParseSuccess(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB, new AddCommand(expectedVendor));
    }

    @Test
    public void parse_clientCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.CLIENT_MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, CLIENT_FLAG + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY
                        + ADDRESS_DESC_AMY, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, CLIENT_FLAG + VALID_NAME_AMY + VALID_PHONE_AMY + VALID_EMAIL_AMY + VALID_ADDRESS_AMY,
                expectedMessage);
    }

    @Test
    public void parse_vendorCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.VENDOR_MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VENDOR_FLAG + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, expectedMessage);

        // missing service prefix
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SERVICE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VENDOR_FLAG + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_vendorInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VENDOR_FLAG + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + SERVICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid service
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_SERVICE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Service.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, VENDOR_FLAG + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + SERVICE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VENDOR_FLAG + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + SERVICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.VENDOR_MESSAGE_USAGE));
    }

    @Test
    public void parse_clientInvalidValue_failure() {
        // invalid name
        assertParseFailure(parser, CLIENT_FLAG + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC
                + ADDRESS_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, CLIENT_FLAG + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CLIENT_FLAG + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.CLIENT_MESSAGE_USAGE));
    }

}
