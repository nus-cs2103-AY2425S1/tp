package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "65+1234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
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
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
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
    public void parseCriteria_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCriteria(null));
    }

    @Test
    public void parseCriteria_emptyString_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseCriteria("").isEmpty());
    }

    @Test
    public void parseCriteria_multipleCriteria_returnsList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parseCriteria("criteria1 criteria2 criteria3");
        List<String> expectedCriteriaList = Arrays.asList("criteria1", "criteria2", "criteria3");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parseCriteria_criteriaWithSpaces_returnsTrimmedList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parseCriteria("  criteria1  criteria2  ");
        List<String> expectedCriteriaList = Arrays.asList("criteria1", "criteria2");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parseAgeCriteria_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAgeCriteria(null));
    }

    @Test
    public void parseAgeCriteria_emptyString_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseAgeCriteria("").isEmpty());
    }

    @Test
    public void parseAgeCriteria_validCriteria_returnsList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parseAgeCriteria("30 >20 <40");
        List<String> expectedCriteriaList = Arrays.asList("30", ">20", "<40");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parseAgeCriteria_criteriaWithSpaces_returnsTrimmedList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parseAgeCriteria("  30  >20  <40  ");
        List<String> expectedCriteriaList = Arrays.asList("30", ">20", "<40");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parseAgeCriteria_invalidCharacter_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAgeCriteria("30 >20 <40 abc"));
    }

    @Test
    public void parseAgeCriteria_multipleInstancesOfSymbols_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAgeCriteria("30 >>20 <<40"));
    }

    @Test
    public void parseAgeCriteria_symbolsNotAtStart_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAgeCriteria("30 2>0 4<0"));
    }

    @Test
    public void parsePhoneCriteria_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhoneCriteria(null));
    }

    @Test
    public void parsePhoneCriteria_emptyString_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parsePhoneCriteria("").isEmpty());
    }

    @Test
    public void parsePhoneCriteria_validCriteria_returnsList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parsePhoneCriteria("12345 +67890 123-456");
        List<String> expectedCriteriaList = Arrays.asList("12345", "+67890", "123-456");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parsePhoneCriteria_criteriaWithSpaces_returnsTrimmedList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parsePhoneCriteria("  12345  +67890  ");
        List<String> expectedCriteriaList = Arrays.asList("12345", "+67890");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parsePhoneCriteria_invalidCharacter_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhoneCriteria("12345 +67890 abc"));
    }

    @Test
    public void parseIncomeCriteria_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIncomeCriteria(null));
    }

    @Test
    public void parseIncomeCriteria_emptyString_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseIncomeCriteria("").isEmpty());
    }

    @Test
    public void parseIncomeCriteria_validCriteria_returnsList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parseIncomeCriteria("low mid high");
        List<String> expectedCriteriaList = Arrays.asList("low", "mid", "high");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parseIncomeCriteria_criteriaWithSpaces_returnsTrimmedList() throws Exception {
        List<String> actualCriteriaList = ParserUtil.parseIncomeCriteria("  low  mid  ");
        List<String> expectedCriteriaList = Arrays.asList("low", "mid");

        assertEquals(expectedCriteriaList, actualCriteriaList);
    }

    @Test
    public void parseIncomeCriteria_invalidCharacter_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIncomeCriteria("low mid abc"));
    }
}
