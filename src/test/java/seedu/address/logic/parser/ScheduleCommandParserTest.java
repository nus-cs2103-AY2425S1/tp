package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;


public class ScheduleCommandParserTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_DATE = "2024-10-04 1400";
    private static final String INVALID_DATE = "invalid_date";
    private static final String NON_EXISTENT_DATE = "2024-02-30 1200";

    private ScheduleCommandParser parser = new ScheduleCommandParser();
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
        Schedule expectedSchedule = new Schedule(VALID_DATE, "");
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(expectedSchedule);
        ScheduleCommand expectedCommand = new ScheduleCommand(VALID_NAME, schedules);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        // valid input with extra whitespaces
        String userInput = "    " + VALID_NAME + "     d/" + VALID_DATE + "   ";
        LocalDateTime expectedDate = LocalDateTime.parse(VALID_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        Schedule expectedSchedule = new Schedule(VALID_DATE, "");
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(expectedSchedule);
        ScheduleCommand expectedCommand = new ScheduleCommand(VALID_NAME, schedules);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_dateDoNotExist_failure() {
        String userInput = VALID_NAME + " d/" + NON_EXISTENT_DATE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_DATE);
    }
}
