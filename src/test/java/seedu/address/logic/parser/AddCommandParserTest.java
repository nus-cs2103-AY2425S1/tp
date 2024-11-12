package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDANCE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REGISTER_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_CLASS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REGISTER_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REGISTER_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_CLASS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_CLASS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGISTER_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_CLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB
                + TAG_DESC_FRIEND + ATTENDANCE_DESC_BOB, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REGISTER_NUMBER_DESC_BOB
                + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB
                + TAG_DESC_FRIEND + ATTENDANCE_DESC_BOB;

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

        // multiple register numbers
        assertParseFailure(parser, REGISTER_NUMBER_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REGISTER_NUMBER));

        // multiple sexes
        assertParseFailure(parser, SEX_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // multiple classes
        assertParseFailure(parser, STUDENT_CLASS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_CLASS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                + REGISTER_NUMBER_DESC_AMY + SEX_DESC_AMY + STUDENT_CLASS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE,
                PREFIX_REGISTER_NUMBER, PREFIX_SEX, PREFIX_STUDENT_CLASS));

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

        // invalid register number
        assertParseFailure(parser, INVALID_REGISTER_NUMBER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REGISTER_NUMBER));

        // invalid sex
        assertParseFailure(parser, INVALID_SEX_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // invalid class
        assertParseFailure(parser, INVALID_STUDENT_CLASS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_CLASS));

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

        // invalid register number
        assertParseFailure(parser, validExpectedPersonString + INVALID_REGISTER_NUMBER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REGISTER_NUMBER));

        // invalid sex
        assertParseFailure(parser, validExpectedPersonString + INVALID_SEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // invalid class
        assertParseFailure(parser, validExpectedPersonString + INVALID_STUDENT_CLASS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_CLASS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REGISTER_NUMBER_DESC_AMY + SEX_DESC_AMY + STUDENT_CLASS_DESC_AMY, new AddCommand(expectedPerson));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB, expectedMessage);

        // missing register number prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_REGISTER_NUMBER_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB, expectedMessage);

        // missing sex prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + VALID_SEX_BOB + STUDENT_CLASS_DESC_BOB, expectedMessage);

        // missing class prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + VALID_STUDENT_CLASS_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_REGISTER_NUMBER_BOB + VALID_SEX_BOB + VALID_STUDENT_CLASS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid register number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_REGISTER_NUMBER_DESC + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, RegisterNumber.MESSAGE_CONSTRAINTS);

        // invalid sex
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + INVALID_SEX_DESC + STUDENT_CLASS_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Sex.MESSAGE_CONSTRAINTS);

        // invalid class
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + INVALID_STUDENT_CLASS_DESC + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, StudentClass.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REGISTER_NUMBER_DESC_BOB + SEX_DESC_BOB + STUDENT_CLASS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
