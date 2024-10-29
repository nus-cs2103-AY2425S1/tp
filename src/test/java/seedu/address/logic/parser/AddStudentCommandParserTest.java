package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MADE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_YEAR1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MADE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_YEAR1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalContacts.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;

public class AddStudentCommandParserTest {
    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(AMY).withTags(VALID_TAG_YEAR1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY
                + STUDENTID_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_YEAR1, new AddStudentCommand(expectedStudent));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(AMY).withTags(VALID_TAG_YEAR1, VALID_TAG_MADE_PAYMENT)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + STUDENTID_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1,
                new AddStudentCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedStudentString = NAME_DESC_AMY + STUDENTID_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_YEAR1;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple student IDs
        assertParseFailure(parser, STUDENTID_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY
                        + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddStudentCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_AMY + STUDENTID_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing student ID prefix
        assertParseFailure(parser, NAME_DESC_AMY + VALID_STUDENTID_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY + VALID_PHONE_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY + PHONE_DESC_AMY
                        + VALID_EMAIL_AMY + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + VALID_ADDRESS_AMY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_AMY + VALID_STUDENTID_AMY + VALID_PHONE_AMY
                        + VALID_EMAIL_AMY + VALID_ADDRESS_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENTID_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1, Name.MESSAGE_CONSTRAINTS);

        // invalid student ID
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_STUDENTID_DESC
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1, StudentId.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY
                + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY
                + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC + VALID_TAG_YEAR1, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENTID_DESC_AMY
                        + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_AMY
                        + STUDENTID_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + TAG_DESC_MADE_PAYMENT + TAG_DESC_YEAR1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
    }
}
