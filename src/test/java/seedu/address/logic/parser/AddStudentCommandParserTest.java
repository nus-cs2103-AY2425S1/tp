package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.CHRIS;
import static seedu.address.testutil.TypicalPersons.MICHAEL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;

public class AddStudentCommandParserTest {

    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(MICHAEL).withTags(VALID_TAG_FRIEND).withDaysAttended(10).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL
                        + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                        + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL,
                new AddStudentCommand(expectedStudent));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(MICHAEL)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).withDaysAttended(10).build();
        assertParseSuccess(parser,
                NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL
                        + CLASSES_DESC_MICHAEL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL,
                new AddStudentCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(CHRIS).withTags().withDaysAttended(10).build();
        assertParseSuccess(parser, NAME_DESC_CHRIS + GENDER_DESC_BOB + PHONE_DESC_CHRIS + EMAIL_DESC_CHRIS
            + ADDRESS_DESC_CHRIS + SUBJECT_DESC_CHRIS + CLASSES_DESC_CHRIS + ATTENDANCE_DESC_CHRIS, new AddStudentCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_MICHAEL + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_MICHAEL + VALID_PHONE_MICHAEL + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_MICHAEL + PHONE_DESC_MICHAEL + VALID_EMAIL_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_MICHAEL + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                + VALID_ADDRESS_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, NAME_DESC_MICHAEL + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + CLASSES_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);

        // missing classes prefix
        assertParseFailure(parser, NAME_DESC_MICHAEL + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);

        // missing attendance prefix
        assertParseFailure(parser, NAME_DESC_MICHAEL + PHONE_DESC_MICHAEL + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_MICHAEL + VALID_PHONE_MICHAEL + VALID_EMAIL_MICHAEL
                + VALID_ADDRESS_MICHAEL + VALID_SUBJECT_MICHAEL + VALID_CLASSES_MICHAEL + ATTENDANCE_DESC_MICHAEL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                        + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL
                        + CLASSES_DESC_MICHAEL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, Name.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_MICHAEL + INVALID_GENDER_DESC + PHONE_DESC_MICHAEL
                        + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, Gender.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + INVALID_PHONE_DESC
                        + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                        + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                        + EMAIL_DESC_MICHAEL
                        + INVALID_ADDRESS_DESC + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, Address.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                        + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + INVALID_SUBJECT_DESC + CLASSES_DESC_MICHAEL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, Subject.MESSAGE_CONSTRAINTS);

        // invalid classes
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                        + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + INVALID_CLASSES_DESC
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, "Classes should be valid!");

        // invalid tag
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                        + EMAIL_DESC_MICHAEL
                        + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                        + INVALID_TAG_DESC + INVALID_TAG_DESC + ATTENDANCE_DESC_MICHAEL, Tag.MESSAGE_CONSTRAINTS);

        // invalid attendance
        assertParseFailure(parser, NAME_DESC_MICHAEL + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + INVALID_ATTENDANCE_DESC_MICHAEL, DaysAttended.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_MICHAEL + PHONE_DESC_MICHAEL
                + EMAIL_DESC_MICHAEL
                + INVALID_ADDRESS_DESC + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL + ATTENDANCE_DESC_MICHAEL, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_MICHAEL + GENDER_DESC_BOB + PHONE_DESC_MICHAEL
                + EMAIL_DESC_MICHAEL
                + ADDRESS_DESC_MICHAEL + SUBJECT_DESC_MICHAEL + CLASSES_DESC_MICHAEL + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND + ATTENDANCE_DESC_MICHAEL, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
    }
}
