package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.CancelLessonCommand;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Time;

public class CancelLessonCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelLessonCommand.MESSAGE_USAGE);
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final Date VALID_DATE = new Date("15-10-2024");
    private static final Time VALID_TIME = new Time("10:30");
    private final CancelLessonCommandParser parser = new CancelLessonCommandParser();

    @Test
    public void parse_noMissingParts_success() {
        CancelLessonCommand expectedCommand = new CancelLessonCommand(VALID_INDEX, VALID_DATE, VALID_TIME);
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "10:30";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_dateNotPresent_failure() {
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_START_TIME + "14:30";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_startTimeNotPresent_failure() {
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_DATE + "15-10-2024";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_DATE + "2024-10-15 " + PREFIX_START_TIME + "14:30";
        assertParseFailure(parser, userInput, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStartTimeFormat_failure() {
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "1430";
        assertParseFailure(parser, userInput, Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidIndexFormat_failure() {
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "-1" + PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "10:30",
                expectedString);
        assertParseFailure(parser, "0" + PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "10:30",
                expectedString);
        assertParseFailure(parser, "1 r" + PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "10:30",
                expectedString);
        assertParseFailure(parser, "1 i/s" + PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "10:30",
                expectedString);
    }

    @Test
    public void parse_missingPreamble_failure() {
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREFIX_DATE + "15-10-2024 " + PREFIX_START_TIME + "10:30", expectedString);
    }

    @Test
    public void parse_missingPrefixes_failure() {
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_INDEX.getOneBased() + " " + PREFIX_DATE + PREFIX_START_TIME
                + "10:30", expectedString);
    }
}

