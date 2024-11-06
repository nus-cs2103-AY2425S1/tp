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

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "6+51234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_FINANCIAL_INFO = "Salary: $5000";
    private static final String VALID_SOCIAL_MEDIA_HANDLE = "@johndoe";

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
    public void parseTag_emptyKeyAndValue_throwsParseException() {
        String tagWithoutKeyAndValue = ":";
        assertThrows(ParseException.class,
                Tag.MESSAGE_TAG_NAMES_CANNOT_BE_EMPTY, () -> ParserUtil.parseTag(tagWithoutKeyAndValue));
    }

    @Test
    public void parseTag_emptyKey_throwsParseException() {
        String tagWithEmptyKey = ":value";
        assertThrows(ParseException.class,
                Tag.MESSAGE_TAG_NAME_OR_VALUE_MISSING, () -> ParserUtil.parseTag(tagWithEmptyKey));
    }

    @Test
    public void parseTag_invalidKey_throwsParseException() {
        String tagWithInvalidKey = "$key:value";
        assertThrows(ParseException.class,
                Tag.MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC, () -> ParserUtil.parseTag(tagWithInvalidKey));
    }

    @Test
    public void parseTag_emptyValue_throwsParseException() {
        String tagWithEmptyValue = "key:";
        assertThrows(ParseException.class,
                Tag.MESSAGE_TAG_NAME_OR_VALUE_MISSING, () -> ParserUtil.parseTag(tagWithEmptyValue));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        String tagWithInvalidValue = "key:$value";
        assertThrows(ParseException.class,
                Tag.MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC, () -> ParserUtil.parseTag(tagWithInvalidValue));
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
    public void parseFinancialInfo_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFinancialInfo(null));
    }

    @Test
    public void parseFinancialInfo_validValueWithoutWhitespace_returnsFinancialInfo() throws Exception {
        String expectedFinancialInfo = VALID_FINANCIAL_INFO;
        assertEquals(expectedFinancialInfo, ParserUtil.parseFinancialInfo(VALID_FINANCIAL_INFO));
    }

    @Test
    public void parseFinancialInfo_validValueWithWhitespace_returnsTrimmedFinancialInfo() throws Exception {
        String financialInfoWithWhitespace = WHITESPACE + VALID_FINANCIAL_INFO + WHITESPACE;
        String expectedFinancialInfo = VALID_FINANCIAL_INFO;
        assertEquals(expectedFinancialInfo, ParserUtil.parseFinancialInfo(financialInfoWithWhitespace));
    }

    @Test
    public void parseSocialMediaHandle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSocialMediaHandle(null));
    }

    @Test
    public void parseSocialMediaHandle_validValueWithoutWhitespace_returnsSocialMediaHandle() throws Exception {
        String expectedSocialMediaHandle = VALID_SOCIAL_MEDIA_HANDLE;
        assertEquals(expectedSocialMediaHandle, ParserUtil.parseSocialMediaHandle(VALID_SOCIAL_MEDIA_HANDLE));
    }

    @Test
    public void parseSocialMediaHandle_validValueWithWhitespace_returnsTrimmedSocialMediaHandle() throws Exception {
        String socialMediaHandleWithWhitespace = WHITESPACE + VALID_SOCIAL_MEDIA_HANDLE + WHITESPACE;
        String expectedSocialMediaHandle = VALID_SOCIAL_MEDIA_HANDLE;
        assertEquals(expectedSocialMediaHandle, ParserUtil.parseSocialMediaHandle(socialMediaHandleWithWhitespace));
    }

    @Test
    public void parseTags_duplicateTagKeys_throwsParseException() {
        String duplicateTag1 = "priority:high";
        String duplicateTag2 = "priority:low";
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(duplicateTag1, duplicateTag2)));
    }

    @Test
    public void parseTags_uniqueTagKeys_returnsTagSet() throws Exception {
        String tag1 = "priority:high";
        String tag2 = "status:open";
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(tag1, tag2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag("priority", "high"),
                new Tag("status", "open")));
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTags_noDuplicateKeys_returnsTagSet() throws Exception {
        String tag1 = "friend";
        String tag2 = "colleague";
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(tag1, tag2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag("friend"), new Tag("colleague")));
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseIndex_zero_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex("0"));
    }

    @Test
    public void parseIndex_negativeNumber_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex("-1"));
    }

    @Test
    public void parseIndex_maxIntegerValue() throws Exception {
        Index maxIndex = Index.fromOneBased(Integer.MAX_VALUE);
        assertEquals(maxIndex, ParserUtil.parseIndex(Integer.toString(Integer.MAX_VALUE)));

    @Test
    public void parseTag_decimalValue() throws Exception {
        String tagWithDecimalValue = "grade:8.5";
        Tag expectedTag = new Tag("grade", "8.5");
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithDecimalValue));

    }
}
