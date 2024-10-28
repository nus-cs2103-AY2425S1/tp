package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_POSTALCODE = "123 45";
    private static final String INVALID_UNIT = "11";
    private static final String INVALID_TYPE = "PUBLIC";
    private static final String INVALID_ASK = "11.00";
    private static final String INVALID_BID = "11.00";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_POSTALCODE = "123456";
    private static final String VALID_UNIT = "11-11";
    private static final String VALID_TYPE = "HDB";
    private static final String VALID_ASK = "60000";
    private static final String VALID_BID = "50000";
    private static final String VALID_MEETING_TITLE = "Project Meeting";
    private static final String INVALID_MEETING_TITLE = "Project@Meeting"; // Invalid due to special character
    private static final String VALID_MEETING_DATE = "01-01-2024";
    private static final String INVALID_MEETING_DATE = "32-13-2024"; // Invalid due to incorrect day and month


    private static final String WHITESPACE = " \t\r\n";

    // Test for parsing index
    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    // Tests for client Name
    @Test
    public void parseClientName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientName(null));
    }

    @Test
    public void parseClientName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientName(INVALID_NAME));
    }

    @Test
    public void parseClientName_validValueWithoutWhitespace_returnsClientName() throws Exception {
        seedu.address.model.client.Name expectedName = new seedu.address.model.client.Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseClientName(VALID_NAME));
    }

    @Test
    public void parseClientName_validValueWithWhitespace_returnsTrimmedClientName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        seedu.address.model.client.Name expectedName = new seedu.address.model.client.Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseClientName(nameWithWhitespace));
    }

    // Tests for client Phone
    @Test
    public void parseClientPhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientPhone(null));
    }

    @Test
    public void parseClientPhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientPhone(INVALID_PHONE));
    }

    @Test
    public void parseClientPhone_validValueWithoutWhitespace_returnsClientPhone() throws Exception {
        seedu.address.model.client.Phone expectedPhone = new seedu.address.model.client.Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parseClientPhone(VALID_PHONE));
    }

    @Test
    public void parseClientPhone_validValueWithWhitespace_returnsTrimmedClientPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        seedu.address.model.client.Phone expectedPhone = new seedu.address.model.client.Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parseClientPhone(phoneWithWhitespace));
    }

    // Tests for client Email
    @Test
    public void parseClientEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientEmail((String) null));
    }

    @Test
    public void parseClientEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientEmail(INVALID_EMAIL));
    }

    @Test
    public void parseClientEmail_validValueWithoutWhitespace_returnsClientEmail() throws Exception {
        seedu.address.model.client.Email expectedEmail = new seedu.address.model.client.Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseClientEmail(VALID_EMAIL));
    }

    @Test
    public void parseClientEmail_validValueWithWhitespace_returnsTrimmedClientEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        seedu.address.model.client.Email expectedEmail = new seedu.address.model.client.Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseClientEmail(emailWithWhitespace));
    }

    // Tests for property PostalCode
    @Test
    public void parsePropertyPostalCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePostalCode((String) null));
    }

    @Test
    public void parsePropertyPostalCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(INVALID_POSTALCODE));
    }

    @Test
    public void parsePropertyPostalCode_validValueWithoutWhitespace_returnsClientEmail() throws Exception {
        PostalCode expectedPostalCode = new PostalCode(VALID_POSTALCODE);
        assertEquals(expectedPostalCode, ParserUtil.parsePostalCode(VALID_POSTALCODE));
    }

    @Test
    public void parsePropertyPostalCode_validValueWithWhitespace_returnsTrimmedClientEmail() throws Exception {
        String postalCodeWithWhitespace = WHITESPACE + VALID_POSTALCODE + WHITESPACE;
        PostalCode expectedPostalCode = new PostalCode(VALID_POSTALCODE);
        assertEquals(expectedPostalCode, ParserUtil.parsePostalCode(postalCodeWithWhitespace));
    }

    // Tests for property Unit
    @Test
    public void parsePropertyUnit_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUnit((String) null));
    }

    @Test
    public void parsePropertyUnit_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseUnit(INVALID_UNIT));
    }

    @Test
    public void parsePropertyUnit_validValueWithoutWhitespace_returnsClientEmail() throws Exception {
        Unit expectedUnit = new Unit(VALID_UNIT);
        assertEquals(expectedUnit, ParserUtil.parseUnit(VALID_UNIT));
    }

    @Test
    public void parsePropertyUnit_validValueWithWhitespace_returnsTrimmedClientEmail() throws Exception {
        String unitWithWhitespace = WHITESPACE + VALID_UNIT + WHITESPACE;
        Unit expectedUnit = new Unit(VALID_UNIT);
        assertEquals(expectedUnit, ParserUtil.parseUnit(unitWithWhitespace));
    }

    // Tests for property Type
    @Test
    public void parsePropertyType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseType((String) null));
    }

    @Test
    public void parsePropertyType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseType(INVALID_TYPE));
    }

    @Test
    public void parsePropertyType_validValueWithoutWhitespace_returnsClientEmail() throws Exception {
        Type expectedType = new Type(VALID_TYPE);
        assertEquals(expectedType, ParserUtil.parseType(VALID_TYPE));
    }

    @Test
    public void parsePropertyType_validValueWithWhitespace_returnsTrimmedClientEmail() throws Exception {
        String typeWithWhitespace = WHITESPACE + VALID_TYPE + WHITESPACE;
        Type expectedType = new Type(VALID_TYPE);
        assertEquals(expectedType, ParserUtil.parseType(typeWithWhitespace));
    }

    // Tests for property Ask
    @Test
    public void parsePropertyAsk_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAsk((String) null));
    }

    @Test
    public void parsePropertyAsk_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAsk(INVALID_ASK));
    }

    @Test
    public void parsePropertyAsk_validValueWithoutWhitespace_returnsClientEmail() throws Exception {
        Ask expectedAsk = new Ask(VALID_ASK);
        assertEquals(expectedAsk, ParserUtil.parseAsk(VALID_ASK));
    }

    @Test
    public void parsePropertyAsk_validValueWithWhitespace_returnsTrimmedClientEmail() throws Exception {
        String askWithWhitespace = WHITESPACE + VALID_ASK + WHITESPACE;
        Ask expectedAsk = new Ask(VALID_ASK);
        assertEquals(expectedAsk, ParserUtil.parseAsk(askWithWhitespace));
    }

    // Tests for property Bid
    @Test
    public void parsePropertyBid_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBid((String) null));
    }

    @Test
    public void parsePropertyBid_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBid(INVALID_BID));
    }

    @Test
    public void parsePropertyBid_validValueWithoutWhitespace_returnsClientEmail() throws Exception {
        Bid expectedBid = new Bid(VALID_BID);
        assertEquals(expectedBid, ParserUtil.parseBid(VALID_BID));
    }

    @Test
    public void parsePropertyBid_validValueWithWhitespace_returnsTrimmedClientEmail() throws Exception {
        String bidWithWhitespace = WHITESPACE + VALID_BID + WHITESPACE;
        Bid expectedBid = new Bid(VALID_BID);
        assertEquals(expectedBid, ParserUtil.parseBid(bidWithWhitespace));
    }

    @Test
    public void parseMeetingTitle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeetingTitle(null));
    }

    @Test
    public void parseMeetingTitle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeetingTitle(INVALID_MEETING_TITLE));
    }

    @Test
    public void parseMeetingTitle_validValueWithoutWhitespace_returnsMeetingTitle() throws Exception {
        MeetingTitle expectedMeetingTitle = new MeetingTitle(VALID_MEETING_TITLE);
        assertEquals(expectedMeetingTitle, ParserUtil.parseMeetingTitle(VALID_MEETING_TITLE));
    }

    @Test
    public void parseMeetingTitle_validValueWithWhitespace_returnsTrimmedMeetingTitle() throws Exception {
        String meetingTitleWithWhitespace = WHITESPACE + VALID_MEETING_TITLE + WHITESPACE;
        MeetingTitle expectedMeetingTitle = new MeetingTitle(VALID_MEETING_TITLE);
        assertEquals(expectedMeetingTitle, ParserUtil.parseMeetingTitle(meetingTitleWithWhitespace));
    }

    @Test
    public void parseMeetingDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeetingDate(null));
    }

    @Test
    public void parseMeetingDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeetingDate(INVALID_MEETING_DATE));
    }

    @Test
    public void parseMeetingDate_validValueWithoutWhitespace_returnsMeetingDate() throws Exception {
        MeetingDate expectedMeetingDate = new MeetingDate(VALID_MEETING_DATE);
        assertEquals(expectedMeetingDate, ParserUtil.parseMeetingDate(VALID_MEETING_DATE));
    }

    @Test
    public void parseMeetingDate_validValueWithWhitespace_returnsTrimmedMeetingDate() throws Exception {
        String meetingDateWithWhitespace = WHITESPACE + VALID_MEETING_DATE + WHITESPACE;
        MeetingDate expectedMeetingDate = new MeetingDate(VALID_MEETING_DATE);
        assertEquals(expectedMeetingDate, ParserUtil.parseMeetingDate(meetingDateWithWhitespace));
    }
}
