package hallpointer.address.logic.parser;

import static hallpointer.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_INDEX = "-1";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TELEGRAM = "+hisrocks";
    private static final String INVALID_ROOM = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SESSION_NAME = "Session #1";
    private static final String INVALID_DATE = "40 Feb 2090";
    private static final String INVALID_POINTS = "-1";

    private static final String VALID_INDEX_1 = "1";
    private static final String VALID_INDEX_2 = "2";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TELEGRAM = "elephant";
    private static final String VALID_ROOM = "8-7-23";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_SESSION_NAME = "Session 1";
    private static final String VALID_DATE = "20 Mar 2023";
    private static final String VALID_POINTS = "3";

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
        assertEquals(INDEX_FIRST_MEMBER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_MEMBER, ParserUtil.parseIndex("  1  "));
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
    public void parseTelegram_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTelegram((String) null));
    }

    @Test
    public void parseTelegram_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTelegram(INVALID_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithoutWhitespace_returnsTelegram() throws Exception {
        Telegram expectedTelegram = new Telegram(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(VALID_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithWhitespace_returnsTrimmedTelegram() throws Exception {
        String telegramWithWhitespace = WHITESPACE + VALID_TELEGRAM + WHITESPACE;
        Telegram expectedTelegram = new Telegram(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(telegramWithWhitespace));
    }

    @Test
    public void parseRoom_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRoom((String) null));
    }

    @Test
    public void parseRoom_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRoom(INVALID_ROOM));
    }

    @Test
    public void parseRoom_validValueWithoutWhitespace_returnsRoom() throws Exception {
        Room expectedRoom = new Room(VALID_ROOM);
        assertEquals(expectedRoom, ParserUtil.parseRoom(VALID_ROOM));
    }

    @Test
    public void parseRoom_validValueWithWhitespace_returnsTrimmedRoom() throws Exception {
        String roomWithWhitespace = WHITESPACE + VALID_ROOM + WHITESPACE;
        Room expectedRoom = new Room(VALID_ROOM);
        assertEquals(expectedRoom, ParserUtil.parseRoom(roomWithWhitespace));
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

    @Test
    public void parseTags_collectionWithValidDuplicateTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_1));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseSessionName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSessionName((String) null));
    }

    @Test
    public void parseSessionName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSessionName(INVALID_SESSION_NAME));
    }

    @Test
    public void parseSessionName_validValueWithoutWhitespace_returnsName() throws Exception {
        SessionName expectedSessionName = new SessionName(VALID_SESSION_NAME);
        assertEquals(expectedSessionName, ParserUtil.parseSessionName(VALID_SESSION_NAME));
    }

    @Test
    public void parseSessionName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String sessionNameWithWhitespace = WHITESPACE + VALID_SESSION_NAME + WHITESPACE;
        SessionName expectedSessionName = new SessionName(VALID_SESSION_NAME);
        assertEquals(expectedSessionName, ParserUtil.parseSessionName(sessionNameWithWhitespace));
    }

    @Test
    public void parseSessionDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSessionDate((String) null));
    }

    @Test
    public void parseSessionDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSessionDate(INVALID_DATE));
    }

    @Test
    public void parseSessionDate_validValueWithoutWhitespace_returnsName() throws Exception {
        SessionDate expectedSessionDate = new SessionDate(VALID_DATE);
        assertEquals(expectedSessionDate, ParserUtil.parseSessionDate(VALID_DATE));
    }

    @Test
    public void parseSessionDate_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String sessionDateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        SessionDate expectedSessionDate = new SessionDate(VALID_DATE);
        assertEquals(expectedSessionDate, ParserUtil.parseSessionDate(sessionDateWithWhitespace));
    }

    @Test
    public void parsePoints_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePoints(null));
    }

    @Test
    public void parsePoints_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePoints(INVALID_POINTS));
    }

    @Test
    public void parsePoints_validValue_returnsPoints() throws Exception {
        Point expectedPoints = new Point(VALID_POINTS);
        assertEquals(expectedPoints, ParserUtil.parsePoints(VALID_POINTS));
    }

    @Test
    public void parseIndices_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIndices(null));
    }

    @Test
    public void parseIndices_collectionWithInvalidIndices_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_INDEX_1, INVALID_INDEX)));
    }

    @Test
    public void parseIndices_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseIndices(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseIndices_collectionWithValidIndices_returnsIndexSet() throws Exception {
        Set<Index> actualIndexSet = ParserUtil.parseIndices(Arrays.asList(VALID_INDEX_1, VALID_INDEX_2));
        Set<Index> expectedIndexSet = new HashSet<Index>(Arrays.asList(INDEX_FIRST_MEMBER, INDEX_SECOND_MEMBER));

        assertEquals(expectedIndexSet, actualIndexSet);
    }

    @Test
    public void parseIndices_collectionWithValidDuplicateIndices_returnsIndex() throws Exception {
        Set<Index> actualIndexSet = ParserUtil.parseIndices(Arrays.asList(VALID_INDEX_1, VALID_INDEX_2, VALID_INDEX_1));
        Set<Index> expectedIndexSet = new HashSet<Index>(Arrays.asList(INDEX_FIRST_MEMBER, INDEX_SECOND_MEMBER));

        assertEquals(expectedIndexSet, actualIndexSet);
    }
}
