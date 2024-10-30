package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

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
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String BLANK_ADDRESS = "";
    private static final String VALID_TAG_1_NAME = "florist";
    private static final String VALID_TAG_2_NAME = "photographer";

    private static final String INVALID_TASK_TYPE = "unknownTask";
    private static final String INVALID_TODO_DESCRIPTION = "todo"; // No description
    private static final String INVALID_DEADLINE_FORMAT = "deadline Submit assignment /by";
    private static final String INVALID_EVENT_FORMAT = "event Conference /from 2024-10-01";

    private static final String VALID_TODO_DESCRIPTION = "todo Buy groceries";
    private static final String VALID_DEADLINE_DESCRIPTION = "deadline Submit assignment /by 2024-12-31";
    private static final String VALID_EVENT_DESCRIPTION = "event Conference /from 2024-10-01 /to 2024-10-05";

    private static final String VALID_EVENT_START_DATE = "2024-10-01";
    private static final String VALID_EVENT_END_DATE = "2024-10-05";
    private static final String VALID_DEADLINE_DATE = "2024-12-31";

    private static final String INVALID_DEADLINE_DATE_MONTH = "deadline Submit assignment /by 2024-13-31";
    private static final String INVALID_DEADLINE_DATE_DAY = "deadline Submit assignment /by 2024-12-32";
    private static final String INVALID_DEADLINE_DATE_STRING = "deadline Submit assignment /by not-a-date";

    private static final String INVALID_EVENT_DATE_MONTH = "event Conference /from 2024-13-01 /to 2024-12-31";
    private static final String INVALID_EVENT_DATE_DAY = "event Conference /from 2024-12-01 /to 2024-12-32";
    private static final String INVALID_EVENT_DATE_STRING = "event Conference /from 2024-12-01 /to not-a-date";
    private static final String INVALID_EVENT_DATE_ORDER = "event Conference /from 2024-12-31 /to 2024-12-01";

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
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
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
    public void parseAddress_blankValue_returnsBlankAddressTrimmed() throws Exception {
        Address expectedAddress = new Address(BLANK_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(WHITESPACE));
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
        Tag expectedTag = new Tag(new TagName(VALID_TAG_1_NAME));
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1_NAME));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1_NAME + WHITESPACE;
        Tag expectedTag = new Tag(new TagName(VALID_TAG_1_NAME));
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1_NAME, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1_NAME, VALID_TAG_2_NAME));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(new TagName(VALID_TAG_1_NAME)),
                new Tag(new TagName(VALID_TAG_2_NAME))));

        assertEquals(expectedTagSet, actualTagSet);
    }
    /*
    *=======================================================================================
    */
    @Test
    public void parseTask_invalidTaskType_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_TASK_TYPE));
    }

    @Test
    public void parseTask_invalidTodoDescription_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_TODO_DESCRIPTION));
    }

    @Test
    public void parseTask_invalidDeadlineFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_DEADLINE_FORMAT));
    }

    @Test
    public void parseTask_invalidEventFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_EVENT_FORMAT));
    }

    @Test
    public void parseTask_validTodoDescription_returnsTodo() throws Exception {
        Todo expectedTodo = new Todo("Buy groceries");
        Task actualTask = ParserUtil.parseTask(VALID_TODO_DESCRIPTION);
        assertEquals(expectedTodo, actualTask);
    }

    @Test
    public void parseTask_validDeadlineDescription_returnsDeadline() throws Exception {
        Deadline expectedDeadline = new Deadline("Submit assignment", VALID_DEADLINE_DATE);
        Task actualTask = ParserUtil.parseTask(VALID_DEADLINE_DESCRIPTION);
        assertEquals(expectedDeadline, actualTask);
    }

    @Test
    public void parseTask_validEventDescription_returnsEvent() throws Exception {
        Event expectedEvent = new Event("Conference", VALID_EVENT_START_DATE, VALID_EVENT_END_DATE);
        Task actualTask = ParserUtil.parseTask(VALID_EVENT_DESCRIPTION);
        assertEquals(expectedEvent, actualTask);
    }

    @Test
    public void parseTasks_collectionWithValidTasks_returnsTaskSet() throws Exception {
        Set<Task> actualTaskSet = ParserUtil.parseTasks(
                Arrays.asList(VALID_TODO_DESCRIPTION, VALID_DEADLINE_DESCRIPTION, VALID_EVENT_DESCRIPTION));

        Set<Task> expectedTaskSet = new HashSet<>(
                Arrays.asList(
                        new Todo("Buy groceries"),
                        new Deadline("Submit assignment", VALID_DEADLINE_DATE),
                        new Event("Conference", VALID_EVENT_START_DATE, VALID_EVENT_END_DATE)
                )
        );
        assertEquals(expectedTaskSet, actualTaskSet);
    }

    @Test
    public void parseTasks_emptyCollection_returnsEmptySet() throws Exception {
        Set<Task> taskSet = ParserUtil.parseTasks(Arrays.asList());
        assertEquals(new HashSet<>(), taskSet);
    }

    @Test
    public void parseTasks_collectionWithInvalidTask_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseTasks(Arrays.asList(VALID_TODO_DESCRIPTION, INVALID_DEADLINE_FORMAT)));
    }

    @Test
    public void parseTask_invalidDeadlineDate_throwsParseException() {
        // Invalid month
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_DEADLINE_DATE_MONTH));

        // Invalid day
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_DEADLINE_DATE_DAY));

        // Invalid date string
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_DEADLINE_DATE_STRING));
    }

    @Test
    public void parseTask_invalidEventDates_throwsParseException() {
        // Invalid month
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_EVENT_DATE_MONTH));

        // Invalid day
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_EVENT_DATE_DAY));

        // Invalid date string
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_EVENT_DATE_STRING));

        // "from" date is after "to" date
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_EVENT_DATE_ORDER));
    }
}
