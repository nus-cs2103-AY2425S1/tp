package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OWED_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PAID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OWED_AMOUNT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OWED_AMOUNT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PAID_AMOUNT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PAID_AMOUNT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHEDULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHEDULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // EP: all fields present
        Student expectedStudent = new StudentBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB + PAID_AMOUNT_DESC_BOB
                        + OWED_AMOUNT_DESC_BOB,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_repeatedValue_failure() {
        // EP: repeated values
        String validExpectedStudentString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB + PAID_AMOUNT_DESC_AMY
                        + OWED_AMOUNT_DESC_BOB;

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

        // multiple schedules
        assertParseFailure(parser, SCHEDULE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // multiple rates
        assertParseFailure(parser, RATE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATE));

        // multiple owedAmounts
        assertParseFailure(parser, OWED_AMOUNT_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_OWED_AMOUNT));

        // multiple subjects
        assertParseFailure(parser, SUBJECT_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBJECT));

        // multiple paidAmount
        assertParseFailure(parser, PAID_AMOUNT_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PAID_AMOUNT));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedStudentString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + SCHEDULE_DESC_AMY + SUBJECT_DESC_AMY + RATE_DESC_AMY
                        + PAID_AMOUNT_DESC_AMY + OWED_AMOUNT_DESC_AMY + validExpectedStudentString,
                        Messages.getErrorMessageForDuplicatePrefixes(
                                PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE,
                                PREFIX_SCHEDULE, PREFIX_SUBJECT, PREFIX_RATE, PREFIX_PAID_AMOUNT, PREFIX_OWED_AMOUNT));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid schedule
        assertParseFailure(parser, INVALID_SCHEDULE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // invalid subject
        assertParseFailure(parser, INVALID_SUBJECT_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBJECT));

        // invalid rate
        assertParseFailure(parser, INVALID_RATE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATE));

        // invalid paidAmount
        assertParseFailure(parser, INVALID_PAID_AMOUNT_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PAID_AMOUNT));

        // invalid owedAmount
        assertParseFailure(parser, INVALID_OWED_AMOUNT_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_OWED_AMOUNT));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedStudentString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedStudentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedStudentString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid schedule
        assertParseFailure(parser, validExpectedStudentString + INVALID_SCHEDULE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // invalid subject
        assertParseFailure(parser, validExpectedStudentString + INVALID_SUBJECT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBJECT));

        // invalid rate
        assertParseFailure(parser, validExpectedStudentString + INVALID_RATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATE));

        // invalid paidAmount
        assertParseFailure(parser, validExpectedStudentString + INVALID_PAID_AMOUNT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PAID_AMOUNT));

        // invalid owedAmount
        assertParseFailure(parser, validExpectedStudentString + INVALID_OWED_AMOUNT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_OWED_AMOUNT));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // EP: optional fields missing

        // owedAmount missing
        Student expectedStudent = new StudentBuilder(AMY).withOwedAmount("0").build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + SCHEDULE_DESC_AMY + SUBJECT_DESC_AMY + RATE_DESC_AMY + PAID_AMOUNT_DESC_AMY,
                        new AddCommand(expectedStudent));

        // paidAmount missing
        expectedStudent = new StudentBuilder(AMY).withPaidAmount("0").build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + SCHEDULE_DESC_AMY + SUBJECT_DESC_AMY + RATE_DESC_AMY + OWED_AMOUNT_DESC_AMY,
                new AddCommand(expectedStudent));

        // both paidAmount and owedAmount missing
        expectedStudent = new StudentBuilder(AMY).withPaidAmount("0").withOwedAmount("0").build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + SCHEDULE_DESC_AMY + SUBJECT_DESC_AMY + RATE_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // EP: compulsory field missing
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB + PAID_AMOUNT_DESC_AMY, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB, expectedMessage);

        // missing schedule prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_SCHEDULE_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB, expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHEDULE_DESC_BOB + VALID_SUBJECT_BOB + RATE_DESC_BOB, expectedMessage);

        // missing rate prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + VALID_RATE_BOB, expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + VALID_SCHEDULE_BOB + VALID_SUBJECT_BOB + VALID_RATE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Address.MESSAGE_CONSTRAINTS);

        // invalid schedule
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_SCHEDULE_DESC + SUBJECT_DESC_BOB + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Schedule.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + INVALID_SUBJECT_DESC + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Subject.MESSAGE_CONSTRAINTS);

        // invalid rate
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB
                + INVALID_RATE_DESC, Rate.MESSAGE_CONSTRAINTS);

        // invalid paidAmount
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB
                + RATE_DESC_BOB + INVALID_PAID_AMOUNT_DESC, PaidAmount.MESSAGE_CONSTRAINTS);

        // invalid owedAmount
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB
                + RATE_DESC_BOB + INVALID_OWED_AMOUNT_DESC, OwedAmount.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB
                + OWED_AMOUNT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHEDULE_DESC_BOB + SUBJECT_DESC_BOB + RATE_DESC_BOB
                        + OWED_AMOUNT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
