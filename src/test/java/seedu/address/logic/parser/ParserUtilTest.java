package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SECOND_LIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505, S120300";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DELIVERY_ATTRIBUTE_ADDRESS = "address";
    private static final String VALID_DELIVERY_ATTRIBUTE_COST = "cost";
    private static final String VALID_DELIVERY_ATTRIBUTE_DATE = "date";
    private static final String VALID_DELIVERY_ATTRIBUTE_ETA = "eta";
    private static final String VALID_DELIVERY_ATTRIBUTE_ID = "id";
    private static final String VALID_DELIVERY_ATTRIBUTE_STATUS = "status";
    private static final String VALID_CONTACT_ATTRIBUTE_EMAIL = "email";
    private static final String VALID_CONTACT_ATTRIBUTE_NAME = "name";
    private static final String VALID_CONTACT_ATTRIBUTE_PHONE = "phone";
    private static final String VALID_CONTACT_ATTRIBUTE_ROLE = "role";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("1 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_LIST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_LIST, ParserUtil.parseIndex("  1  "));

        // 2 Input with only one whitespace
        List<Index> indexList = new ArrayList<>();
        indexList = ParserUtil.parseIndex("1 2");
        assertTrue(INDEX_FIRST_SECOND_LIST.containsAll(indexList)
                && indexList.containsAll(INDEX_FIRST_SECOND_LIST));

        //2 Input with leading and trailing whitespace
        indexList = ParserUtil.parseIndex("   1    2     ");
        assertTrue(INDEX_FIRST_SECOND_LIST.containsAll(indexList)
                && indexList.containsAll(INDEX_FIRST_SECOND_LIST));
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
    public void parseDeliveryAttribute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeliveryAttribute(null));
    }

    @Test
    public void parseDeliveryAttribute_unknownAttributeWithoutWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryAttribute("gamer"));
    }

    @Test
    public void parseDeliveryAttribute_unknownAttributeWithWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryAttribute(WHITESPACE + "gamer" + WHITESPACE));
    }

    @Test
    public void parseDeliveryAttribute_knownAttributeWithGibberishBehind_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_ADDRESS
                + WHITESPACE + "gamer" + WHITESPACE));
    }

    @Test
    public void parseDeliveryAttribute_knownAttributeWithGibberishBehindAndWhiteSpaceInFront_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryAttribute(WHITESPACE
                + VALID_DELIVERY_ATTRIBUTE_ADDRESS + WHITESPACE + "gamer" + WHITESPACE));
    }

    @Test
    public void parseDeliveryAttribute_addressAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "address";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_ADDRESS));
    }

    @Test
    public void parseDeliveryAttribute_addressAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_ADDRESS + WHITESPACE;
        String expectedAttribute = "address";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseDeliveryAttribute_costAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "cost";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_COST));
    }

    @Test
    public void parseDeliveryAttribute_costAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_COST + WHITESPACE;
        String expectedAttribute = "cost";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseDeliveryAttribute_dateAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "date";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_DATE));
    }

    @Test
    public void parseDeliveryAttribute_dateAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_DATE + WHITESPACE;
        String expectedAttribute = "date";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseDeliveryAttribute_etaAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "eta";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_ETA));
    }

    @Test
    public void parseDeliveryAttribute_etaAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_ETA + WHITESPACE;
        String expectedAttribute = "eta";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseDeliveryAttribute_idAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "id";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_ID));
    }

    @Test
    public void parseDeliveryAttribute_idAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_ID + WHITESPACE;
        String expectedAttribute = "id";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(attributeWithWhitespace));
    }


    @Test
    public void parseDeliveryAttribute_statusAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "status";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(VALID_DELIVERY_ATTRIBUTE_STATUS));
    }

    @Test
    public void parseDeliveryAttribute_statusAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_STATUS + WHITESPACE;
        String expectedAttribute = "status";
        assertEquals(expectedAttribute, ParserUtil.parseDeliveryAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseContactAttribute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseContactAttribute(null));
    }

    @Test
    public void parseContactAttribute_unknownAttributeWithoutWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseContactAttribute("gamer"));
    }

    @Test
    public void parseContactAttribute_unknownAttributeWithWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseContactAttribute(WHITESPACE + "gamer" + WHITESPACE));
    }

    @Test
    public void parseContactAttribute_dateAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "date";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(VALID_DELIVERY_ATTRIBUTE_DATE));
    }

    @Test
    public void parseContactAttribute_dateAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_DELIVERY_ATTRIBUTE_DATE + WHITESPACE;
        String expectedAttribute = "date";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseContactAttribute_emailAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "email";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(VALID_CONTACT_ATTRIBUTE_EMAIL));
    }

    @Test
    public void parseContactAttribute_emailAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_CONTACT_ATTRIBUTE_EMAIL + WHITESPACE;
        String expectedAttribute = "email";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseContactAttribute_nameAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "name";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(VALID_CONTACT_ATTRIBUTE_NAME));
    }

    @Test
    public void parseContactAttribute_nameAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_CONTACT_ATTRIBUTE_NAME + WHITESPACE;
        String expectedAttribute = "name";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseContactAttribute_phoneAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "phone";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(VALID_CONTACT_ATTRIBUTE_PHONE));
    }

    @Test
    public void parseContactAttribute_phoneAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_CONTACT_ATTRIBUTE_PHONE + WHITESPACE;
        String expectedAttribute = "phone";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseContactAttribute_roleAttributeWithoutWhitespace_returnsTrimmedAttribute() throws Exception {
        String expectedAttribute = "role";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(VALID_CONTACT_ATTRIBUTE_ROLE));
    }

    @Test
    public void parseContactAttribute_roleAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_CONTACT_ATTRIBUTE_ROLE + WHITESPACE;
        String expectedAttribute = "role";
        assertEquals(expectedAttribute, ParserUtil.parseContactAttribute(attributeWithWhitespace));
    }
}
