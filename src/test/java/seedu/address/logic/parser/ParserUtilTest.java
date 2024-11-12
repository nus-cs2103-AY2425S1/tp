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
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.tag.StudyGroupTag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_GENDER = "A";
    private static final String INVALID_AGE = "A";
    private static final String INVALID_STUDY_GROUP_TAG = "#1A";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_GENDER = "F";
    private static final String VALID_AGE = "30";
    private static final String VALID_DETAIL = "detail";
    private static final String VALID_STUDY_GROUP_TAG_1 = "1A";
    private static final String VALID_STUDY_GROUP_TAG_2 = "2B";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
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
    public void parseGender_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGender((String) null));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(VALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(genderWithWhitespace));
    }

    @Test
    public void parseAge_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAge((String) null));
    }

    @Test
    public void parseAge_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAge(INVALID_AGE));
    }

    @Test
    public void parseAge_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Age expectedAddress = new Age(VALID_AGE);
        assertEquals(expectedAddress, ParserUtil.parseAge(VALID_AGE));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_AGE + WHITESPACE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(addressWithWhitespace));
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
    public void parseDetail_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Detail expectedDetail = new Detail(VALID_DETAIL);
        assertEquals(expectedDetail, ParserUtil.parseDetail(VALID_DETAIL));
    }

    @Test
    public void parseStudyGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudyGroup(null));
    }

    @Test
    public void parseStudyGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudyGroup(INVALID_STUDY_GROUP_TAG));
    }

    @Test
    public void parseStudyGroup_validValueWithoutWhitespace_returnsTag() throws Exception {
        StudyGroupTag expectedTag = new StudyGroupTag(VALID_STUDY_GROUP_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseStudyGroup(VALID_STUDY_GROUP_TAG_1));
    }

    @Test
    public void parseStudyGroup_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_STUDY_GROUP_TAG_1 + WHITESPACE;
        StudyGroupTag expectedTag = new StudyGroupTag(VALID_STUDY_GROUP_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseStudyGroup(tagWithWhitespace));
    }

    @Test
    public void parseStudyGroups_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudyGroups(null));
    }

    @Test
    public void parseStudyGroups_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudyGroups(Arrays.asList(VALID_STUDY_GROUP_TAG_1,
                INVALID_STUDY_GROUP_TAG)));
    }

    @Test
    public void parseStudyGroups_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseStudyGroups(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseStudyGroups_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<StudyGroupTag> actualTagSet = ParserUtil.parseStudyGroups(Arrays.asList(VALID_STUDY_GROUP_TAG_1,
                VALID_STUDY_GROUP_TAG_2));
        Set<StudyGroupTag> expectedTagSet = new HashSet<StudyGroupTag>(
                Arrays.asList(new StudyGroupTag(VALID_STUDY_GROUP_TAG_1), new StudyGroupTag(VALID_STUDY_GROUP_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
