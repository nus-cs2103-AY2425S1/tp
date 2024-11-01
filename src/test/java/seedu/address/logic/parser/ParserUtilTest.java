package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String EMPTY_ENTRY = "   ";

    private static final String INVALID_NAME = "      \t   \n  ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_SHORT_PHONE = "999";
    private static final String INVALID_LONG_PHONE = "123456789";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_EMERGENCY_CONTACT = INVALID_PHONE;
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATEOFLASTVISIT = "13/13/2024";

    private static final String NO_ENTRY = "";

    private static final String VALID_NAME = "Rachel Walker-Runner";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_EMERGENCY_CONTACT = "12345678";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DATEOFLASTVISIT = "02-02-2024";

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
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_SHORT_PHONE));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_LONG_PHONE));
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
    public void parseAddress_emptyValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseAddress(Optional.of(EMPTY_ENTRY)));
    }

    @Test
    public void parseAddress_noValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseAddress(Optional.of(NO_ENTRY)));
    }

    @Test
    public void parseAddress_null_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseAddress(Optional.ofNullable((String) null)));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(Optional.of(expectedAddress), ParserUtil.parseAddress(Optional.of(VALID_ADDRESS)));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(Optional.of(expectedAddress),
                ParserUtil.parseAddress(Optional.of(addressWithWhitespace)));
    }

    @Test
    public void parseEmail_emptyValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseEmail(Optional.of(EMPTY_ENTRY)));
    }

    @Test
    public void parseEmail_noValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseEmail(Optional.of(NO_ENTRY)));
    }


    @Test
    public void parseEmail_null_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseEmail(Optional.ofNullable((String) null)));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(Optional.of(INVALID_EMAIL)));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(Optional.of(expectedEmail), ParserUtil.parseEmail(Optional.of(VALID_EMAIL)));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(Optional.of(expectedEmail), ParserUtil.parseEmail(Optional.of(emailWithWhitespace)));
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
    public void parseDateOfLastVisit_emptyValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseDateOfLastVisit(Optional.of(EMPTY_ENTRY)));
    }

    @Test
    public void parseDateOfLastVisit_noValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseDateOfLastVisit(Optional.of(NO_ENTRY)));
    }

    @Test
    public void parseDateOfLastVisit_null_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseDateOfLastVisit(Optional.ofNullable((String) null)));
    }

    @Test
    public void parseDateOfLastVisit_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseDateOfLastVisit(Optional.of(INVALID_DATEOFLASTVISIT)));
    }

    @Test
    public void parseDateOfLastVisit_validValueWithoutWhitespace_returnsDateOfLastVisit() throws Exception {
        DateOfLastVisit expectedDateOfLastVisit = new DateOfLastVisit(VALID_DATEOFLASTVISIT);
        assertEquals(Optional.of(expectedDateOfLastVisit),
                ParserUtil.parseDateOfLastVisit(Optional.of(VALID_DATEOFLASTVISIT)));
    }

    @Test
    public void parseDateOfLastVisit_validValueWithWhitespace_returnsTrimmedDateOfLastVisit()
            throws Exception {
        String dateOfLastVisitWithWhitespace = WHITESPACE + VALID_DATEOFLASTVISIT + WHITESPACE;
        DateOfLastVisit expectedDateOfLastVisit = new DateOfLastVisit(VALID_DATEOFLASTVISIT);
        assertEquals(Optional.of(expectedDateOfLastVisit),
                ParserUtil.parseDateOfLastVisit(Optional.of(dateOfLastVisitWithWhitespace)));
    }

    @Test
    public void parseEmergencyContact_emptyValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseEmergencyContact(Optional.of(EMPTY_ENTRY)));
    }

    @Test
    public void parseEmergencyContact_noValue_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseEmergencyContact(Optional.of(NO_ENTRY)));
    }


    @Test
    public void parseEmergencyContact_null_optionalEmpty() throws Exception {
        assertEquals(Optional.empty(), ParserUtil.parseEmergencyContact(Optional.ofNullable((String) null)));
    }

    @Test
    public void parseEmergencyContact_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseEmergencyContact(Optional.of(INVALID_EMERGENCY_CONTACT)));
    }

    @Test
    public void parseEmergencyContact_validValueWithoutWhitespace_returnsEmergencyContact() throws Exception {
        EmergencyContact expectedEmergencyContact = new EmergencyContact(VALID_EMERGENCY_CONTACT);
        assertEquals(Optional.of(expectedEmergencyContact),
                ParserUtil.parseEmergencyContact(Optional.of(VALID_EMERGENCY_CONTACT)));
    }
}
