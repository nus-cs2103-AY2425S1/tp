package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDY_GROUP_TAG_DESC_1A;
import static seedu.address.logic.commands.CommandTestUtil.STUDY_GROUP_TAG_DESC_2B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_1A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_2B;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withStudyGroupTags(VALID_STUDY_GROUP_TAG_2B).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + EMAIL_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple study groups - all accepted
        Person expectedPersonMultipleStudyGroups = new PersonBuilder(BOB)
                .withStudyGroupTags(VALID_STUDY_GROUP_TAG_2B, VALID_STUDY_GROUP_TAG_1A)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + STUDY_GROUP_TAG_DESC_1A
                        + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB,
                new AddCommand(expectedPersonMultipleStudyGroups));
    }

    @Test
    public void parse_repeatedNonStudyGroupTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + EMAIL_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_GENDER,
                        PREFIX_AGE, PREFIX_DETAIL));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid gender
        assertParseFailure(parser, INVALID_GENDER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid age
        assertParseFailure(parser, INVALID_AGE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid gender
        assertParseFailure(parser, validExpectedPersonString + INVALID_GENDER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid age
        assertParseFailure(parser, validExpectedPersonString + INVALID_AGE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

    }

    @Test
    public void parse_detailFieldsMissing_success() {
        // no detail
        Person expectedPerson = new PersonBuilder(AMY).withDetail("").build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY
                + STUDY_GROUP_TAG_DESC_1A, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_studyGroupTagFieldsMissing_success() {
        // zero study group
        Person expectedPerson = new PersonBuilder(AMY).withStudyGroupTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + GENDER_DESC_AMY + AGE_DESC_AMY
                + DETAIL_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_EMAIL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                + STUDY_GROUP_TAG_DESC_1A + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_EMAIL_DESC + GENDER_DESC_BOB + AGE_DESC_BOB
                + STUDY_GROUP_TAG_DESC_1A + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + INVALID_GENDER_DESC + AGE_DESC_BOB
                + STUDY_GROUP_TAG_DESC_1A + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB, Gender.MESSAGE_CONSTRAINTS);

        // invalid age
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + GENDER_DESC_BOB + INVALID_AGE_DESC
                + STUDY_GROUP_TAG_DESC_1A + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB, Age.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB + INVALID_GENDER_DESC + AGE_DESC_BOB
                + STUDY_GROUP_TAG_DESC_2B + DETAIL_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + EMAIL_DESC_BOB
                + STUDY_GROUP_TAG_DESC_1A + STUDY_GROUP_TAG_DESC_2B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
