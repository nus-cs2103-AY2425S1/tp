package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.EventParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;

public class VolunteerParserUtilTest {

    /* Test input for Volunteer */
    private static final String INVALID_VOLUNTEER_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATE_FORMAT = "2020/10/10";
    private static final String INVALID_DATE = "2020-32-32";
    private static final String INVALID_TIME_24H_FORMAT = "0000";
    private static final String INVALID_TIME = "11:69";

    private static final String VALID_VOLUNTEER_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_DATE = "2020-10-10";
    private static final String VALID_TIME = "12:00";

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

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VolunteerParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> VolunteerParserUtil.parseDate(INVALID_DATE));
        assertThrows(ParseException.class, () -> VolunteerParserUtil.parseDate(INVALID_DATE_FORMAT));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, VolunteerParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, VolunteerParserUtil.parseDate(dateWithWhitespace));
    }
}
