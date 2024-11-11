package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ALLERGY = "#friend";
    private static final String INVALID_MEDCON = "@Diabetes";
    private static final String INVALID_NRIC = "S123456";
    private static final String INVALID_GENDER = "x";
    private static final String INVALID_DATE_OF_BIRTH_FORMAT = "2020/12/12";
    private static final String INVALID_DATE_OF_BIRTH_VALUE = "2023-02-29";
    private static final String INVALID_DATE_OF_BIRTH_FUTURE = LocalDate.now().plusDays(2)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_ALLERGY_1 = "friend";
    private static final String VALID_MEDCON = "Diabetes";
    private static final String VALID_NRIC = "T0123456A";
    private static final String VALID_GENDER = "f";
    private static final String VALID_DOB = "2020-11-11";
    private static final String VALID_ALLERGY_2 = "neighbour";

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
    public void parseNric_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNric((String) null));
    }

    @Test
    public void parseNric_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNric(INVALID_NRIC));
    }

    @Test
    public void parseNric_validValueWithoutWhitespace_returnsNric() throws Exception {
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(VALID_NRIC));
    }

    @Test
    public void parseNric_validValueWithWhitespace_returnsTrimmedNric() throws Exception {
        String nricWithWhitespace = WHITESPACE + VALID_NRIC + WHITESPACE;
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(nricWithWhitespace));
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
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
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
    public void parseDateOfBirth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateOfBirth((String) null));
    }

    @Test
    public void parseDateOfBirth_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DATE_OF_BIRTH_FORMAT));
    }

    @Test
    public void parseDateOfBirth_invalidFutureDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DATE_OF_BIRTH_FUTURE));
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DATE_OF_BIRTH_VALUE));
    }

    @Test
    public void parseDateOfBirth_validValueWithoutWhitespace_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DOB);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(VALID_DOB));
    }

    @Test
    public void parseDateOfBirth_validValueWithWhitespace_returnsTrimmedDateOfBirth() throws Exception {
        String dobWithWhitespace = WHITESPACE + VALID_DOB + WHITESPACE;
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DOB);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(dobWithWhitespace));
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
    public void parseAllergy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAllergy(null));
    }

    @Test
    public void parseAllergy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAllergy(INVALID_ALLERGY));
    }

    @Test
    public void parseAllergy_validValueWithoutWhitespace_returnsTag() throws Exception {
        Allergy expectedTag = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedTag, ParserUtil.parseAllergy(VALID_ALLERGY_1));
    }

    @Test
    public void parseMedCon_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedCon(null));
    }

    @Test
    public void parseMedCon_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedCon(INVALID_MEDCON));
    }

    @Test
    public void parseMedCon_validValueWithoutWhitespace_returnsMedCon() throws Exception {
        MedCon expectedMedCon = new MedCon(VALID_MEDCON);
        assertEquals(expectedMedCon, ParserUtil.parseMedCon(VALID_MEDCON));
    }

    @Test
    public void parseMedCons_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedCon(null));
    }

}
