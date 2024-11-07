package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_HOURS = "five";
    private static final String INVALID_SUBJECT = "Chinese";
    private static final String INVALIED_FILEPATH = "C:\\Users\\user\\Desktop\\";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505, 123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_HOURS = "5";
    private static final String VALID_SUBJECT = "Math";
    private static final String VALID_FILEPATH = "~/Desktop/tp/src/test/data/CsvImportTest/typicalPersonsCsv.csv";

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
    public void parseHours_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHours(null));
    }

    @Test
    public void parseHours_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseHours(INVALID_HOURS));
    }

    @Test
    public void parseHours_validValueWithoutWhitespace_returnsHours() throws Exception {
        Hours expectedHours = new Hours(VALID_HOURS);
        assertEquals(expectedHours, ParserUtil.parseHours(VALID_HOURS));
    }

    @Test
    public void parseHours_validValueWithWhitespace_returnsTrimmedHours() throws Exception {
        String hoursWithWhitespace = WHITESPACE + VALID_HOURS + WHITESPACE;
        Hours expectedHours = new Hours(VALID_HOURS);
        assertEquals(expectedHours, ParserUtil.parseHours(hoursWithWhitespace));
    }

    @Test
    public void parseSubject_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject(null));
    }

    @Test
    public void parseSubject_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject(INVALID_SUBJECT));
    }

    @Test
    public void parseSubject_validValueWithoutWhitespace_returnsSubject() throws Exception {
        Subject expectedSubject = new Subject(VALID_SUBJECT);
        assertEquals(expectedSubject, ParserUtil.parseSubject(VALID_SUBJECT));
    }

    @Test
    public void parseSubject_validValueWithWhitespace_returnsTrimmedSubject() throws Exception {
        String subjectWithWhitespace = WHITESPACE + VALID_SUBJECT + WHITESPACE;
        Subject expectedSubject = new Subject(VALID_SUBJECT);
        assertEquals(expectedSubject, ParserUtil.parseSubject(subjectWithWhitespace));
    }

    @Test
    public void parseSubjects_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubjects(null));
    }

    @Test
    public void parseSubjects_invalidValue_throwsParseException() {
        Collection<String> expectedSet = Set.of("Math", "Chinese");
        assertThrows(ParseException.class, () -> ParserUtil.parseSubjects(expectedSet));
    }

    @Test
    public void parseSubjects_validValueWithoutWhitespace_returnsSubjects() throws Exception {
        Collection<Subject> expectedSet = Set.of(new Subject("Math"), new Subject("English"));
        Collection<String> subjectsWithoutWhitespace = Set.of("Math", "English");
        assertEquals(expectedSet, ParserUtil.parseSubjects(subjectsWithoutWhitespace));
    }

    @Test
    public void parseSubjects_validValueWithWhitespace_returnsTrimmedSubjects() throws Exception {
        Collection<Subject> expectedSet = Set.of(new Subject("Math"), new Subject("English"));
        Collection<String> subjectsWithWhitespace = Set.of(WHITESPACE + "Math" + WHITESPACE,
                WHITESPACE + "English" + WHITESPACE);
        assertEquals(expectedSet, ParserUtil.parseSubjects(subjectsWithWhitespace));
    }

    @Test
    public void parseSubjects_duplicateSubjects_returnsSubjects() throws Exception {
        Collection<Subject> expectedSet = Set.of(new Subject("Math"));
        Collection<String> subjectsWithWhitespace = Set.of("Math", "Math ");
        assertEquals(expectedSet, ParserUtil.parseSubjects(subjectsWithWhitespace));
    }

    @Test
    public void parseFilepath_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFilepath(null));
    }

    @Test
    public void parseFilepath_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFilepath(INVALIED_FILEPATH));
    }

    @Test
    public void parseFilepath_validValueWithoutWhitespace_returnsFilepath() throws Exception {
        String replacedFilepath = VALID_FILEPATH.replaceFirst("~", System.getProperty("user.home"));
        assertEquals(replacedFilepath, ParserUtil.parseFilepath(VALID_FILEPATH));
    }

    @Test
    public void parseFilepath_validValueWithWhitespace_returnsTrimmedFilepath() throws Exception {
        String replacedFilepath = VALID_FILEPATH.replaceFirst("~", System.getProperty("user.home"));
        String filepathWithWhitespace = WHITESPACE + VALID_FILEPATH + WHITESPACE;
        assertEquals(replacedFilepath, ParserUtil.parseFilepath(filepathWithWhitespace));
    }
}
