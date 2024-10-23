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
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
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

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ROLE = "Software Engineer";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SKILL_1 = "Python";
    private static final String VALID_SKILL_2 = "C";
    private static final String VALID_BILLING_DATE = "25";

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
    public void parseSkill_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills((String) null));
    }

    @Test
    public void parseSkill_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(INVALID_SKILL));
    }

    @Test
    public void parseSkill_validValueWithoutWhitespace_returnsTag() throws Exception {
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkills(VALID_SKILL_1));
    }

    @Test
    public void parseSkill_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_SKILL_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_SKILL_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseSkills_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills((String) null));
    }

    @Test
    public void parseSkills_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, INVALID_SKILL)));
    }

    @Test
    public void parseSkills_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSkills(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSkills_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Skill> actualTagSet = ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, VALID_SKILL_2));
        Set<Skill> expectedTagSet =
                new HashSet<Skill>(Arrays.asList(new Skill(VALID_SKILL_1), new Skill(VALID_SKILL_2)));
        assertEquals(expectedTagSet, actualTagSet);
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
}
