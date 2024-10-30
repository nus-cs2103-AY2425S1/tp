package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "invalid";
    private static final String INVALID_AREA = "invalid";
    private static final String INVALID_REGION = "eastwest";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String OTHER_VALID_NAME = "Jake Blue";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_PRICE = "12345";
    private static final String VALID_AREA = "100";
    private static final String VALID_REGION = "northeast";
    private static final String CASE_INSENSITIVE_REGION = "nOrTHEAst";

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
    public void parseNames_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNames(null));
    }

    @Test
    public void parseNames_invalidValue_throwsParseException() {
        Collection<String> names = Arrays.asList(INVALID_NAME);
        assertThrows(ParseException.class, () -> ParserUtil.parseNames(names));
    }

    @Test
    public void parseNames_emptyCollection_returnsEmptySet() throws ParseException {
        Collection<String> names = Collections.emptyList();
        Set<Name> result = ParserUtil.parseNames(names);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseNames_duplicateValues_returnsDistinctNames() throws ParseException {
        Collection<String> names = Arrays.asList(VALID_NAME, VALID_NAME);
        Set<Name> result = ParserUtil.parseNames(names);
        assertEquals(1, result.size());
    }

    @Test
    public void parseNames_validValues_returnsNames() throws ParseException {
        Collection<String> names = Arrays.asList(VALID_NAME, OTHER_VALID_NAME);
        Set<Name> result = ParserUtil.parseNames(names);
        assertEquals(names.size(), result.size());

        assertTrue(result.contains(new Name(VALID_NAME)));
        assertTrue(result.contains(new Name(OTHER_VALID_NAME)));
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
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice(null));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price result = ParserUtil.parsePrice(VALID_PRICE);
        Price expected = new Price(VALID_PRICE, new BigDecimal(VALID_PRICE));
        assertEquals(expected, result);
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        Price result = ParserUtil.parsePrice(" " + VALID_PRICE + " ");
        Price expected = new Price(VALID_PRICE, new BigDecimal(VALID_PRICE));
        assertEquals(expected, result);
    }

    @Test
    void parsePrice_invalidPrice_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parseArea_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseArea(null));
    }

    @Test
    void parseArea_validValueWithoutWhitespace_returnsArea() throws Exception {
        Area result = ParserUtil.parseArea(VALID_AREA);
        Area expected = new Area(Integer.parseInt(VALID_AREA));
        assertEquals(expected, result);
    }

    @Test
    void parseArea_validValueWithWhitespace_returnsTrimmedArea() throws Exception {
        Area result = ParserUtil.parseArea(" " + VALID_AREA + " ");
        Area expected = new Area(Integer.parseInt(VALID_AREA));
        assertEquals(result, expected);
    }

    @Test
    void parseArea_invalidArea_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseArea(INVALID_AREA));
    }

    @Test
    public void parseReqion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRegion(null));
    }

    @Test
    void parseRegion_validValueWithoutWhitespace_returnsRegion() throws ParseException {
        Region result = ParserUtil.parseRegion(VALID_REGION);
        Region expected = Region.fromString(VALID_REGION);
        assertEquals(expected, result);
    }

    @Test
    void parseRegion_caseInsensitive_returnsRegion() throws ParseException {
        Region result = ParserUtil.parseRegion(CASE_INSENSITIVE_REGION);
        Region expected = Region.fromString(CASE_INSENSITIVE_REGION);
        assertEquals(expected, result);
    }

    @Test
    void parseRegion_validValueWithWhitespace_returnsTrimmedRegion() throws ParseException {
        Region result = ParserUtil.parseRegion(" " + VALID_REGION + " ");
        Region expected = Region.fromString(VALID_REGION);
        assertEquals(expected, result);
    }

    @Test
    void parseRegion_invalidRegion_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRegion(INVALID_REGION));
    }
}
