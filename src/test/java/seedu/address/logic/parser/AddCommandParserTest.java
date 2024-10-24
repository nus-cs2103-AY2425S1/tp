package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEW_SCORE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEW_SCORE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTERVIEW_SCORE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.InterviewScore;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withSkills(VALID_SKILL_PYTHON, VALID_SKILL_JAVA)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_PENDING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND, VALID_TAG_PENDING)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple jobs
        assertParseFailure(parser, JOB_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_JOB));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple interview scores
        assertParseFailure(parser, INTERVIEW_SCORE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INTERVIEW_SCORE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + JOB_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + INTERVIEW_SCORE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_JOB, PREFIX_EMAIL,
                        PREFIX_PHONE, PREFIX_INTERVIEW_SCORE));

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

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid job
        assertParseFailure(parser, validExpectedPersonString + INVALID_JOB_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_JOB));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid interview score
        assertParseFailure(parser, validExpectedPersonString + INVALID_INTERVIEW_SCORE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INTERVIEW_SCORE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags, pending tag is included by default
        Person expectedPerson = new PersonBuilder(AMY).withSkills(VALID_SKILL_PYTHON)
                .withTags(VALID_TAG_PENDING).build();
        assertParseSuccess(parser, NAME_DESC_AMY + JOB_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + SKILL_DESC_PYTHON + INTERVIEW_SCORE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INTERVIEW_SCORE_DESC_BOB, expectedMessage);

        // missing job prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_JOB_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INTERVIEW_SCORE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + INTERVIEW_SCORE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + INTERVIEW_SCORE_DESC_BOB, expectedMessage);

        // missing interview score prefix
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_INTERVIEW_SCORE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_JOB_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_INTERVIEW_SCORE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid job
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_JOB_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Job.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid skills
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_SKILL_DESC + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Skill.MESSAGE_CONSTRAINTS);

        // invalid interview score
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INVALID_INTERVIEW_SCORE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, InterviewScore.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + JOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SKILL_DESC_PYTHON + SKILL_DESC_JAVA + INTERVIEW_SCORE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
