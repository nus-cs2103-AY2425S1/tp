package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.SocialMedia;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SCHEDULE_NAME = "my-appointment";
    private static final String INVALID_SCHEDULE_DATE = "2024-12";
    private static final String INVALID_SCHEDULE_TIME = "12000";
    private static final String INVALID_SOCIAL_MEDIA = "my@ismy@";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SCHEDULE_NAME = "appointment";
    private static final String VALID_SCHEDULE_DATE = "2024-10-22";
    private static final String VALID_SCHEDULE_TIME = "10:00";
    private static final String VALID_SOCIAL_MEDIA = "shiningBoots";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

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
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseScheduleName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSchedule(
                INVALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleName_validValueWithoutWhitespace_returnsSchedule() throws Exception {
        Schedule expectedSchedule = new Schedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleName_validValueWithWhitespace_returnsTrimmedSchedule() throws Exception {
        String scheduleNameWithWhitespace = WHITESPACE + VALID_SCHEDULE_NAME + WHITESPACE;
        Schedule expectedSchedule = new Schedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(
                scheduleNameWithWhitespace, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, INVALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleDate_validValueWithoutWhitespace_returnsSchedule() throws Exception {
        Schedule expectedSchedule = new Schedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleDate_validValueWithWhitespace_returnsTrimmedSchedule() throws Exception {
        String scheduleDateWithWhitespace = WHITESPACE + VALID_SCHEDULE_DATE + WHITESPACE;
        Schedule expectedSchedule = new Schedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, scheduleDateWithWhitespace, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, INVALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleTime_validValueWithoutWhitespace_returnsSchedule() throws Exception {
        Schedule expectedSchedule = new Schedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME));
    }

    @Test
    public void parseScheduleTime_validValueWithWhitespace_returnsTrimmedSchedule() throws Exception {
        String scheduleTimeWithWhitespace = WHITESPACE + VALID_SCHEDULE_TIME + WHITESPACE;
        Schedule expectedSchedule = new Schedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME);
        assertEquals(expectedSchedule, ParserUtil.parseSchedule(
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, scheduleTimeWithWhitespace));
    }

    @Test
    public void parseSocialMedia_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSocialMedia(INVALID_SOCIAL_MEDIA));
    }

    @Test
    public void parseSocialMedia_validValueWithoutWhitespace_returnsSchedule() throws Exception {
        SocialMedia expectedSocialMedia = new SocialMedia(VALID_SOCIAL_MEDIA, SocialMedia.Platform.CAROUSELL);
        assertEquals(expectedSocialMedia, ParserUtil.parseSocialMedia(VALID_SOCIAL_MEDIA));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
