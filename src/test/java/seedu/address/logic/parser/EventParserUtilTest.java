package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.EventParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;

public class EventParserUtilTest {

    /* Test input for Event */
    private static final String INVALID_EVENT_NAME = "Hack@thon";
    private static final String INVALID_DATE_FORMAT = "2020/10/10";
    private static final String INVALID_DATE = "2020-32-32";
    private static final String INVALID_TIME_24H_FORMAT = "0000";
    private static final String INVALID_TIME = "11:69";
    private static final String INVALID_LOCATION = "LT 27!";
    private static final String INVALID_DESCRIPTION = "#friendParty";

    private static final String VALID_EVENT_NAME = "Hackathon";
    private static final String VALID_DATE = "2020-10-10";
    private static final String VALID_START_TIME = "12:00";
    private static final String VALID_END_TIME = "18:00";
    private static final String VALID_LOCATION = "LT 27";
    private static final String VALID_DESCRIPTION = "Friends Party";

    private static final String WHITESPACE = " \t\r\n";


    /* Tests for Event */
    @Test
    public void parseEventIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> EventParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseEventIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> EventParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, EventParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, EventParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> EventParserUtil.parseName(INVALID_EVENT_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsEventName() throws Exception {
        EventName expectedName = new EventName(VALID_EVENT_NAME);
        assertEquals(expectedName, EventParserUtil.parseName(VALID_EVENT_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedEventName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_EVENT_NAME + WHITESPACE;
        EventName expectedName = new EventName(VALID_EVENT_NAME);
        assertEquals(expectedName, EventParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> EventParserUtil.parseDate(INVALID_DATE));
        assertThrows(ParseException.class, () -> EventParserUtil.parseDate(INVALID_DATE_FORMAT));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, EventParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, EventParserUtil.parseDate(phoneWithWhitespace));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> EventParserUtil.parseLocation(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithoutWhitespace_returnsLocation() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, EventParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithWhitespace_returnsTrimmedLocation() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, EventParserUtil.parseLocation(addressWithWhitespace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> EventParserUtil.parseTime(INVALID_TIME));
        assertThrows(ParseException.class, () -> EventParserUtil.parseTime(INVALID_TIME_24H_FORMAT));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedStartTime = new Time(VALID_START_TIME);
        assertEquals(expectedStartTime, EventParserUtil.parseTime(VALID_START_TIME));
        Time expectedEndTime = new Time(VALID_END_TIME);
        assertEquals(expectedEndTime, EventParserUtil.parseTime(VALID_END_TIME));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String startTimeWithWhitespace = WHITESPACE + VALID_START_TIME + WHITESPACE;
        Time expectedStartTime = new Time(VALID_START_TIME);
        assertEquals(expectedStartTime, EventParserUtil.parseTime(startTimeWithWhitespace));
        String endTimeWithWhitespace = WHITESPACE + VALID_END_TIME + WHITESPACE;
        Time expectedEndTime = new Time(VALID_END_TIME);
        assertEquals(expectedEndTime, EventParserUtil.parseTime(endTimeWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> EventParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description validDescription = new Description(VALID_DESCRIPTION);
        assertEquals(validDescription, EventParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description validDescription = new Description(VALID_DESCRIPTION);
        assertEquals(validDescription, EventParserUtil.parseDescription(descriptionWithWhitespace));
    }
}
