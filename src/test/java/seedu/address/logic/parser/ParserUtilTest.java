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
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String INVALID_PET_NAME = "henry*";
    private static final String INVALID_SPECIES = "Dog cat";
    private static final String INVALID_BREED = "3 toed sloth";
    private static final String INVALID_AGE = "100";
    private static final String INVALID_SEX = "Feeee";

    private static final String VALID_PET_NAME = "Oscar";
    private static final String VALID_SPECIES = "Dog";
    private static final String VALID_BREED = "German Shepard";
    private static final String VALID_AGE = "7";
    private static final String VALID_SEX_FEMALE = "f";
    private static final String VALID_SEX_MALE = "M";
    private static final String VALID_SEX_DISPLAY_FORMAT_FEMALE = "Female";
    private static final String VALID_SEX_DISPLAY_FORMAT_MALE = "Male";

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
    public void parsePetName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePetName((String) null));
    }

    @Test
    public void parsePetName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePetName(INVALID_PET_NAME));
    }

    @Test
    public void parsePetName_validValueWithoutWhitespace_returnsName() throws Exception {
        seedu.address.model.pet.Name expectedName = new seedu.address.model.pet.Name(VALID_PET_NAME);
        assertEquals(expectedName, ParserUtil.parsePetName(VALID_PET_NAME));
    }

    @Test
    public void parsePetName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_PET_NAME + WHITESPACE;
        seedu.address.model.pet.Name expectedName = new seedu.address.model.pet.Name(VALID_PET_NAME);
        assertEquals(expectedName, ParserUtil.parsePetName(nameWithWhitespace));
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
    public void parseSpecies_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSpecies((String) null));
    }

    @Test
    public void parseSpecies_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSpecies(INVALID_SPECIES));
    }

    @Test
    public void parseSpecies_validValueWithoutWhitespace_returnsSpecies() throws Exception {
        Species expectedSpecies = new Species(VALID_SPECIES);
        assertEquals(expectedSpecies, ParserUtil.parseSpecies(VALID_SPECIES));
    }

    @Test
    public void parseSpecies_validValueWithWhitespace_returnsTrimmedSpecies() throws Exception {
        String speciesWithWhitespace = WHITESPACE + VALID_SPECIES + WHITESPACE;
        Species expectedSpecies = new Species(VALID_SPECIES);
        assertEquals(expectedSpecies, ParserUtil.parseSpecies(speciesWithWhitespace));
    }

    @Test
    public void parseBreed_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBreed((String) null));
    }

    @Test
    public void parseBreed_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBreed(INVALID_BREED));
    }

    @Test
    public void parseBreed_validValueWithoutWhitespace_returnsBreed() throws Exception {
        Breed expectedBreed = new Breed(VALID_BREED);
        assertEquals(expectedBreed, ParserUtil.parseBreed(VALID_BREED));
    }

    @Test
    public void parseBreed_validValueWithWhitespace_returnsTrimmedBreed() throws Exception {
        String breedWithWhitespace = WHITESPACE + VALID_BREED + WHITESPACE;
        Breed expectedBreed = new Breed(VALID_BREED);
        assertEquals(expectedBreed, ParserUtil.parseBreed(breedWithWhitespace));
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
    public void parseAge_validValueWithoutWhitespace_returnsAge() throws Exception {
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(VALID_AGE));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAge() throws Exception {
        String ageWithWhitespace = WHITESPACE + VALID_AGE + WHITESPACE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(ageWithWhitespace));
    }

    @Test
    public void parseSex_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSex((String) null));
    }

    @Test
    public void parseSex_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSex(INVALID_SEX));
    }

    @Test
    public void parseSex_validValueWithoutWhitespace_returnsSex() throws Exception {
        Sex expectedSex = new Sex(VALID_SEX_DISPLAY_FORMAT_MALE);
        assertEquals(expectedSex, ParserUtil.parseSex(VALID_SEX_MALE));
    }

    @Test
    public void parseSex_validValueWithWhitespace_returnsTrimmedSex() throws Exception {
        String sexWithWhitespace = WHITESPACE + VALID_SEX_MALE + WHITESPACE;
        Sex expectedSex = new Sex(VALID_SEX_DISPLAY_FORMAT_MALE);
        assertEquals(expectedSex, ParserUtil.parseSex(sexWithWhitespace));
    }

    @Test
    public void parseSex_validFemaleValueConvertedToFullForm_returnsTrimmedSex() throws Exception {
        Sex expectedSex = new Sex(VALID_SEX_DISPLAY_FORMAT_FEMALE);
        assertEquals(expectedSex, ParserUtil.parseSex(VALID_SEX_FEMALE));
    }

    @Test
    public void parseSex_validMaleValueConvertedToFullForm_returnsTrimmedSex() throws Exception {
        Sex expectedSex = new Sex(VALID_SEX_DISPLAY_FORMAT_MALE);
        assertEquals(expectedSex, ParserUtil.parseSex(VALID_SEX_MALE));
    }

    @Test
    public void parseSex_validFullFormFemaleValueLeftAsItIs_returnsTrimmedSex() throws Exception {
        Sex expectedSex = new Sex(VALID_SEX_DISPLAY_FORMAT_FEMALE);
        assertEquals(expectedSex, ParserUtil.parseSex(VALID_SEX_DISPLAY_FORMAT_FEMALE));
    }

    @Test
    public void parseSex_validFullFormMaleValueLeftAsItIs_returnsTrimmedSex() throws Exception {
        Sex expectedSex = new Sex(VALID_SEX_DISPLAY_FORMAT_MALE);
        assertEquals(expectedSex, ParserUtil.parseSex(VALID_SEX_DISPLAY_FORMAT_MALE));
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
