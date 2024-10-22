package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_POLICY_TYPE = "live";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_POLICY_TYPE_LIFE = "life";
    private static final String VALID_POLICY_TYPE_HEALTH = "health";
    private static final String VALID_POLICY_TYPE_EDUCATION = "education";
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
    public void parsePolicyType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePolicyType(null));
    }

    @Test
    public void parsePolicyType_validPolicyWithoutWhitespace_returnsPolicy() throws Exception {
        assertEquals(PolicyType.LIFE, ParserUtil.parsePolicyType(VALID_POLICY_TYPE_LIFE));
    }

    @Test
    public void parsePolicyType_validValueWithWhitespace_returnsPolicy() throws Exception {
        String policyWithWhitespace = WHITESPACE + VALID_POLICY_TYPE_LIFE + WHITESPACE;
        assertEquals(PolicyType.LIFE, ParserUtil.parsePolicyType(policyWithWhitespace));
    }

    @Test
    public void parsePolicyType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyType(INVALID_POLICY_TYPE));
    }

    @Test
    public void parsePolicyTypes_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePolicyTypes(null));
    }

    @Test
    public void parsePolicyTypes_collectionWithInvalidPolicies_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyTypes(
                Arrays.asList(VALID_POLICY_TYPE_LIFE, INVALID_POLICY_TYPE)));
    }

    @Test
    public void parsePolicyTypes_emptyCollection_throwsParseException() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyTypes(Collections.emptyList()));
    }

    @Test
    public void parsePolicyTypes_collectionWithValidPolicies_returnsSet() throws Exception {
        Set<PolicyType> actual = ParserUtil.parsePolicyTypes(Arrays.asList(VALID_POLICY_TYPE_LIFE,
                VALID_POLICY_TYPE_HEALTH, VALID_POLICY_TYPE_EDUCATION));
        Set<PolicyType> expected = new HashSet<>();
        expected.add(PolicyType.LIFE);
        expected.add(PolicyType.HEALTH);
        expected.add(PolicyType.EDUCATION);
        assertEquals(expected, actual);
    }

    @Test
    public void parsePolicyTypes_collectionWithDuplicatePolicies_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyTypes(
                Arrays.asList(VALID_POLICY_TYPE_LIFE, VALID_POLICY_TYPE_LIFE)));
    }

    @Test
    public void parsePremiumAmount_validValueWithoutWhitespace_returnsPremiumAmount() throws ParseException {
        String amount = "200.0";
        PremiumAmount expected = new PremiumAmount(amount);
        PremiumAmount actual = ParserUtil.parsePremiumAmount(amount);

        assertEquals(expected, actual);
    }

    @Test
    public void parsePremiumAmount_validValueWithWhitespace_returnsPremiumAmount() throws ParseException {
        String amount = "200.0";
        PremiumAmount expected = new PremiumAmount(amount);
        PremiumAmount actual = ParserUtil.parsePremiumAmount(WHITESPACE + amount + WHITESPACE);

        assertEquals(expected, actual);
    }

    @Test
    public void parsePremiumAmount_null_throwsNullPointerException() throws ParseException {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePremiumAmount(null));
    }

    @Test
    public void parsePremiumAmount_emptyString_returnsNull() throws ParseException {
        assertNull(ParserUtil.parsePremiumAmount(""));
    }

    @Test
    public void parsePremiumAmount_negativeValue_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parsePremiumAmount("-1"));
    }

    @Test
    public void parsePremiumAmount_moreThanTwoDecimalPlaces_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parsePremiumAmount("1.555"));
    }

    @Test
    public void parsePremiumAmount_nonNumerals_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parsePremiumAmount("foo"));
    }

    @Test
    public void parseCoverageAmount_validValueWithoutWhitespace_returnsCoverageAmount() throws ParseException {
        String amount = "200.0";
        CoverageAmount expected = new CoverageAmount(amount);
        CoverageAmount actual = ParserUtil.parseCoverageAmount(amount);

        assertEquals(expected, actual);
    }

    @Test
    public void parseCoverageAmount_validValueWithWhitespace_returnsCoverageAmount() throws ParseException {
        String amount = "200.0";
        CoverageAmount expected = new CoverageAmount(amount);
        CoverageAmount actual = ParserUtil.parseCoverageAmount(WHITESPACE + amount + WHITESPACE);

        assertEquals(expected, actual);
    }

    @Test
    public void parseCoverageAmount_null_throwsNullPointerException() throws ParseException {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCoverageAmount(null));
    }

    @Test
    public void parseCoverageAmount_emptyString_returnsNull() throws ParseException {
        assertNull(ParserUtil.parseCoverageAmount(""));
    }

    @Test
    public void parseCoverageAmount_negativeValue_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseCoverageAmount("-1"));
    }

    @Test
    public void parseCoverageAmount_moreThanTwoDecimalPlaces_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseCoverageAmount("1.555"));
    }

    @Test
    public void parseCoverageAmount_nonNumerals_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseCoverageAmount("foo"));
    }

    @Test
    public void parseExpiryDate_validValueWithWhitespace_returnExpiryDate() throws ParseException {
        String expiryDate = "12/23/2024";
        ExpiryDate expected = new ExpiryDate(expiryDate);
        ExpiryDate actual = ParserUtil.parseExpiryDate(expiryDate);

        assertEquals(expected, actual);
    }

    @Test
    public void parseExpiryDate_validValueWithoutWhitespace_returnExpiryDate() throws ParseException {
        String expiryDate = "12/23/2024";
        ExpiryDate expected = new ExpiryDate(expiryDate);
        ExpiryDate actual = ParserUtil.parseExpiryDate(WHITESPACE + expiryDate + WHITESPACE);

        assertEquals(expected, actual);
    }

    @Test
    public void parseExpiryDate_invalidValue_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate("99/99/9999"));
    }
}
