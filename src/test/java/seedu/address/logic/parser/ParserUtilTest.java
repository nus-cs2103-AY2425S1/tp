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
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_EMPLOYEE_ID = "123b";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SKILL = "coding!";

    private static final String VALID_EMPLOYEE_ID = "1230";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_SKILL_1 = "programming";
    private static final String VALID_SKILL_2 = "writing";

    private static final String INVALID_PROJECT_ID = "4252@$g";
    private static final String INVALID_PROJECT_NAME = "Prj R@chel";

    private static final String VALID_PROJECT_ID = "A0276123K";
    private static final String VALID_PROJECT_NAME = "Project Alpha";

    private static final String VALID_ASSIGNMENT_ID = "1";

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
    public void parseEmployeeId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmployeeId((String) null));
    }

    @Test
    public void parseEmployeeId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmployeeId(INVALID_EMPLOYEE_ID));
    }

    @Test
    public void parseEmployeeId_validValueWithoutWhitespace_returnsEmployeeId() throws Exception {
        EmployeeId expectedEmployeeId = new EmployeeId(VALID_EMPLOYEE_ID);
        assertEquals(expectedEmployeeId, ParserUtil.parseEmployeeId(VALID_EMPLOYEE_ID));
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
    public void parseSkill_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkill(null));
    }

    @Test
    public void parseSkill_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkill(INVALID_SKILL));
    }

    @Test
    public void parseSkill_validValueWithoutWhitespace_returnsSkill() throws Exception {
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(VALID_SKILL_1));
    }

    @Test
    public void parseSkill_validValueWithWhitespace_returnsTrimmedSkill() throws Exception {
        String skillWithWhitespace = WHITESPACE + VALID_SKILL_1 + WHITESPACE;
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(skillWithWhitespace));
    }

    @Test
    public void parseSkills_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills(null));
    }

    @Test
    public void parseSkills_collectionWithInvalidSkills_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, INVALID_SKILL)));
    }

    @Test
    public void parseSkills_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSkills(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSkills_collectionWithValidSkills_returnsSkillSet() throws Exception {
        Set<Skill> actualSkillSet = ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, VALID_SKILL_2));
        Set<Skill> expectedSkillSet = new HashSet<Skill>(Arrays.asList(new Skill(VALID_SKILL_1),
                new Skill(VALID_SKILL_2)));

        assertEquals(expectedSkillSet, actualSkillSet);
    }

    @Test
    public void parseProjectId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProjectId((String) null));
    }

    @Test
    public void parseProjectId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProjectId(INVALID_PROJECT_ID));
    }

    @Test
    public void parseProjectId_validValueWithoutWhitespace_returnsProjectId() throws Exception {
        ProjectId expectedProjectId = new ProjectId(VALID_PROJECT_ID);
        assertEquals(expectedProjectId, ParserUtil.parseProjectId(VALID_PROJECT_ID));
    }

    @Test
    public void parseProjectName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProjectName((String) null));
    }

    @Test
    public void parseProjectName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProjectName(INVALID_PROJECT_NAME));
    }

    @Test
    public void parseProjectName_validValueWithoutWhitespace_returnsProjectName() throws Exception {
        ProjectName expectedProjectName = new ProjectName(VALID_PROJECT_NAME);
        assertEquals(expectedProjectName, ParserUtil.parseProjectName(VALID_PROJECT_NAME));
    }

    @Test
    public void parseProjectName_validValueWithWhitespace_returnsTrimmedProjectName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_PROJECT_NAME + WHITESPACE;
        ProjectName expectedProjectName = new ProjectName(VALID_PROJECT_NAME);
        assertEquals(expectedProjectName, ParserUtil.parseProjectName(nameWithWhitespace));
    }

    @Test
    public void parseAssignmentId_validValue_returnTrimmedAssignmentId() throws Exception {
        AssignmentId expectedAssignmentId = new AssignmentId(VALID_ASSIGNMENT_ID);
        assertEquals(expectedAssignmentId, ParserUtil.parseAssignmentId(VALID_ASSIGNMENT_ID));
    }
}
