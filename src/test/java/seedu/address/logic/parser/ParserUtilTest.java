package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;
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
    public void parseAssignmentName_validName_success() throws Exception {
        AssignmentName assignmentName = ParserUtil.parseAssignmentName("CS2103 Project");
        assertEquals("CS2103 Project", assignmentName.fullName);
    }

    @Test
    public void parseAssignmentName_invalidName_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignmentName("  "));
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignmentName("!!InvalidName"));
    }

    @Test
    public void parseDeadline_validDate_success() throws Exception {
        Deadline deadline = ParserUtil.parseDeadline("2024-12-01");
        assertEquals("2024-12-01", deadline.deadline.format(Deadline.DATE_TIME_FORMATTER));
    }

    @Test
    public void parseDeadline_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline("2024-13-01"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline("invalid-date"));
    }

    @Test
    public void parseStatus_validStatus_success() throws Exception {
        Status status1 = ParserUtil.parseStatus("Y");
        Status status2 = ParserUtil.parseStatus("n");

        assertEquals(Status.State.Y, status1.status);
        assertEquals(Status.State.N, status2.status);
    }

    @Test
    public void parseStatus_invalidStatus_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus("Yes"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus("maybe"));
    }

    @Test
    public void parseStatuses_validStatuses_success() throws Exception {
        List<String> statuses = Arrays.asList("Y", "N", "y", "n");
        List<Status> parsedStatuses = ParserUtil.parseStatuses(statuses, 4);

        assertEquals(4, parsedStatuses.size());
        assertEquals(Status.State.Y, parsedStatuses.get(0).status);
        assertEquals(Status.State.N, parsedStatuses.get(1).status);
        assertEquals(Status.State.Y, parsedStatuses.get(2).status);
        assertEquals(Status.State.N, parsedStatuses.get(3).status);
    }

    @Test
    public void parseStatuses_invalidStatuses_throwsParseException() {
        List<String> statuses = Arrays.asList("Y", "maybe");
        assertThrows(ParseException.class, () -> ParserUtil.parseStatuses(statuses, 2));
    }

    @Test
    public void parseGrade_validGrade_success() throws Exception {
        Grade grade1 = ParserUtil.parseGrade("85.5");
        Grade grade2 = ParserUtil.parseGrade("NULL");

        assertTrue(grade1.grade.isPresent());
        assertEquals(85.5, grade1.grade.get());

        assertFalse(grade2.grade.isPresent());
    }

    @Test
    public void parseGrade_invalidGrade_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGrade("invalid"));
        assertThrows(ParseException.class, () -> ParserUtil.parseGrade("101")); // greater than 100
    }

    @Test
    public void parseTutorialGroup_validValue_success() throws Exception {
        TutorialGroup expectedGroup = new TutorialGroup("G01");
        assertEquals(expectedGroup, ParserUtil.parseTutorialGroup("G01"));
    }

    @Test
    public void parseTutorialGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialGroup("Invalid Group"));
    }

    @Test
    public void parseStudentNumber_validValue_success() throws Exception {
        StudentNumber expectedNumber = new StudentNumber("S1234567A");
        assertEquals(expectedNumber, ParserUtil.parseStudentNumber("S1234567A"));
    }

    @Test
    public void parseStudentNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudentNumber("Invalid Number"));
    }

    @Test
    public void parseDate_validValue_success() throws Exception {
        LocalDate expectedDate = LocalDate.of(2024, 12, 01);
        assertEquals(expectedDate, ParserUtil.parseDate("2024-12-01"));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("2024-13-01"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("invalid-date"));
    }

    @Test
    public void parseAttendance_validValue_success() throws Exception {
        Attendance expectedAttendance = new Attendance("p");
        assertEquals(expectedAttendance, ParserUtil.parseAttendance("p"));
    }

    @Test
    public void parseAttendance_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttendance("invalid-status"));
    }
}
