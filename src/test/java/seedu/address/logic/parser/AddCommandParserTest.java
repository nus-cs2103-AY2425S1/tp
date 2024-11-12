package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMERGENCY_CONTACT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_TIME_SUN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_TIME_TUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S1_NA;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S4_NT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME_SUN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME_TUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.student.Address;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).withSubjects(VALID_SUBJECT_ENGLISH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_S1_EXPRESS + SUBJECT_DESC_ENGLISH, new AddCommand(expectedStudent));

        // multiple spaces in name
        expectedStudent = new StudentBuilder(BOB).withName("Bob    Choo  ").withSubjects(VALID_SUBJECT_ENGLISH).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_S1_EXPRESS + SUBJECT_DESC_ENGLISH, new AddCommand(expectedStudent));

        // different casing in name
        expectedStudent = new StudentBuilder(BOB).withName("bOB cHOo").withSubjects(VALID_SUBJECT_ENGLISH).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_S1_EXPRESS + SUBJECT_DESC_ENGLISH, new AddCommand(expectedStudent));

        // multiple spaces and different casing in name
        expectedStudent = new StudentBuilder(BOB).withName("bOB    cHOo   ")
                .withSubjects(VALID_SUBJECT_ENGLISH).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_S1_EXPRESS + SUBJECT_DESC_ENGLISH, new AddCommand(expectedStudent));

        // with valid level NONE NONE
        expectedStudent = new StudentBuilder(BOB).withSubjects().withLevel("NONE NONE").build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB + ADDRESS_DESC_BOB ,
                new AddCommand(expectedStudent));

        // multiple subjects - all accepted
        expectedStudent = new StudentBuilder(BOB).withSubjects(VALID_SUBJECT_ENGLISH, VALID_SUBJECT_MATH).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                        + LEVEL_DESC_S1_EXPRESS + ADDRESS_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH,
                new AddCommand(expectedStudent));

        // multiple lesson times - all accepted
        expectedStudent = new StudentBuilder(BOB).withSubjects(VALID_SUBJECT_ENGLISH)
                .withLessonTimes(VALID_LESSON_TIME_SUN, VALID_LESSON_TIME_TUE).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                        + LEVEL_DESC_S1_EXPRESS + ADDRESS_DESC_BOB + SUBJECT_DESC_ENGLISH
                        + LESSON_TIME_SUN_DESC + LESSON_TIME_TUE_DESC,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_repeatedNonSubjectNonLessonTimeValue_failure() {
        String validExpectedStudentString = NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_S1_NA + SUBJECT_DESC_ENGLISH;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple emergency contacts
        assertParseFailure(parser, EMERGENCY_CONTACT_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_CONTACT));

        // multiple levels
        assertParseFailure(parser, LEVEL_DESC_S4_NT + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEVEL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedStudentString + PHONE_DESC_AMY + EMERGENCY_CONTACT_DESC_AMY
                        + NAME_DESC_AMY + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS,
                        PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));


        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, validExpectedStudentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedStudentString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero subjects
        Student expectedStudent = new StudentBuilder(AMY).withSubjects().withLevel("NONE NONE").build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMERGENCY_CONTACT_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedStudent));

        // zero lesson times
        expectedStudent = new StudentBuilder(AMY).withSubjects().withLevel("NONE NONE").withLessonTimes().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMERGENCY_CONTACT_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedStudent));

        // zero level
        expectedStudent = new StudentBuilder(AMY).withLevel("").withSubjects().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMERGENCY_CONTACT_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing emergency contact prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH, Phone.MESSAGE_CONSTRAINTS);

        // invalid emergencyContact
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMERGENCY_CONTACT_DESC
                + ADDRESS_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH, Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + INVALID_ADDRESS_DESC + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH, Address.MESSAGE_CONSTRAINTS);

        // adding student with subject without level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_SUBJECT_DESC + VALID_SUBJECT_ENGLISH, Subject.MESSAGE_LEVEL_NEEDED);

        // adding student with incorrect level S5 EXPRESS
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + " l/S5 EXPRESS", Level.MESSAGE_CONSTRAINTS);

        // adding student with incorrect level S2 NONE
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + " l/S2 NONE", Level.MESSAGE_CONSTRAINTS);

        // adding student with incorrect level NONE IP
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + " l/NONE IP", Level.MESSAGE_CONSTRAINTS);

        // adding student with invalid lesson time
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_LESSON_TIME_DESC, LessonTime.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMERGENCY_CONTACT_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
