package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTeacherCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Teacher;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TeacherBuilder;

public class AddTeacherCommandParserTest {
    private AddTeacherCommandParser parser = new AddTeacherCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Teacher expectedTeacher = new TeacherBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withSubject(VALID_SUBJECT_BOB).withTags(VALID_TAG_FRIEND).withClasses(VALID_CLASSES_BOB).build();

        // multiple tags - all accepted
        Teacher expectedTeacherMultipleTags = new TeacherBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withSubject(VALID_SUBJECT_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .withClasses(VALID_CLASSES_BOB).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND + CLASS_DESC_BOB, new AddTeacherCommand(expectedTeacherMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Teacher expectedTeacher = new TeacherBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withSubject(VALID_SUBJECT_BOB).withTags().withClasses(VALID_CLASSES_BOB).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + CLASS_DESC_BOB, new AddTeacherCommand(expectedTeacher));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeacherCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + CLASS_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + CLASS_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + CLASS_DESC_BOB, expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + VALID_GENDER_BOB + SUBJECT_DESC_BOB, expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + VALID_SUBJECT_BOB + CLASS_DESC_BOB, expectedMessage);

        // missing class prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + VALID_SUBJECT_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
            + VALID_GENDER_BOB + VALID_SUBJECT_BOB + VALID_CLASSES_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
            + CLASS_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
            + CLASS_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
            + CLASS_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
            + CLASS_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + INVALID_GENDER_DESC + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
            + CLASS_DESC_BOB, Gender.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + INVALID_SUBJECT_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
            + CLASS_DESC_BOB, Subject.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND
            + CLASS_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
            + GENDER_DESC_BOB + SUBJECT_DESC_BOB + CLASS_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + SUBJECT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + CLASS_DESC_BOB, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeacherCommand.MESSAGE_USAGE));
    }
}
