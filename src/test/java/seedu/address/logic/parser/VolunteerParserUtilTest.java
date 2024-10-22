package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.EventParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;

public class VolunteerParserUtilTest {

    /* Test input for Volunteer */
    private static final String INVALID_VOLUNTEER_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_VOLUNTEER_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";

    /* Test input for Event */
    private static final String INVALID_EVENT_NAME = "Hack@thon";
    private static final String INVALID_DATE = "2020/10/10";
    private static final String INVALID_START_TIME = "1111";
    private static final String INVALID_END_TIME = "0000";
    private static final String INVALID_LOCATION = "LT 27!";
    private static final String INVALID_DESCRIPTION = "#friendParty";

    private static final String VALID_EVENT_NAME = "Hackathon";
    private static final String VALID_DATE = "2020-10-10";
    private static final String VALID_START_TIME = "12:00";
    private static final String VALID_END_TIME = "18:00";
    private static final String VALID_LOCATION = "LT 27";
    private static final String VALID_DESCRIPTION = "Friends Party";

    private static final String WHITESPACE = " \t\r\n";

    /* Tests for Volunteer */
    @Test
    public void parseVolunteerIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> VolunteerParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseVolunteerIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> VolunteerParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseVolunteerIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, VolunteerParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, VolunteerParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseVolunteerName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VolunteerParserUtil.parseName((String) null));
    }

    @Test
    public void parseVolunteerName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> VolunteerParserUtil.parseName(INVALID_VOLUNTEER_NAME));
    }

    @Test
    public void parseVolunteerName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_VOLUNTEER_NAME);
        assertEquals(expectedName, VolunteerParserUtil.parseName(VALID_VOLUNTEER_NAME));
    }

    @Test
    public void parseVolunteerName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_VOLUNTEER_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_VOLUNTEER_NAME);
        assertEquals(expectedName, VolunteerParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VolunteerParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> VolunteerParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, VolunteerParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, VolunteerParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VolunteerParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> VolunteerParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, VolunteerParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, VolunteerParserUtil.parseEmail(emailWithWhitespace));
    }


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
        assertThrows(ParseException.class, () -> EventParserUtil.parseTime(INVALID_START_TIME));
        assertThrows(ParseException.class, () -> EventParserUtil.parseTime(INVALID_END_TIME));
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
}
