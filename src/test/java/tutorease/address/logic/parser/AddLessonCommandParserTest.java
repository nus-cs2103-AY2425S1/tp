package tutorease.address.logic.parser;

import static tutorease.address.commons.util.DateTimeUtil.INVALID_DAY_MESSAGE;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_HOUR_MESSAGE;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_MINUTE_MESSAGE;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_MONTH_MESSAGE;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_YEAR_MESSAGE;
import static tutorease.address.logic.commands.CommandTestUtil.DURATION_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.FEE_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_CHAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_TWENTY_FIVE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_ZERO;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_FEE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DAY;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_HOUR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_LEAP_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_MINUTE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_MONTH;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_CHAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_ZERO;
import static tutorease.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorease.address.logic.commands.CommandTestUtil.START_DATE_TIME_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.START_DATE_TIME_LEAP_YEAR_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.UPPERCASE_DURATION_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.UPPERCASE_FEE_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.UPPERCASE_START_DATE_TIME_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.UPPERCASE_STUDENT_ID_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_END_DATE;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_END_DATE_LEAP_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_FEE;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_START_DATE;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_START_DATE_LEAP_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_FEE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.testutil.LessonBuilder;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Lesson expectedLesson = new LessonBuilder().build();
        StudentId studentId = new StudentId(VALID_STUDENT_ID);

        AddLessonCommand expectedCommand = new AddLessonCommand(studentId, expectedLesson.getFee(),
                expectedLesson.getStartDateTime(), expectedLesson.getEndDateTime());

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC + FEE_DESC
                        + START_DATE_TIME_DESC + DURATION_DESC,
                expectedCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        // missing all fields
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);

        // missing studentId
        String expectedStudentIdMessage = String.format(Messages.MISSING_PREFIX, PREFIX_STUDENT_ID,
                AddLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, FEE_DESC + START_DATE_TIME_DESC + DURATION_DESC,
                expectedStudentIdMessage);

        // missing fee
        String expectedFeeMessage = String.format(Messages.MISSING_PREFIX, PREFIX_FEE,
                AddLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, STUDENT_ID_DESC + START_DATE_TIME_DESC + DURATION_DESC,
                expectedFeeMessage);

        // missing startDateTime
        String expectedDateMessage = String.format(Messages.MISSING_PREFIX, PREFIX_START_DATE,
                AddLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + DURATION_DESC,
                expectedDateMessage);

        // missing duration
        String expectedDurationMessage = String.format(Messages.MISSING_PREFIX, PREFIX_DURATION,
                AddLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + START_DATE_TIME_DESC,
                expectedDurationMessage);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedLessonString = STUDENT_ID_DESC + FEE_DESC + START_DATE_TIME_DESC + DURATION_DESC;

        // multiple studentId
        assertParseFailure(parser, STUDENT_ID_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_STUDENT_ID));

        // multiple startDateTime
        assertParseFailure(parser, START_DATE_TIME_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));

        // multiple duration
        assertParseFailure(parser, DURATION_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));

        // multiple fee
        assertParseFailure(parser, FEE_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_FEE));

        // invalid value followed by valid value
        // invalid studentId followed by valid studentId
        assertParseFailure(parser, INVALID_STUDENT_ID_CHAR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_STUDENT_ID));
        assertParseFailure(parser, INVALID_STUDENT_ID_ZERO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_STUDENT_ID));

        // invalid fee followed by valid fee
        assertParseFailure(parser, INVALID_FEE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_FEE));

        // invalid startDateTime followed by valid startDateTime
        assertParseFailure(parser, INVALID_START_DATE_DAY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));
        assertParseFailure(parser, INVALID_START_DATE_MONTH + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));
        assertParseFailure(parser, INVALID_START_DATE_YEAR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));
        assertParseFailure(parser, INVALID_START_DATE_HOUR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));
        assertParseFailure(parser, INVALID_START_DATE_MINUTE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));

        // invalid duration followed by valid duration
        assertParseFailure(parser, " " + PREFIX_DURATION + INVALID_DURATION_CHAR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));
        assertParseFailure(parser, " " + PREFIX_DURATION + INVALID_DURATION_ZERO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));
        assertParseFailure(parser, " " + PREFIX_DURATION + INVALID_DURATION_TWENTY_FIVE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENT_ID_CHAR + FEE_DESC + START_DATE_TIME_DESC
                        + DURATION_DESC,
                StudentId.MESSAGE_CONSTRAINTS);

        // invalid fee
        assertParseFailure(parser, STUDENT_ID_DESC + INVALID_FEE + START_DATE_TIME_DESC
                        + DURATION_DESC,
                Fee.MESSAGE_CONSTRAINTS);

        // invalid startDateTime
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + INVALID_START_DATE_DAY
                        + DURATION_DESC,
                String.format(INVALID_DAY_MESSAGE, 0, 31));
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + INVALID_START_DATE_MONTH
                        + DURATION_DESC,
                String.format(INVALID_MONTH_MESSAGE, 0));
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + INVALID_START_DATE_YEAR
                        + DURATION_DESC,
                String.format(INVALID_YEAR_MESSAGE, 0));
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + INVALID_START_DATE_HOUR
                        + DURATION_DESC,
                String.format(INVALID_HOUR_MESSAGE, 25));
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + INVALID_START_DATE_MINUTE
                        + DURATION_DESC,
                String.format(INVALID_MINUTE_MESSAGE, 60));
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + INVALID_START_DATE_LEAP_YEAR
                        + DURATION_DESC,
                String.format(INVALID_DAY_MESSAGE, 29, 28));

        // invalid duration
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + START_DATE_TIME_DESC
                        + " " + PREFIX_DURATION + INVALID_DURATION_CHAR,
                EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + START_DATE_TIME_DESC
                        + " " + PREFIX_DURATION + INVALID_DURATION_ZERO,
                EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + FEE_DESC + START_DATE_TIME_DESC
                        + " " + PREFIX_DURATION + INVALID_DURATION_TWENTY_FIVE,
                EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_upperCasePrefixes_success() throws ParseException {
        Lesson expectedLesson = new LessonBuilder()
                .withFee(VALID_FEE)
                .withStartDateTime(VALID_START_DATE)
                .withEndDateTime(VALID_END_DATE)
                .build();

        AddLessonCommand expectedCommand = new AddLessonCommand(new StudentId(VALID_STUDENT_ID),
                expectedLesson.getFee(), expectedLesson.getStartDateTime(), expectedLesson.getEndDateTime());

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + UPPERCASE_STUDENT_ID_DESC + FEE_DESC
                        + START_DATE_TIME_DESC + DURATION_DESC,
                expectedCommand);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC + UPPERCASE_FEE_DESC
                        + START_DATE_TIME_DESC + DURATION_DESC,
                expectedCommand);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC + FEE_DESC
                        + UPPERCASE_START_DATE_TIME_DESC + DURATION_DESC,
                expectedCommand);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC + FEE_DESC
                + START_DATE_TIME_DESC + UPPERCASE_DURATION_DESC,
                expectedCommand);
    }

    @Test
    public void parse_validLeapYear_success() throws ParseException {
        Lesson expectedLesson = new LessonBuilder()
                .withStartDateTime(VALID_START_DATE_LEAP_YEAR)
                .withEndDateTime(VALID_END_DATE_LEAP_YEAR)
                .build();
        StudentId studentId = new StudentId(VALID_STUDENT_ID);
        AddLessonCommand expectedCommand = new AddLessonCommand(studentId, expectedLesson.getFee(),
                expectedLesson.getStartDateTime(), expectedLesson.getEndDateTime());
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC + FEE_DESC
                        + START_DATE_TIME_LEAP_YEAR_DESC + DURATION_DESC,
                expectedCommand);
    }
}
