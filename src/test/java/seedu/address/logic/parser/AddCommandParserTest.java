package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_STUDENT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + COURSE_DESC_BOB + TAG_DESC_STUDENT, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_STUDENT, VALID_TAG_TUTOR)
                .build();
        assertParseSuccess(parser,
                STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB + TAG_DESC_TUTOR + TAG_DESC_STUDENT,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + COURSE_DESC_BOB + TAG_DESC_STUDENT;

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
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_STUDENTID, PREFIX_COURSE, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

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
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser,  STUDENTID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + COURSE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + COURSE_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, STUDENTID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + COURSE_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, STUDENTID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB
                + TAG_DESC_TUTOR + TAG_DESC_STUDENT, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB
                + TAG_DESC_TUTOR + TAG_DESC_STUDENT, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + COURSE_DESC_BOB
                + TAG_DESC_TUTOR + TAG_DESC_STUDENT, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + COURSE_DESC_BOB
                + TAG_DESC_TUTOR + TAG_DESC_STUDENT, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COURSE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_STUDENT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, STUDENTID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + COURSE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + COURSE_DESC_BOB + TAG_DESC_TUTOR + TAG_DESC_STUDENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
