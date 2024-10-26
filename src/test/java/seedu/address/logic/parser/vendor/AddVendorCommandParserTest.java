package seedu.address.logic.parser.vendor;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.BLANK_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC_TOO_SHORT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FLORIST;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEIGHBOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.CLIVE;
import static seedu.address.testutil.TypicalPersons.DOMINIC;
import static seedu.address.testutil.TypicalPersons.ERIC;
import static seedu.address.testutil.TypicalVendors.FIONA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.vendor.AddVendorCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.TagName;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.VendorBuilder;


public class AddVendorCommandParserTest {

    private AddVendorCommandParser parser = new AddVendorCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Vendor expectedVendor = new VendorBuilder(FIONA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FIONA + PHONE_DESC_FIONA + EMAIL_DESC_FIONA
                + ADDRESS_DESC_FIONA + TAG_DESC_FRIEND, new AddVendorCommand(expectedVendor));

        // multiple tags - all accepted
        Vendor expectedVendorMultipleTags = new VendorBuilder(FIONA).withTags(VALID_TAG_FRIEND, VALID_TAG_FLORIST)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_FIONA + PHONE_DESC_FIONA + EMAIL_DESC_FIONA + ADDRESS_DESC_FIONA
                        + TAG_DESC_FRIEND + TAG_DESC_FLORIST,
                new AddVendorCommand(expectedVendorMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedVendorString = NAME_DESC_FIONA + PHONE_DESC_FIONA + EMAIL_DESC_FIONA
                + ADDRESS_DESC_FIONA + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple tasks

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedVendorString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone - invalid characters that are non-digits
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid phone - too short, but consists of digits
        assertParseFailure(parser, INVALID_PHONE_DESC_TOO_SHORT + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
        // invalid task

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedVendorString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedVendorString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedVendorString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid task
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddVendorCommand(expectedPerson));

        // no address
        Person expectedPersonNoAddress = new PersonBuilder(CLIVE).build();
        // address tag with blank address
        assertParseSuccess(parser, NAME_DESC_CLIVE + PHONE_DESC_CLIVE + EMAIL_DESC_CLIVE + BLANK_ADDRESS_DESC
                + TAG_DESC_NEIGHBOR, new AddVendorCommand(expectedPersonNoAddress));

        // no address tag
        assertParseSuccess(parser, NAME_DESC_CLIVE + PHONE_DESC_CLIVE + EMAIL_DESC_CLIVE + TAG_DESC_NEIGHBOR,
                new AddVendorCommand(expectedPersonNoAddress));

        // blank phone number
        Person expectedPersonBlankPhone = new PersonBuilder(DOMINIC).withTags().build();
        assertParseSuccess(parser, NAME_DESC_DOMINIC + PHONE_DESC_DOMINIC + EMAIL_DESC_DOMINIC
                + ADDRESS_DESC_DOMINIC, new AddVendorCommand(expectedPersonBlankPhone));

        // no phone number tag
        assertParseSuccess(parser, NAME_DESC_DOMINIC + EMAIL_DESC_DOMINIC + ADDRESS_DESC_DOMINIC,
                new AddVendorCommand(expectedPersonBlankPhone));

        // blank email address
        Person expectedPersonBlankEmail = new PersonBuilder(ERIC).withTags().build();
        assertParseSuccess(parser, NAME_DESC_ERIC + PHONE_DESC_ERIC + EMAIL_DESC_ERIC
                + ADDRESS_DESC_ERIC, new AddVendorCommand(expectedPersonBlankEmail));

        // no email address tag
        assertParseSuccess(parser, NAME_DESC_ERIC + PHONE_DESC_ERIC + ADDRESS_DESC_ERIC,
                new AddVendorCommand(expectedPersonBlankEmail));

        // blank task

        // no task tag

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVendorCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_FIONA + PHONE_DESC_FIONA + EMAIL_DESC_FIONA + ADDRESS_DESC_FIONA,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_FIONA + VALID_EMAIL_FIONA
                        + VALID_ADDRESS_FIONA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone - wrong characters
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid phone - too short, but consists of digits
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC_TOO_SHORT + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, TagName.MESSAGE_CONSTRAINTS);

        // to add: invalid task

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVendorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_optionalValue_success() {
        Vendor expectedVendorNoAddress = new VendorBuilder(FIONA).build();

        // to add: task tag with blank task

        // to add: missing task tag
    }

}
