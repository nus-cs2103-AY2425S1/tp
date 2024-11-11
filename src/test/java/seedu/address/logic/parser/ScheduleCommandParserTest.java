package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_UNEQUAL_NOTES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Schedule;


public class ScheduleCommandParserTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_DATE = "2024-10-04 1400";

    private static final String VALID_DATE_TWO = "2024-10-05 1400";
    private static final String INVALID_DATE = "invalid_date";
    private static final String VALID_NOTE = "first appointment";
    private static final String VALID_NOTE_TWO = "second appointment";
    private static final String NON_EXISTENT_DATE = "2024-02-30 1200";

    private ScheduleCommandParser parser = new ScheduleCommandParser();
    @Test
    public void parse_invalidDate_failure() {
        // invalid date format
        assertParseFailure(parser, VALID_NAME + " d/" + INVALID_DATE + " note/" + VALID_NOTE,
                MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_validInput_success() {
        // valid input with name and date
        String userInput = VALID_NAME + " d/" + VALID_DATE + " note/" + VALID_NOTE;
        LocalDateTime expectedDate = LocalDateTime.parse(VALID_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        Schedule expectedSchedule = new Schedule(VALID_DATE, "");
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(expectedSchedule);
        ScheduleCommand expectedCommand = new ScheduleCommand(new Name(VALID_NAME), schedules);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        // valid input with extra whitespaces
        String userInput = "    " + VALID_NAME + "     d/" + VALID_DATE + "   note/" + VALID_NOTE;
        LocalDateTime expectedDate = LocalDateTime.parse(VALID_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        Schedule expectedSchedule = new Schedule(VALID_DATE, "");
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(expectedSchedule);
        ScheduleCommand expectedCommand = new ScheduleCommand(new Name(VALID_NAME), schedules);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noNotesPresent_failure() {
        String userInput = VALID_NAME + " d/" + VALID_DATE;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_moreDatesThanNotes_failure() {
        String userInput = VALID_NAME + " d/" + VALID_DATE + " note/" + VALID_NOTE + " d/" + VALID_DATE_TWO;
        assertParseFailure(parser, userInput, MESSAGE_UNEQUAL_NOTES);
    }

    @Test
    public void parse_moreNotesThanDates_failure() {
        String userInput = VALID_NAME + " d/" + VALID_DATE + " note/" + VALID_NOTE + " note/" + VALID_DATE_TWO;
        assertParseFailure(parser, userInput, MESSAGE_UNEQUAL_NOTES);
    }

    @Test
    public void parse_dateDoNotExist_failure() {
        String userInput = VALID_NAME + " d/" + NON_EXISTENT_DATE + " note/" + VALID_NOTE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_DATE);
    }
}
