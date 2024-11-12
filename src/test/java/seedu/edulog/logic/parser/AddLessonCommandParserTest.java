package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.commands.CommandTestUtil.DAY_DESC_BIO;
import static seedu.edulog.logic.commands.CommandTestUtil.DAY_DESC_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BIO;
import static seedu.edulog.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.END_TIME_DESC_BIO;
import static seedu.edulog.logic.commands.CommandTestUtil.END_TIME_DESC_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC_EMPTY;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC_TOO_LONG;
import static seedu.edulog.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.edulog.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.edulog.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.edulog.logic.commands.CommandTestUtil.START_TIME_DESC_BIO;
import static seedu.edulog.logic.commands.CommandTestUtil.START_TIME_DESC_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_DAY_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_END_TIME_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_START_TIME_MATH;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.AddLessonCommand;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;
import seedu.edulog.testutil.LessonBuilder;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder()
            .withDescription(VALID_DESCRIPTION_MATH)
            .withDayOfWeek(VALID_DAY_MATH)
            .withStartTime(VALID_START_TIME_MATH)
            .withEndTime(VALID_END_TIME_MATH)
            .build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_MATH + DAY_DESC_MATH
            + START_TIME_DESC_MATH + END_TIME_DESC_MATH, new AddLessonCommand(expectedLesson));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedLessonString = DESCRIPTION_DESC_MATH + DAY_DESC_MATH
            + START_TIME_DESC_MATH + END_TIME_DESC_MATH;

        // multiple names
        assertParseFailure(parser, DESCRIPTION_DESC_BIO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple phones
        assertParseFailure(parser, DAY_DESC_BIO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DAY));

        // multiple emails
        assertParseFailure(parser, START_TIME_DESC_BIO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));

        // multiple addresses
        assertParseFailure(parser, END_TIME_DESC_BIO + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END_TIME));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedLessonString + DESCRIPTION_DESC_BIO + DAY_DESC_BIO
                    + START_TIME_DESC_BIO + END_TIME_DESC_BIO,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION, PREFIX_START_DAY,
                    PREFIX_START_TIME, PREFIX_END_TIME));

        // invalid value followed by valid value

        // invalid description (too short)
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC_EMPTY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid description (too long)
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC_TOO_LONG + validExpectedLessonString,
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid day
        assertParseFailure(parser, INVALID_DAY_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DAY));

        // invalid time
        assertParseFailure(parser, INVALID_TIME_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));


        // valid value followed by invalid value

        // invalid description
        assertParseFailure(parser, validExpectedLessonString + INVALID_DESCRIPTION_DESC_EMPTY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid description
        assertParseFailure(parser, validExpectedLessonString + INVALID_DESCRIPTION_DESC_TOO_LONG,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid day
        assertParseFailure(parser, validExpectedLessonString + INVALID_DAY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DAY));

        // invalid time
        assertParseFailure(parser, validExpectedLessonString + INVALID_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_DESCRIPTION_MATH + DAY_DESC_MATH
                + START_TIME_DESC_MATH + END_TIME_DESC_MATH,
                expectedMessage);

        // missing day of week prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_MATH + VALID_DAY_MATH
                + START_TIME_DESC_MATH + END_TIME_DESC_MATH,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_MATH + DAY_DESC_MATH
                + VALID_START_TIME_MATH + END_TIME_DESC_MATH,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_MATH + DAY_DESC_MATH
                + START_TIME_DESC_MATH + VALID_END_TIME_MATH,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_DESCRIPTION_MATH + VALID_DAY_MATH
                + VALID_START_TIME_MATH + VALID_END_TIME_MATH,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description - empty
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC_EMPTY + DAY_DESC_MATH
                + START_TIME_DESC_MATH + END_TIME_DESC_MATH, Description.DESCRIPTION_EMPTY);

        // invalid description - too long
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC_TOO_LONG + DAY_DESC_MATH
            + START_TIME_DESC_MATH + END_TIME_DESC_MATH, Description.DESCRIPTION_TOO_LONG);

        // invalid spelling of day of the week
        assertParseFailure(parser, DESCRIPTION_DESC_MATH + INVALID_DAY_DESC
            + START_TIME_DESC_MATH + END_TIME_DESC_MATH, Day.INVALID_DAY_OF_WEEK);

        // invalid time format
        assertParseFailure(parser, DESCRIPTION_DESC_MATH + DAY_DESC_MATH
            + INVALID_TIME_DESC + END_TIME_DESC_MATH, LessonTime.NOT_24H_FORMAT);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, DESCRIPTION_DESC_MATH + INVALID_DAY_DESC + INVALID_TIME_DESC
                + END_TIME_DESC_MATH, Day.INVALID_DAY_OF_WEEK);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DESCRIPTION_DESC_MATH + DAY_DESC_MATH
                + START_TIME_DESC_MATH + END_TIME_DESC_MATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
    }
}
