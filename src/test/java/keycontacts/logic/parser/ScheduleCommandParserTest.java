package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static keycontacts.logic.commands.CommandTestUtil.VALID_DAY;
import static keycontacts.logic.commands.CommandTestUtil.VALID_DAY_DESC;
import static keycontacts.logic.commands.CommandTestUtil.VALID_END_TIME;
import static keycontacts.logic.commands.CommandTestUtil.VALID_END_TIME_DESC;
import static keycontacts.logic.commands.CommandTestUtil.VALID_START_TIME;
import static keycontacts.logic.commands.CommandTestUtil.VALID_START_TIME_DESC;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DAY;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import keycontacts.logic.Messages;
import keycontacts.logic.commands.ScheduleCommand;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;

public class ScheduleCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

    private final ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_noMissingParts_success() {
        ScheduleCommand expectedCommand = new ScheduleCommand(
                INDEX_FIRST_STUDENT,
                new RegularLesson(new Day(VALID_DAY), new Time(VALID_START_TIME), new Time(VALID_END_TIME)));

        String userInput = INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + VALID_START_TIME_DESC
                + VALID_END_TIME_DESC;

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_fieldNotPresent_failure() {
        // missing day
        String userInput = INDEX_FIRST_STUDENT.getOneBased() + VALID_START_TIME_DESC + VALID_END_TIME_DESC;;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // missing start time
        userInput = INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + VALID_END_TIME_DESC;;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // missing end time
        userInput = INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + VALID_START_TIME_DESC;;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_repeatedField_failure() {
        String userInput = INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + VALID_START_TIME_DESC
                + VALID_END_TIME_DESC;

        // repeated day
        assertParseFailure(parser, userInput + VALID_DAY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // repeated start time
        assertParseFailure(parser, userInput + VALID_START_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_TIME));

        // repeated end time
        assertParseFailure(parser, userInput + VALID_END_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END_TIME));

        // all fields repeated
        assertParseFailure(parser, userInput + VALID_DAY_DESC + VALID_START_TIME_DESC + VALID_END_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid day
        assertParseFailure(parser, INDEX_FIRST_STUDENT.getOneBased() + INVALID_DAY_DESC + VALID_START_TIME_DESC
                + VALID_END_TIME_DESC, Day.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + INVALID_START_TIME_DESC
                + VALID_END_TIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + VALID_START_TIME_DESC
                + INVALID_END_TIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // start time not before end time
        assertParseFailure(parser, INDEX_FIRST_STUDENT.getOneBased() + VALID_DAY_DESC + " " + PREFIX_START_TIME
                + VALID_END_TIME + VALID_END_TIME_DESC, Lesson.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_indexNotPresent_failure() {
        String userInput = PREFIX_PIECE_NAME + VALID_DAY_DESC + VALID_START_TIME_DESC + VALID_END_TIME_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1" + VALID_DAY_DESC + VALID_START_TIME_DESC + VALID_END_TIME_DESC,
                MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + VALID_DAY_DESC + VALID_START_TIME_DESC + VALID_END_TIME_DESC,
                MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 r" + VALID_DAY_DESC + VALID_START_TIME_DESC + VALID_END_TIME_DESC,
                MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 i/s" + VALID_DAY_DESC + VALID_START_TIME_DESC + VALID_END_TIME_DESC,
                MESSAGE_INVALID_FORMAT);
    }


}
