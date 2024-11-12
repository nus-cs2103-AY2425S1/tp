package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_SORT_ORDER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_ALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tutorial;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SORT_ORDER = "2";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_TUTORIAL_1 = "0";
    private static final String INVALID_TUTORIAL_2 = "a";
    private static final String INVALID_TUTORIAL_3 = "-";
    private static final String INVALID_TUTORIAL_FORMAT = "1-2-4";
    private static final String INVALID_TUTORIAL_NUMBER_IN_LIST = "[1,13]";
    private static final String INVALID_TUTORIAL_NUMBER_IN_RANGE_1 = "1-13";
    private static final String INVALID_TUTORIAL_NUMBER_IN_RANGE_2 = "1--2";
    private static final String INVALID_TUTORIAL_RANGE = "5-3";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SORT_ORDER_1 = "1";
    private static final String VALID_SORT_ORDER_MINUS_1 = "-1";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_TUTORIAL_1 = "1";
    private static final String VALID_TUTORIAL_2 = "2";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("-1"));
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
    public void parseIndexAllowWildcard_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexAllowWildcard("10 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexAllowWildcard("-1"));
    }

    @Test
    public void parseIndexAllowWildcard_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_ALL, ParserUtil.parseIndexAllowWildcard("*"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_ALL, ParserUtil.parseIndexAllowWildcard("  *  "));
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
    public void parseSortOrder_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortOrder(null));
    }

    @Test
    public void parseSortOrder_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_SORT_ORDER, () ->
                ParserUtil.parseSortOrder(INVALID_SORT_ORDER));
    }

    @Test
    public void parseSortOrder_validValueWithoutWhitespace_returnsInteger() throws Exception {
        assertEquals(1, ParserUtil.parseSortOrder(VALID_SORT_ORDER_1));
        assertEquals(-1, ParserUtil.parseSortOrder(VALID_SORT_ORDER_MINUS_1));
    }

    @Test
    public void parseSortOrder_validValueWithWhitespace_returnsTrimmedInteger() throws Exception {
        String sortOrderWithWhitespace = WHITESPACE + VALID_SORT_ORDER_1 + WHITESPACE;
        assertEquals(1, ParserUtil.parseSortOrder(sortOrderWithWhitespace));
    }

    //Singular Tutorial parsing
    @Test
    public void parseTutorial_validTutorial_returnsTutorial() throws Exception {
        Tutorial expectedTutorial = new Tutorial(VALID_TUTORIAL_1);
        assertEquals(expectedTutorial, ParserUtil.parseTutorial(VALID_TUTORIAL_1));
    }

    @Test
    public void parseTutorial_invalidTutorial_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorial(INVALID_TUTORIAL_2));
    }

    //Multiple Tutorial parsing
    // EP: Not a number
    @Test
    public void parseTutorials_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTutorials(null));
    }

    // EP: Non-positive numbers
    @Test
    public void parseTutorials_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_1));
    }

    // EP: Non-numbers input
    @Test
    public void parseTutorials_dash_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_3));
    }

    // EP: Positive numbers
    @Test
    public void parseTutorials_validValueWithoutWhitespace_returnsTutorial() throws Exception {
        Tutorial expectedTutorial = new Tutorial(VALID_TUTORIAL_2);
        assertEquals(List.of(expectedTutorial), ParserUtil.parseTutorials(VALID_TUTORIAL_2));
    }

    // EP: Positive numbers with whitespace
    @Test
    public void parseTutorials_validValueWithWhitespace_returnsTrimmedTutorial() throws Exception {
        String tutWithWhitespace = WHITESPACE + VALID_TUTORIAL_1 + WHITESPACE;
        Tutorial expectedTutorial = new Tutorial(VALID_TUTORIAL_1);
        assertEquals(List.of(expectedTutorial), ParserUtil.parseTutorials(tutWithWhitespace));
    }

    // 1. Test for invalid range (start > end)
    @Test
    public void parseTutorials_invalidRangeStartGreaterThanEnd_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_RANGE));
    }

    // 2. Test for invalid format (e.g., mark 1 tut/1-2-4)
    @Test
    public void parseTutorials_invalidFormatMultipleDashes_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_FORMAT));
    }

    // 3. Test for invalid tutorial number in list (e.g., [1, 13])
    @Test
    public void parseTutorials_invalidTutorialNumberInList_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_NUMBER_IN_LIST));
    }

    // 4. Test for invalid tutorial number in range (e.g., 1-13)
    @Test
    public void parseTutorials_invalidTutorialNumberInRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_NUMBER_IN_RANGE_1));
    }

    @Test
    public void parseTutorials_negativeTutorialNumberInRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorials(INVALID_TUTORIAL_NUMBER_IN_RANGE_2));
    }

    // EP: No keywords
    @Test
    public void parseNameKeywords_empty_success() throws Exception {
        List<String> keywords = new ArrayList<>();

        NameContainsKeywordsPredicate expected = new NameContainsKeywordsPredicate(keywords);
        assertEquals(expected, ParserUtil.parseNameKeywords(keywords));
    }

    // EP: Numerical keywords
    @Test
    public void parseNameKeywords_numbersOnlyKeywords_success() throws Exception {
        List<String> keywords = new ArrayList<>();
        keywords.add("123");

        NameContainsKeywordsPredicate expected = new NameContainsKeywordsPredicate(keywords);
        assertEquals(expected, ParserUtil.parseNameKeywords(keywords));
    }

    // EP: Alphabetical keywords
    @Test
    public void parseNameKeywords_alphabetsOnlyKeywords_success() throws Exception {
        List<String> keywords = new ArrayList<>();
        keywords.add("a");

        NameContainsKeywordsPredicate expected = new NameContainsKeywordsPredicate(keywords);
        assertEquals(expected, ParserUtil.parseNameKeywords(keywords));
    }

    // EP: Alphanumeric keywords
    @Test
    public void parseNameKeywords_alphanumericKeywords_success() throws Exception {
        List<String> keywords = new ArrayList<>();
        keywords.add("a12");

        NameContainsKeywordsPredicate expected = new NameContainsKeywordsPredicate(keywords);
        assertEquals(expected, ParserUtil.parseNameKeywords(keywords));
    }

    // EP: Non-alphanumeric keywords
    @Test
    public void parseNameKeywords_specialCharacters_throwsParseException() {
        List<String> keywords = new ArrayList<>();
        keywords.add("/");

        assertThrows(ParseException.class, () -> ParserUtil.parseNameKeywords(keywords));
    }

    // EP: Testing valid with invalid inputs
    @Test
    public void parseNameKeywords_specialCharactersAndValidKeywords_throwsParseException() {
        List<String> keywords = new ArrayList<>();
        keywords.add("/");
        keywords.add("name");

        assertThrows(ParseException.class, () -> ParserUtil.parseNameKeywords(keywords));
    }
}
