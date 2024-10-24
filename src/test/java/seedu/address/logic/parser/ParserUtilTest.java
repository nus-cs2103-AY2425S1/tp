package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_SUBJECT = "subj";
    private static final String INVALID_TASK_DESCRIPTION = " ";
    private static final String INVALID_TASK_DEADLINE = "tomorrow";
    private static final String INVALID_LESSON_TIME = "every thurs";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_SUBJECT_1 = "MATH";
    private static final String VALID_SUBJECT_2 = "ENGLISH";
    private static final String VALID_TASK_DESCRIPTION = "Mark work";
    private static final String VALID_TASK_DEADLINE = "2024-01-01";
    private static final String VALID_LESSON_TIME = "SUN-11:00-13:30";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void arePrefixPresentTest() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_NAME, "name");
        argumentMultimap.put(PREFIX_NOTE, "");

        // All are present
        assertTrue(arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_NOTE));

        // some are present
        assertFalse(arePrefixesPresent(argumentMultimap, PREFIX_NOTE, PREFIX_TASK_INDEX));

        // None are present
        assertFalse(arePrefixesPresent(argumentMultimap, PREFIX_EMERGENCY_CONTACT, PREFIX_ADDRESS));
    }

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
    public void parseName_validValueWithMultipleWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + "Rachel   Walker" + WHITESPACE;
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
    public void parseSubject_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject(null));
    }

    @Test
    public void parseSubject_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject(INVALID_SUBJECT));
    }

    @Test
    public void parseSubject_validValueWithoutWhitespace_returnsSubject() throws Exception {
        Subject expectedSubject = new Subject(VALID_SUBJECT_1);
        assertEquals(expectedSubject, ParserUtil.parseSubject(VALID_SUBJECT_1));
    }

    @Test
    public void parseSubject_validValueWithWhitespace_returnsTrimmedSubject() throws Exception {
        String subjectWithWhitespace = WHITESPACE + VALID_SUBJECT_1 + WHITESPACE;
        Subject expectedSubject = new Subject(VALID_SUBJECT_1);
        assertEquals(expectedSubject, ParserUtil.parseSubject(subjectWithWhitespace));
    }

    @Test
    public void parseSubjects_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubjects(null));
    }

    @Test
    public void parseSubjects_collectionWithInvalidSubjects_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSubjects(
                Arrays.asList(VALID_SUBJECT_1, INVALID_SUBJECT)));
    }

    @Test
    public void parseSubjects_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSubjects(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSubjects_collectionWithValidSubjects_returnsSubjectSet() throws Exception {
        Set<Subject> actualSubjectSet = ParserUtil.parseSubjects(Arrays.asList(VALID_SUBJECT_1, VALID_SUBJECT_2));
        Set<Subject> expectedSubjectSet = new HashSet<Subject>(
                Arrays.asList(new Subject(VALID_SUBJECT_1), new Subject(VALID_SUBJECT_2)));

        assertEquals(expectedSubjectSet, actualSubjectSet);
    }

    @Test
    public void parseTaskDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTaskDescription((String) null));
    }

    @Test
    public void parseTaskDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTaskDescription(INVALID_TASK_DESCRIPTION));
    }

    @Test
    public void parseTaskDescription_validValueWithoutWhitespace_returnsTaskDescription() throws Exception {
        TaskDescription expectedTaskDescription = new TaskDescription(VALID_TASK_DESCRIPTION);
        assertEquals(expectedTaskDescription, ParserUtil.parseTaskDescription(VALID_TASK_DESCRIPTION));
    }

    @Test
    public void parseTaskDescription_validValueWithWhitespace_returnsTrimmedTaskDescription() throws Exception {
        String taskDescriptionWithWhitespace = WHITESPACE + VALID_TASK_DESCRIPTION + WHITESPACE;
        TaskDescription expectedTaskDescription = new TaskDescription(VALID_TASK_DESCRIPTION);
        assertEquals(expectedTaskDescription, ParserUtil.parseTaskDescription(taskDescriptionWithWhitespace));
    }

    @Test
    public void parseTaskDeadline_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTaskDeadline((String) null));
    }

    @Test
    public void parseTaskDeadline_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTaskDeadline(INVALID_TASK_DEADLINE));
    }

    @Test
    public void parseTaskDeadline_validValueWithoutWhitespace_returnsTaskDeadline() throws Exception {
        TaskDeadline expectedTaskDeadline = new TaskDeadline(VALID_TASK_DEADLINE);
        assertEquals(expectedTaskDeadline, ParserUtil.parseTaskDeadline(VALID_TASK_DEADLINE));
    }

    @Test
    public void parseTaskDeadline_validValueWithWhitespace_returnsTrimmedTaskDeadline() throws Exception {
        String taskDeadlineWithWhitespace = WHITESPACE + VALID_TASK_DEADLINE + WHITESPACE;
        TaskDeadline expectedTaskDeadline = new TaskDeadline(VALID_TASK_DEADLINE);
        assertEquals(expectedTaskDeadline, ParserUtil.parseTaskDeadline(taskDeadlineWithWhitespace));
    }

    @Test
    public void parseTask_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTask(null, null));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTask(VALID_TASK_DESCRIPTION, null));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTask(null, VALID_TASK_DEADLINE));
    }

    @Test
    public void parseTask_validValue_returnsTask() throws Exception {
        Task expectedTask = new Task(new TaskDescription(VALID_TASK_DESCRIPTION),
                new TaskDeadline(VALID_TASK_DEADLINE));
        assertEquals(expectedTask, ParserUtil.parseTask(VALID_TASK_DESCRIPTION, VALID_TASK_DEADLINE));
    }

    @Test
    public void parseLessonTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonTime((String) null));
    }

    @Test
    public void parseLessonTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTime(INVALID_LESSON_TIME));
    }

    @Test
    public void parseLessonTime_validValueWithoutWhitespace_returnsLessonTime() throws Exception {
        LessonTime expectedLessonTime = new LessonTime(VALID_LESSON_TIME);
        assertEquals(expectedLessonTime, ParserUtil.parseLessonTime(VALID_LESSON_TIME));
    }

    @Test
    public void parseLessonTime_validValueWithWhitespace_returnsTrimmedLessonTime() throws Exception {
        String lessonTimeWithWhitespace = WHITESPACE + VALID_LESSON_TIME + WHITESPACE;
        LessonTime expectedLessonTime = new LessonTime(VALID_LESSON_TIME);
        assertEquals(expectedLessonTime, ParserUtil.parseLessonTime(lessonTimeWithWhitespace));
    }

    @Test
    public void parseLessonTimes_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonTimes(null));
    }

    @Test
    public void parseLessonTimes_collectionWithInvalidLessonTimes_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTimes(
                Arrays.asList(VALID_LESSON_TIME, INVALID_LESSON_TIME)));
    }

    @Test
    public void parseLessonTimes_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseLessonTimes(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseLessonTimes_collectionWithValidLessonTimes_returnsLessonTimeSet() throws Exception {
        Set<LessonTime> actualLessonTimeSet = ParserUtil.parseLessonTimes(
                Arrays.asList(VALID_LESSON_TIME, "MON-13:00-15:00"));
        Set<LessonTime> expectedLessonTimeSet = new HashSet<LessonTime>(
                Arrays.asList(new LessonTime(VALID_LESSON_TIME), new LessonTime("MON-13:00-15:00")));

        assertEquals(expectedLessonTimeSet, actualLessonTimeSet);
    }
}
