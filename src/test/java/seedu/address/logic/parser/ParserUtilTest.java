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
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_AGE = "1011";
    private static final String INVALID_SEX = "M@le";
    private static final String INVALID_APPOINTMENT = "111/111/111";
    private static final String INVALID_MEDICATION = "99! gram";
    private static final String INVALID_REMARK = "INVALID_REMARK";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_AGE = "50";
    private static final String VALID_SEX = "Female";
    private static final String VALID_APPOINTMENT_1 = "01/01/2025 1200";
    private static final String VALID_APPOINTMENT_2 = "01/01/2025 0000";
    private static final String VALID_MEDICATION = "10mg Ibuprofen";
    private static final String VALID_REMARK = "Allergic to Ibuprofen";

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
        Sex expectedSex = new Sex(VALID_SEX);
        assertEquals(expectedSex, ParserUtil.parseSex(VALID_SEX));
    }

    @Test
    public void parseSex_validValueWithWhitespace_returnsTrimmedSex() throws Exception {
        String sexWithWhitespace = WHITESPACE + VALID_SEX + WHITESPACE;
        Sex expectedSex = new Sex(VALID_SEX);
        assertEquals(expectedSex, ParserUtil.parseSex(sexWithWhitespace));
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
    public void parseAppointment_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAppointment(null));
    }

    @Test
    public void parseAppointment_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAppointment(INVALID_APPOINTMENT));
    }

    @Test
    public void parseAppointment_validValueWithoutWhitespace_returnsAppointment() throws Exception {
        Appointment expectedAppointment = new Appointment(VALID_APPOINTMENT_1);
        assertEquals(expectedAppointment, ParserUtil.parseAppointment(VALID_APPOINTMENT_1));
    }

    @Test
    public void parseAppointment_validValueWithWhitespace_returnsTrimmedAppointment() throws Exception {
        String appointmentWithWhitespace = WHITESPACE + VALID_APPOINTMENT_1 + WHITESPACE;
        Appointment expectedAppointment = new Appointment(VALID_APPOINTMENT_1);
        assertEquals(expectedAppointment, ParserUtil.parseAppointment(appointmentWithWhitespace));
    }

    @Test
    public void parseAppointments_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAppointments(null));
    }

    @Test
    public void parseAppointments_collectionWithInvalidAppointments_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseAppointments(Arrays.asList(VALID_APPOINTMENT_1, INVALID_APPOINTMENT)));
    }

    @Test
    public void parseAppointments_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAppointments(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAppointments_collectionWithValidAppointments_returnsAppointmentSet() throws Exception {
        Set<Appointment> actualAppointmentSet = ParserUtil
                .parseAppointments(Arrays.asList(VALID_APPOINTMENT_1, VALID_APPOINTMENT_2));
        Set<Appointment> expectedAppointmentSet =
                new HashSet<Appointment>(Arrays.asList(new Appointment(VALID_APPOINTMENT_1),
                        new Appointment(VALID_APPOINTMENT_2)));

        assertEquals(expectedAppointmentSet, actualAppointmentSet);
    }

    @Test
    public void parseMedication_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedication(null));
    }

    @Test
    public void parseMedication_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedication(INVALID_MEDICATION));
    }

    @Test
    public void parseMedication_validValueWithoutWhitespace_returnsMedication() throws Exception {
        assertEquals(VALID_MEDICATION, ParserUtil.parseMedication(VALID_MEDICATION));
    }

    @Test
    public void parseMedication_validValueWithWhitespace_returnsTrimmedMedication() throws Exception {
        String medicationWithWhitespace = WHITESPACE + VALID_MEDICATION + WHITESPACE;
        assertEquals(VALID_MEDICATION, ParserUtil.parseMedication(medicationWithWhitespace));
    }

    @Test
    public void parseMedications_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedications(null));
    }

    @Test
    public void parseMedications_collectionWithInvalidMedications_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseMedications(Arrays.asList(VALID_MEDICATION, INVALID_MEDICATION)));
    }

    @Test
    public void parseMedications_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMedications(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseMedications_collectionWithValidMedications_returnsMedicationSet() throws Exception {
        Set<String> actualMedicationSet = ParserUtil
                .parseMedications(Arrays.asList(VALID_MEDICATION, VALID_MEDICATION));
        Set<String> expectedMedicationSet = new HashSet<String>(Arrays.asList(VALID_MEDICATION, VALID_MEDICATION));

        assertEquals(expectedMedicationSet, actualMedicationSet);
    }

    @Test
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark(null));
    }

    @Test
    public void parseRemark_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRemark(INVALID_REMARK));
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsRemark() throws Exception {
        assertEquals(VALID_REMARK, ParserUtil.parseRemark(VALID_REMARK));
    }

    @Test
    public void parseRemark_validValueWithWhitespace_returnsTrimmedRemark() throws Exception {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        assertEquals(VALID_REMARK, ParserUtil.parseRemark(remarkWithWhitespace));
    }

    @Test
    public void parseRemarks_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemarks(null));
    }

    @Test
    public void parseRemarks_collectionWithInvalidRemarks_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRemarks(Arrays.asList(VALID_REMARK, INVALID_REMARK)));
    }

    @Test
    public void parseRemarks_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseRemarks(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseRemarks_collectionWithValidRemarks_returnsRemarkSet() throws Exception {
        Set<String> actualRemarkSet = ParserUtil.parseRemarks(Arrays.asList(VALID_REMARK, VALID_REMARK));
        Set<String> expectedRemarkSet = new HashSet<String>(Arrays.asList(VALID_REMARK, VALID_REMARK));

        assertEquals(expectedRemarkSet, actualRemarkSet);
    }
}
