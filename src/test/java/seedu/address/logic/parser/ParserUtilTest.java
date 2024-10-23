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
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_REGISTER_NUMBER = "41";
    private static final String INVALID_SEX = "H";
    private static final String INVALID_STUDENT_CLASS = "A1";
    private static final String INVALID_EMERGENCY_CONTACT_NAME = "--";
    private static final String INVALID_EMERGENCY_CONTACT_NUMBER = "1234";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SORT_ATTRIBUTE = "names";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_REGISTER_NUMBER = "1";
    private static final String VALID_SEX = "F";
    private static final String VALID_STUDENT_CLASS = "1A";
    private static final String VALID_EMERGENCY_CONTACT_NAME = "Joe Walker";
    private static final String VALID_EMERGENCY_CONTACT_NUMBER = "91234567";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_SORT_ATTRIBUTE = "register number";

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
    public void parseRegisterNumber_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRegisterNumber((String) null));
    }

    @Test
    public void parseRegisterNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRegisterNumber(INVALID_REGISTER_NUMBER));
    }

    @Test
    public void parseRegisterNumber_validValueWithoutWhitespace_returnsRegisterNumber() throws Exception {
        RegisterNumber expectedRegisterNumber = new RegisterNumber(VALID_REGISTER_NUMBER);
        assertEquals(expectedRegisterNumber, ParserUtil.parseRegisterNumber(VALID_REGISTER_NUMBER));
    }

    @Test
    public void parseRegisterNumber_validValueWithWhitespace_returnsTrimmedRegisterNumber() throws Exception {
        String registerNumberWithWhitespace = WHITESPACE + VALID_REGISTER_NUMBER + WHITESPACE;
        RegisterNumber expectedRegisterNumber = new RegisterNumber(VALID_REGISTER_NUMBER);
        assertEquals(expectedRegisterNumber, ParserUtil.parseRegisterNumber(registerNumberWithWhitespace));
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
    public void parseStudentClass_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudentClass((String) null));
    }

    @Test
    public void parseStudentClass_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudentClass(INVALID_STUDENT_CLASS));
    }

    @Test
    public void parseStudentClass_validValueWithoutWhitespace_returnsStudentClass() throws Exception {
        StudentClass expectedStudentClass = new StudentClass(VALID_STUDENT_CLASS);
        assertEquals(expectedStudentClass, ParserUtil.parseStudentClass(VALID_STUDENT_CLASS));
    }

    @Test
    public void parseStudentClass_validValueWithWhitespace_returnsTrimmedStudentClass() throws Exception {
        String studentClassWithWhitespace = WHITESPACE + VALID_STUDENT_CLASS + WHITESPACE;
        StudentClass expectedStudentClass = new StudentClass(VALID_STUDENT_CLASS);
        assertEquals(expectedStudentClass, ParserUtil.parseStudentClass(studentClassWithWhitespace));
    }

    @Test
    public void parseEmergencyContactName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmergencyContactName((String) null));
    }

    @Test
    public void parseEmergencyContactName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmergencyContactName(INVALID_EMERGENCY_CONTACT_NAME));
    }

    @Test
    public void parseEmergencyContactName_validValueWithoutWhitespace_returnsEcName() throws Exception {
        EcName expectedEcName = new EcName(VALID_EMERGENCY_CONTACT_NAME);
        assertEquals(expectedEcName, ParserUtil.parseEmergencyContactName(VALID_EMERGENCY_CONTACT_NAME));
    }

    @Test
    public void parseEmergencyContactName_validValueWithWhitespace_returnsTrimmedEcName() throws Exception {
        String ecNameWithWhitespace = WHITESPACE + VALID_EMERGENCY_CONTACT_NAME + WHITESPACE;
        EcName expectedEcName = new EcName(VALID_EMERGENCY_CONTACT_NAME);
        assertEquals(expectedEcName, ParserUtil.parseEmergencyContactName(ecNameWithWhitespace));
    }

    @Test
    public void parseEcNumber_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEcNumber((String) null));
    }

    @Test
    public void parseEcNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEcNumber(INVALID_EMERGENCY_CONTACT_NUMBER));
    }

    @Test
    public void parseEcNumber_validValueWithoutWhitespace_returnsEcNumber() throws Exception {
        EcNumber expectedEcNumber = new EcNumber(VALID_EMERGENCY_CONTACT_NUMBER);
        assertEquals(expectedEcNumber, ParserUtil.parseEcNumber(VALID_EMERGENCY_CONTACT_NUMBER));
    }

    @Test
    public void parseEcNumber_validValueWithWhitespace_returnsTrimmedEcNumber() throws Exception {
        String ecNumberWithWhitespace = WHITESPACE + VALID_EMERGENCY_CONTACT_NUMBER + WHITESPACE;
        EcNumber expectedEcNumber = new EcNumber(VALID_EMERGENCY_CONTACT_NUMBER);
        assertEquals(expectedEcNumber, ParserUtil.parseEcNumber(ecNumberWithWhitespace));
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
    public void parseSortAttribute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortAttribute((String) null));
    }

    @Test
    public void parseSortAttribute_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortAttribute(INVALID_SORT_ATTRIBUTE));
    }

    @Test
    public void parseSortAttribute_validValueWithoutWhitespace_returnsSortAttribute() throws Exception {
        ParserUtil.SortAttribute expectedSortAttribute = ParserUtil.SortAttribute.REGISTERNUMBER;
        assertEquals(expectedSortAttribute, ParserUtil.parseSortAttribute(VALID_SORT_ATTRIBUTE));
    }

    @Test
    public void parseSortAttribute_validValueWithWhitespace_returnsTrimmedSortAttribute() throws Exception {
        String sortAttributeWithWhiteSpace = WHITESPACE + VALID_SORT_ATTRIBUTE + WHITESPACE;
        ParserUtil.SortAttribute expectedSortAttribute = ParserUtil.SortAttribute.REGISTERNUMBER;
        assertEquals(expectedSortAttribute, ParserUtil.parseSortAttribute(sortAttributeWithWhiteSpace));
    }
}
