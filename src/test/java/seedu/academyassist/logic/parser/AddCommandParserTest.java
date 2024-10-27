package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.IC_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.IC_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_IC_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_YEAR_GROUP_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.academyassist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.academyassist.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.SUBJECT_DESC_MULT;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_1;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_2;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_3;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_YEAR_GROUP_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.YEAR_GROUP_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.YEAR_GROUP_DESC_BOB;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_YEARGROUP;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academyassist.model.person.StudentId.TEMPORARY_STUDENT_ID;
import static seedu.academyassist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.AddCommand;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;
import seedu.academyassist.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withStudentId(TEMPORARY_STUDENT_ID.toString()).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + IC_DESC_BOB + ADDRESS_DESC_BOB + YEAR_GROUP_DESC_BOB + SUBJECT_DESC_BOB,
                new AddCommand(expectedPerson));


        // multiple subjects - all accepted
        Person expectedPersonMultipleSubjects = new PersonBuilder(BOB).withStudentId(TEMPORARY_STUDENT_ID.toString())
                .withSubjects(VALID_SUBJECT_1, VALID_SUBJECT_2, VALID_SUBJECT_3).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + IC_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + SUBJECT_DESC_MULT,
                new AddCommand(expectedPersonMultipleSubjects));
    }

    @Test
    public void parse_repeatedNonSubjectValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + IC_DESC_BOB + YEAR_GROUP_DESC_BOB + SUBJECT_DESC_BOB;

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

        // multiple ics
        assertParseFailure(parser, IC_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));

        // multiple year groups
        assertParseFailure(parser, YEAR_GROUP_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEARGROUP));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + IC_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEARGROUP, PREFIX_IC, PREFIX_NAME, PREFIX_ADDRESS,
                        PREFIX_EMAIL, PREFIX_PHONE));

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

        // invalid year group
        assertParseFailure(parser, INVALID_YEAR_GROUP_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEARGROUP));

        // invalid ic
        assertParseFailure(parser, INVALID_IC_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));

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

        // invalid year group
        assertParseFailure(parser, validExpectedPersonString + INVALID_YEAR_GROUP_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEARGROUP));

        // invalid ic
        assertParseFailure(parser, validExpectedPersonString + INVALID_IC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_IC));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB,
                expectedMessage);

        // missing year group prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_YEAR_GROUP_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB,
                expectedMessage);

        // missing ic prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + VALID_IC_BOB + SUBJECT_DESC_BOB,
                expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + VALID_SUBJECT_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + VALID_YEAR_GROUP_BOB + VALID_IC_BOB + VALID_SUBJECT_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // invalid year group
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_YEAR_GROUP_DESC + IC_DESC_BOB + SUBJECT_DESC_BOB, YearGroup.MESSAGE_CONSTRAINTS);

        // invalid ic
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + YEAR_GROUP_DESC_BOB + INVALID_IC_DESC + SUBJECT_DESC_BOB, Ic.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        // two subjects, one valid subject followed by one invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB + INVALID_SUBJECT_DESC,
                Subject.MESSAGE_CONSTRAINTS);

        // two subjects, one invalid subject followed by one valid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + INVALID_SUBJECT_DESC + SUBJECT_DESC_BOB,
                Subject.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + YEAR_GROUP_DESC_BOB + IC_DESC_BOB + SUBJECT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
