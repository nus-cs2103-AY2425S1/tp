package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobSalary;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ROLE = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SKILL = "#friend";
    private static final String INVALID_BILLING_DATE = "30";
    private static final String INVALID_COMPANY = "@Google";
    private static final String INVALID_SALARY = "$700"; // $ not allowed
    private static final String INVALID_TAG = "#Matlab";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ROLE = "Software Engineer";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SKILL_1 = "Python";
    private static final String VALID_SKILL_2 = "C";
    private static final String VALID_BILLING_DATE = "25";
    private static final String VALID_COMPANY = "Google";
    private static final String VALID_SALARY = "100";
    private static final String VALID_TAG_1 = "Matlab";
    private static final String VALID_TAG_2 = "Conda";

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
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
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
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ROLE);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        Address expectedAddress = new Address(VALID_ROLE);
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
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany(null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany(INVALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        JobCompany expectedCompany = new JobCompany(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        JobCompany expectedCompany = new JobCompany(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseSalary_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSalary(null));
    }

    @Test
    public void parseSalary_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSalary(INVALID_SALARY));
    }

    @Test
    public void parseSalary_validValueWithoutWhitespace_returnsSalary() throws Exception {
        JobSalary expectedSalary = new JobSalary(VALID_SALARY);
        assertEquals(expectedSalary, ParserUtil.parseSalary(VALID_SALARY));
    }

    @Test
    public void parseSalary_validValueWithWhitespace_returnsTrimmedSalary() throws Exception {
        String salaryWithWhitespace = WHITESPACE + VALID_SALARY + WHITESPACE;
        JobSalary expectedSalary = new JobSalary(VALID_SALARY);
        assertEquals(expectedSalary, ParserUtil.parseSalary(salaryWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidTag_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validTagWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validTagWithWhitespace_returnsTrimmedTag() throws Exception {
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
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseSkill_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills((String) null));
    }

    @Test
    public void parseSkill_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(INVALID_SKILL));
    }

    @Test
    public void parseSkill_validValueWithoutWhitespace_returnsSkill() throws Exception {
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkills(VALID_SKILL_1));
    }

    @Test
    public void parseSkill_validValueWithWhitespace_returnsTrimmedSkill() throws Exception {
        String skillWithWhitespace = WHITESPACE + VALID_SKILL_1 + WHITESPACE;
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkills(skillWithWhitespace));
    }

    @Test
    public void parseSkills_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills((String) null));
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
        Set<Skill> expectedSkillSet =
                new HashSet<Skill>(Arrays.asList(new Skill(VALID_SKILL_1), new Skill(VALID_SKILL_2)));
        assertEquals(expectedSkillSet, actualSkillSet);
    }

    @Test
    public void parseBillingDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBillingDate(null));
    }

    @Test
    public void parseBillingDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBillingDate(INVALID_BILLING_DATE));
    }

    @Test
    public void parseBillingDate_validDateWithoutWhitespace_returnsDate() throws Exception {
        BillingDate expectedDate = new BillingDate(VALID_BILLING_DATE);
        assertEquals(expectedDate, ParserUtil.parseBillingDate(VALID_BILLING_DATE));
    }

    @Test
    public void parseBillingDate_validDateWithWhitespace_returnsDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_BILLING_DATE + WHITESPACE;
        BillingDate expectedDate = new BillingDate(VALID_BILLING_DATE);
        assertEquals(expectedDate, ParserUtil.parseBillingDate(dateWithWhitespace));
    }

    @Test
    public void parseRequiredNumberOfArguments_validInput_returnSplitArgs() throws Exception {
        String inputString = WHITESPACE + "1" + WHITESPACE + "2" + WHITESPACE;
        int numberOfArguments = 2;
        String usageMessage = "sample usage message";

        String[] expectedOutput = {"1", "2"};
        String[] actualOutput = ParserUtil.parseRequiredNumberOfArguments(inputString, numberOfArguments, usageMessage);

        assertEquals(numberOfArguments, expectedOutput.length);
        assertEquals(expectedOutput.length, actualOutput.length);
        for (int i = 0; i < numberOfArguments; i++) {
            assertEquals(expectedOutput[i], actualOutput[i]);
        }
    }

    @Test
    public void parseRequiredNumberOfArguments_incorrectNumberOfArguments_throwException() throws Exception {
        String inputString = WHITESPACE + "1" + WHITESPACE + "2" + WHITESPACE;
        int numberOfArguments = 3;
        String usageMessage = "sample usage message";

        assertThrows(ParseException.class, ()
                -> ParserUtil.parseRequiredNumberOfArguments(inputString, numberOfArguments, usageMessage));
    }

    @Test
    public void parseRequiredNumberOfArguments_emptyInput_throwException() throws Exception {
        int numberOfArguments = 0;
        String usageMessage = "sample usage message";

        assertThrows(ParseException.class, ()
                -> ParserUtil.parseRequiredNumberOfArguments(WHITESPACE, numberOfArguments, usageMessage));
    }

    @Test
    public void parseEntity_validEntity_noExceptionsThrown() throws Exception {
        assertEquals("contact", ParserUtil.parseEntity("contact"));
        assertEquals("job", ParserUtil.parseEntity("job"));
        assertEquals("company", ParserUtil.parseEntity("company"));
        assertEquals("all", ParserUtil.parseEntity("all"));

        // Different case
        assertEquals("contact", ParserUtil.parseEntity("CoNtAcT"));
        assertEquals("job", ParserUtil.parseEntity("jOb"));
        assertEquals("company", ParserUtil.parseEntity("COMPany"));
        assertEquals("all", ParserUtil.parseEntity("aLL"));
    }

    @Test
    public void parseEntity_invalidEntity_throwParseException() throws Exception {
        assertThrows(ParseException.class, ()
                ->ParserUtil.parseEntity("someInvalidEntity"));
    }

    @Test
    public void parseEntity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEntity(null));
    }
}
