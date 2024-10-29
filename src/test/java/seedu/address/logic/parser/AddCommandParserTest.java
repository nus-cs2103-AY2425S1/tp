package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DELETION;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFLASTVISIT_DELETION;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFLASTVISIT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFLASTVISIT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DELETION;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DELETION;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATEOFLASTVISIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMERGENCY_CONTACT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFLASTVISIT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFLASTVISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple dateOfLastVisit
        assertParseFailure(parser, DATEOFLASTVISIT_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATEOFLASTVISIT));

        // multiple emergency contacts
        assertParseFailure(parser, EMERGENCY_CONTACT_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_CONTACT));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + DATEOFLASTVISIT_DESC_AMY + EMERGENCY_CONTACT_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL,
                        PREFIX_PHONE, PREFIX_DATEOFLASTVISIT, PREFIX_EMERGENCY_CONTACT));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid dateOfLastVisit
        assertParseFailure(parser, INVALID_DATEOFLASTVISIT_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATEOFLASTVISIT));

        // invalid emergency contact
        assertParseFailure(parser, INVALID_EMERGENCY_CONTACT_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_CONTACT));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid dateOfLastVisit
        assertParseFailure(parser, validExpectedPersonString + INVALID_DATEOFLASTVISIT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATEOFLASTVISIT));

        // invalid emergency contact
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMERGENCY_CONTACT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_CONTACT));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(
                parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + DATEOFLASTVISIT_DESC_AMY + EMERGENCY_CONTACT_DESC_AMY,
                new AddCommand(expectedPerson));

        // no email provided
        Person expectedPersonWithoutEmail = new PersonBuilder(BOB).withEmail().withTags().build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DATEOFLASTVISIT_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPersonWithoutEmail));

        // no address provided
        Person expectedPersonWithoutAddress = new PersonBuilder(BOB).withAddress().withTags().build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DATEOFLASTVISIT_DESC_BOB
                + EMERGENCY_CONTACT_DESC_BOB, new AddCommand(expectedPersonWithoutAddress));

        // no dateOfLastVisit provided
        Person expectedPersonWithoutDateOfLastVisit = new PersonBuilder(BOB).withDateOfLastVisit().withTags()
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB
                + EMERGENCY_CONTACT_DESC_BOB, new AddCommand(expectedPersonWithoutDateOfLastVisit));

        // no emergency contact provided
        Person expectedPersonWithoutEmergencyContact = new PersonBuilder(BOB).withEmergencyContact()
                .withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + DATEOFLASTVISIT_DESC_BOB,
                new AddCommand(expectedPersonWithoutEmergencyContact));

        // no email or address provided
        Person expectedPersonWithoutAddressOrEmail = new PersonBuilder(BOB).withAddress().withEmail()
                .withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + DATEOFLASTVISIT_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPersonWithoutAddressOrEmail));

        // no email or dateOfLastVisit provided
        Person expectedPersonWithoutEmailOrDateOfLastVisit = new PersonBuilder(BOB).withEmail().withTags()
                .withDateOfLastVisit().build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPersonWithoutEmailOrDateOfLastVisit));

        // no address or dateOfLastVisit provided
        Person expectedPersonWithoutAddressOrDateOfLastVisit = new PersonBuilder(BOB).withAddress().withTags()
                .withDateOfLastVisit().build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPersonWithoutAddressOrDateOfLastVisit));

        // no address or emergencyContact provided
        Person expectedPersonWithoutAddressOrEmergencyContact = new PersonBuilder(BOB).withAddress()
                .withTags().withEmergencyContact().build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DATEOFLASTVISIT_DESC_BOB,
                new AddCommand(expectedPersonWithoutAddressOrEmergencyContact));

        // Providing empty ("") email, address, dateoflastvisit, emergency contact
        Person expectedPersonWithoutAnyOptionalFields = new PersonBuilder(BOB).withEmail().withAddress()
                .withDateOfLastVisit().withEmergencyContact().withTags().build();
        assertParseSuccess(
                //Note DELETION refers to the empty string ex. EMAIL DELETION = "e/"
                parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DELETION + EMERGENCY_CONTACT_DELETION
                + ADDRESS_DELETION + DATEOFLASTVISIT_DELETION,
                new AddCommand(expectedPersonWithoutAnyOptionalFields));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DATEOFLASTVISIT_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DATEOFLASTVISIT_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_DATEOFLASTVISIT_BOB, expectedMessage);
    }

    @Test
    public void parse_nameWithNonAlphanumeric_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(
                parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(
                parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(
                parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + DATEOFLASTVISIT_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + DATEOFLASTVISIT_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid dateOfLastVisit
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + INVALID_DATEOFLASTVISIT_DESC,
                DateOfLastVisit.MESSAGE_CONSTRAINTS);

        // invalid emergency contact
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB + INVALID_EMERGENCY_CONTACT_DESC,
                EmergencyContact.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + DATEOFLASTVISIT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
