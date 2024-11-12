package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Date;
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
    private static final String INVALID_ALLERGY = "#chocolate-";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "98765432";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "High Risk";
    private static final String VALID_TAG_2 = "Low Risk";
    private static final String VALID_ALLERGY_1 = "Fish";
    private static final String VALID_ALLERGY_2 = "Peanut";
    private static final String VALID_DATE_1 = "26/5/2024 1900";
    private static final String VALID_DATE_2 = "None"; //remove date from a person
    private static final String INVALID_DATE_1 = "26/14/2024 1900";
    private static final String INVALID_DATE_2 = "invalid date";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        // not integer
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));

        // large integer boundary value
        assertThrows(ParseException.class, Messages.MESSAGE_INVALID_PERSON_OUT_OF_BOUNDS, ()
                -> ParserUtil.parseIndex(Integer.toString(1000000000)));
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
    public void parseAllergy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAllergy(null));
    }

    @Test
    public void parseAllergy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAllergy(INVALID_ALLERGY));
    }

    @Test
    public void parseAllergy_validValueWithoutWhitespace_returnsAllergy() throws Exception {
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(VALID_ALLERGY_1));
    }

    @Test
    public void parseAllergy_validValueWithWhitespace_returnsTrimmedAllergy() throws Exception {
        String allergyWithWhitespace = WHITESPACE + VALID_ALLERGY_1 + WHITESPACE;
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(allergyWithWhitespace));
    }

    @Test
    public void parseAllergies_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAllergies(null));
    }

    @Test
    public void parseAllergies_collectionWithInvalidAllergies_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseAllergies(Arrays.asList(VALID_ALLERGY_1, INVALID_ALLERGY)));
    }

    @Test
    public void parseAllergies_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAllergies(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAllergies_collectionWithValidAllergies_returnsAllergySet() throws Exception {
        Set<Allergy> actualAllergySet = ParserUtil.parseAllergies(Arrays.asList(VALID_ALLERGY_1, VALID_ALLERGY_2));
        Set<Allergy> expectedAllergySet = new HashSet<Allergy>(Arrays.asList(new Allergy(VALID_ALLERGY_1),
                new Allergy(VALID_ALLERGY_2)));

        assertEquals(expectedAllergySet, actualAllergySet);
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateAndTime(INVALID_DATE_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateAndTime(INVALID_DATE_2));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(LocalDateTime.of(2024, 5, 26, 19, 0));
        assertEquals(expectedDate, ParserUtil.parseDateAndTime(VALID_DATE_1));

        expectedDate = Date.NO_DATE;
        assertEquals(expectedDate, ParserUtil.parseDateAndTime(VALID_DATE_2));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(LocalDateTime.of(2024, 5, 26, 19, 0));
        assertEquals(expectedDate, ParserUtil.parseDateAndTime(WHITESPACE + VALID_DATE_1 + WHITESPACE));

        expectedDate = Date.NO_DATE;
        assertEquals(expectedDate, ParserUtil.parseDateAndTime(WHITESPACE + VALID_DATE_2 + WHITESPACE));
    }

}
