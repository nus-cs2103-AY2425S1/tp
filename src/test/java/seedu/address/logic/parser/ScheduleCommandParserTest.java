package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Schedule;


public class ScheduleCommandParserTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_DATE = "2024-10-04 1400";
    private static final String INVALID_DATE = "invalid_date";

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, "d/" + VALID_DATE, MESSAGE_INVALID_DATE_FORMAT);

        // no date specified
        assertParseFailure(parser, VALID_NAME, MESSAGE_INVALID_DATE_FORMAT);

        // no name and no date specified
        assertParseFailure(parser, "", MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {
        // invalid date format
        assertParseFailure(parser, VALID_NAME + " d/" + INVALID_DATE, MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_validInput_success() {
        // valid input with name and date
        String userInput = VALID_NAME + " d/" + VALID_DATE;
        LocalDateTime expectedDate = LocalDateTime.parse(VALID_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        Schedule expectedSchedule = new Schedule(VALID_DATE);
        ScheduleCommand expectedCommand = new ScheduleCommand(VALID_NAME, expectedSchedule);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        // valid input with extra whitespaces
        String userInput = "    " + VALID_NAME + "     d/" + VALID_DATE + "   ";
        LocalDateTime expectedDate = LocalDateTime.parse(VALID_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        Schedule expectedSchedule = new Schedule(VALID_DATE);
        ScheduleCommand expectedCommand = new ScheduleCommand(VALID_NAME, expectedSchedule);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
