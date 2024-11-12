package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.edulog.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.edulog.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.FEE;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_NAME_DESC_NUMERIC;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_NAME_DESC_SYMBOL;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.edulog.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edulog.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.edulog.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edulog.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edulog.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.edulog.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edulog.testutil.TypicalStudents.AMY;
import static seedu.edulog.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.AddCommand;
import seedu.edulog.model.student.Address;
import seedu.edulog.model.student.Email;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Phone;
import seedu.edulog.model.student.Student;
import seedu.edulog.model.tag.Tag;
import seedu.edulog.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + FEE, new AddCommand(expectedStudent));


        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + FEE,
                new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedStudentString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + FEE;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedStudentString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC_SYMBOL + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC_NUMERIC + validExpectedStudentString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid edulog
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC_SYMBOL,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedStudentString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedStudentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid edulog
        assertParseFailure(parser, validExpectedStudentString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + FEE,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FEE,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FEE,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + FEE,
                expectedMessage);

        // missing edulog prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + FEE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + FEE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name - symbols
        assertParseFailure(parser, INVALID_NAME_DESC_SYMBOL + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FEE, Name.MESSAGE_CONSTRAINTS);

        // invalid name - purely numerical name
        assertParseFailure(parser, INVALID_NAME_DESC_NUMERIC + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FEE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FEE, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FEE, Email.MESSAGE_CONSTRAINTS);

        // invalid edulog
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FEE, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + FEE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC_SYMBOL + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + FEE, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FEE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
