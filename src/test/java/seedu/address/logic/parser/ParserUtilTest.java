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
import seedu.address.model.group.Group;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentId;
public class ParserUtilTest {
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_MAJOR = " ";
    private static final String INVALID_NETID = "e12345";
    private static final String INVALID_GROUP =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ullamcorper sollicitudin sem, ut abc";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_STUDENTID = "A1234567P";
    private static final String VALID_MAJOR = "Computer Science";
    private static final String VALID_EMAIL = "e1234567@u.nus.edu";
    private static final String VALID_NETID = "e1234567";
    private static final String VALID_GROUP_1 = "group 1";
    private static final String VALID_GROUP_2 = "group 2";

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
    public void parseStudentId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudentId((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudentId(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsStudentId() throws Exception {
        StudentId expectedStudentId = new StudentId(VALID_STUDENTID);
        assertEquals(expectedStudentId, ParserUtil.parseStudentId(VALID_STUDENTID));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedStudentId() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_STUDENTID + WHITESPACE;
        StudentId expectedStudentId = new StudentId(VALID_STUDENTID);
        assertEquals(expectedStudentId, ParserUtil.parseStudentId(phoneWithWhitespace));
    }

    @Test
    public void parseMajor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((String) null));
    }

    @Test
    public void parseMajor_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMajor(INVALID_MAJOR));
    }

    @Test
    public void parseMajor_validValueWithoutWhitespace_returnsMajor() throws Exception {
        Major expectedAddress = Major.makeMajor(VALID_MAJOR);
        assertEquals(expectedAddress, ParserUtil.parseMajor(VALID_MAJOR));
    }

    @Test
    public void parseMajor_validValueWithWhitespace_returnsTrimmedMajor() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_MAJOR + WHITESPACE;
        Major expectedAddress = Major.makeMajor(VALID_MAJOR);
        assertEquals(expectedAddress, ParserUtil.parseMajor(addressWithWhitespace));
    }

    @Test
    public void parseNetId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNetId((String) null));
    }

    @Test
    public void parseNetId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNetId(INVALID_NETID));
    }

    @Test
    public void parseNetId_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = Email.makeEmail(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseNetId(VALID_NETID));
    }

    @Test
    public void parseNetId_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String netIdWithWhitespace = WHITESPACE + VALID_NETID + WHITESPACE;
        Email expectedEmail = Email.makeEmail(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseNetId(netIdWithWhitespace));
    }

    @Test
    public void parseGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroup(null));
    }

    @Test
    public void parseGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroup(INVALID_GROUP));
    }

    @Test
    public void parseGroup_validValueWithoutWhitespace_returnsGroup() throws Exception {
        Group expectedGroup = new Group(VALID_GROUP_1);
        assertEquals(expectedGroup, ParserUtil.parseGroup(VALID_GROUP_1));
    }

    @Test
    public void parseGroup_validValueWithWhitespace_returnsTrimmedGroup() throws Exception {
        String groupWithWhitespace = WHITESPACE + VALID_GROUP_1 + WHITESPACE;
        Group expectedGroup = new Group(VALID_GROUP_1);
        assertEquals(expectedGroup, ParserUtil.parseGroup(groupWithWhitespace));
    }

    @Test
    public void parseGroups_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroups(null));
    }

    @Test
    public void parseGroups_collectionWithInvalidGroups_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroups(Arrays.asList(VALID_GROUP_1, INVALID_GROUP)));
    }

    @Test
    public void parseGroups_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseGroups(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseGroups_collectionWithValidGroups_returnsGroupSet() throws Exception {
        GroupList actualGroupSet = ParserUtil.parseGroups(Arrays.asList(VALID_GROUP_1, VALID_GROUP_2));
        Set<Group> groupSet = new HashSet<>(Arrays.asList(new Group(VALID_GROUP_1), new Group(VALID_GROUP_2)));
        GroupList expectedGroupSet = new GroupList(groupSet);

        assertEquals(expectedGroupSet, actualGroupSet);
    }
}
