package tutorease.address.logic.parser;

import static tutorease.address.logic.commands.CommandTestUtil.DURATION_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_CHAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_TWENTY_FIVE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_ZERO;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_LOCATION_INDEX_CHAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_LOCATION_INDEX_ZERO;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DAY;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_HOUR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_MINUTE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_MONTH;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_START_DATE_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_CHAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_ZERO;
import static tutorease.address.logic.commands.CommandTestUtil.LOCATION_INDEX_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorease.address.logic.commands.CommandTestUtil.START_DATE_TIME_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.LocationIndex;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.testutil.LessonBuilder;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Lesson expectedLesson = new LessonBuilder().build();
        StudentId studentId = new StudentId(VALID_STUDENT_ID);
        AddLessonCommand expectedCommand = new AddLessonCommand(studentId, expectedLesson.getStartDateTime(),
                expectedLesson.getLocationIndex(), expectedLesson.getEndDateTime());
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC + LOCATION_INDEX_DESC
                        + START_DATE_TIME_DESC + DURATION_DESC,
                expectedCommand);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedLessonString = STUDENT_ID_DESC + LOCATION_INDEX_DESC + START_DATE_TIME_DESC + DURATION_DESC;
        // multiple studentId
        assertParseFailure(parser, STUDENT_ID_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_STUDENT_ID));
        // multiple locationIndex
        assertParseFailure(parser, LOCATION_INDEX_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_LOCATION_INDEX));
        // multiple startDateTime
        assertParseFailure(parser, START_DATE_TIME_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));
        // multiple duration
        assertParseFailure(parser, DURATION_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));

        // invalid value followed by valid value
        // invalid studentId followed by valid studentId
        assertParseFailure(parser, INVALID_STUDENT_ID_CHAR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_STUDENT_ID));
        assertParseFailure(parser, INVALID_STUDENT_ID_ZERO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_STUDENT_ID));
        // invalid locationIndex followed by valid locationIndex
        assertParseFailure(parser, INVALID_LOCATION_INDEX_ZERO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_LOCATION_INDEX));
        assertParseFailure(parser, INVALID_LOCATION_INDEX_CHAR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_LOCATION_INDEX));
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
        assertParseFailure(parser, INVALID_DURATION_CHAR + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));
        assertParseFailure(parser, INVALID_DURATION_ZERO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));
        assertParseFailure(parser, INVALID_DURATION_TWENTY_FIVE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_DURATION));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENT_ID_CHAR + LOCATION_INDEX_DESC + START_DATE_TIME_DESC
                        + DURATION_DESC,
                StudentId.MESSAGE_CONSTRAINTS);

        // invalid locationIndex
        assertParseFailure(parser, STUDENT_ID_DESC + INVALID_LOCATION_INDEX_CHAR + START_DATE_TIME_DESC
                        + DURATION_DESC,
                LocationIndex.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + INVALID_LOCATION_INDEX_ZERO + START_DATE_TIME_DESC
                        + DURATION_DESC,
                LocationIndex.MESSAGE_CONSTRAINTS);

        // invalid startDateTime
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + INVALID_START_DATE_DAY
                        + DURATION_DESC,
                StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + INVALID_START_DATE_MONTH
                        + DURATION_DESC,
                StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + INVALID_START_DATE_YEAR
                        + DURATION_DESC,
                StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + INVALID_START_DATE_HOUR
                        + DURATION_DESC,
                StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + INVALID_START_DATE_MINUTE
                        + DURATION_DESC,
                StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);

        // invalid duration
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + START_DATE_TIME_DESC
                        + INVALID_DURATION_CHAR,
                EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + START_DATE_TIME_DESC
                        + INVALID_DURATION_ZERO,
                EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, STUDENT_ID_DESC + LOCATION_INDEX_DESC + START_DATE_TIME_DESC
                        + INVALID_DURATION_TWENTY_FIVE,
                EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
    }
}
