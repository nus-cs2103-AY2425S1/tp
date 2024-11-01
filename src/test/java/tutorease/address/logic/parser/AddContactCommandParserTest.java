package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.ADDRESS_DESC_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.EMAIL_DESC_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.NAME_DESC_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.PHONE_DESC_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorease.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorease.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.ROLE_DESC_MEG;
import static tutorease.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static tutorease.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static tutorease.address.logic.commands.CommandTestUtil.TAG_DESC_MENTOR;
import static tutorease.address.logic.commands.CommandTestUtil.TAG_DESC_SUPPORTIVE;
import static tutorease.address.logic.commands.CommandTestUtil.UPPERCASE_EMAIL_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.UPPERCASE_NAME_DESC_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_MENTOR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_SUPPORTIVE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorease.address.testutil.TypicalGuardians.MEG;
import static tutorease.address.testutil.TypicalStudents.AMY;
import static tutorease.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.model.person.Address;
import tutorease.address.model.person.Email;
import tutorease.address.model.person.Guardian;
import tutorease.address.model.person.Name;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.Phone;
import tutorease.address.model.person.Student;
import tutorease.address.model.tag.Tag;
import tutorease.address.testutil.GuardianBuilder;
import tutorease.address.testutil.StudentBuilder;

public class AddContactCommandParserTest {
    private AddContactCommandParser parser = new AddContactCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddContactCommand(expectedStudent));

        //
        assertParseSuccess(parser, UPPERCASE_NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddContactCommand(expectedStudent));

        assertParseSuccess(parser, NAME_DESC_BOB
                + PHONE_DESC_BOB + UPPERCASE_EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddContactCommand(expectedStudent));


        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddContactCommand(expectedStudentMultipleTags));

        Guardian expectedGuardian = new GuardianBuilder(MEG).withTags(VALID_TAG_SUPPORTIVE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MEG + PHONE_DESC_MEG + EMAIL_DESC_MEG
                + ADDRESS_DESC_MEG + ROLE_DESC_MEG + TAG_DESC_SUPPORTIVE, new AddContactCommand(expectedGuardian));


        // multiple tags - all accepted
        Guardian expectedGuardianMultipleTags = new GuardianBuilder(MEG)
                .withTags(VALID_TAG_MENTOR, VALID_TAG_SUPPORTIVE)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_MEG + PHONE_DESC_MEG + EMAIL_DESC_MEG + ADDRESS_DESC_MEG
                        + ROLE_DESC_MEG + TAG_DESC_SUPPORTIVE + TAG_DESC_MENTOR,
                new AddContactCommand(expectedGuardianMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND;

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

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_ROLE, PREFIX_EMAIL,
                        PREFIX_PHONE));

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

        // invalid role
        assertParseFailure(parser, validExpectedPersonString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ROLE_DESC_AMY,
                new AddContactCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing all fields
        String expectedMissingFieldsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddContactCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMissingFieldsMessage);


        // missing name prefix
        String expectedNameMessage = String.format(Messages.MISSING_PREFIX, PREFIX_NAME,
                AddContactCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ROLE_DESC_BOB,
                expectedNameMessage);

        // missing phone prefix
        String expectedPhoneMessage = String.format(Messages.MISSING_PREFIX, PREFIX_PHONE,
                AddContactCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedPhoneMessage);

        // missing email prefix
        String expectedEmailMessage = String.format(Messages.MISSING_PREFIX, PREFIX_EMAIL,
                AddContactCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + ROLE_DESC_BOB, expectedEmailMessage);

        // missing address prefix
        String expectedAddressMessage = String.format(Messages.MISSING_PREFIX, PREFIX_ADDRESS,
                AddContactCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + ROLE_DESC_BOB, expectedAddressMessage);

        // missing role prefix
        String expectedRoleMessage = String.format(Messages.MISSING_PREFIX, PREFIX_ROLE,
                AddContactCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_ROLE_BOB, expectedRoleMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMissingFieldsMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + ROLE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
    }
}
