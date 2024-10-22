package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_POLICY_LIFE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_AMOUNT_DUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
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
    private static final String VALID_POLICY_NAME = "life insurance";
    private static final String VALID_DATE_1 = "2024-10-01";
    private static final String VALID_DATE_2 = "2025-11-11";

    private static final String WHITESPACE = " \t\r\n";
    private static final String CUSTOM_ERROR_MESSAGE = "Custom error message";

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
    public void parseName_nullCustomErrorMessage_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null, CUSTOM_ERROR_MESSAGE));
    }

    @Test
    public void parseName_invalidValueCustomErrorMessage_throwsParseException() {
        assertThrows(ParseException.class,
                CUSTOM_ERROR_MESSAGE, () -> ParserUtil.parseName(INVALID_NAME, CUSTOM_ERROR_MESSAGE));
    }

    @Test
    public void parseName_validValueWithoutWhitespaceCustomErrorMessage_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME, CUSTOM_ERROR_MESSAGE));
    }

    @Test
    public void parseName_validValueWithWhitespaceCustomErrorMessage_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace, CUSTOM_ERROR_MESSAGE));
    }

    @Test
    public void parsePolicy_invalidArgumentWithNoEndDate_throwsParseException() {
        String policyArgumentWithNoEndDate = "1 " + PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1;

        assertThrows(ParseException.class, () -> ParserUtil.parsePolicy(policyArgumentWithNoEndDate));
    }

    @Test
    public void parsePolicy_invalidArgumentWithEndDateEarlierThanStartDate_throwsParseException() {
        String policyArgumentWithNoEndDate = "1 " + PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_2 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_1;

        assertThrows(ParseException.class, () -> ParserUtil.parsePolicy(policyArgumentWithNoEndDate));
    }

    @Test
    public void parsePolicy_invalidArgumentWithEmptyPolicyName_throwsParseException() {
        String policyArgumentWithNoEndDate = "1 " + PREFIX_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_2;

        assertThrows(ParseException.class, () -> ParserUtil.parsePolicy(policyArgumentWithNoEndDate));
    }

    @Test
    public void parsePolicy_validArgument_returnsPolicy() throws ParseException {
        String validPolicyArgument = "1 " + PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " " + PREFIX_NEXT_PAYMENT_DATE
                + VALID_INSURANCE_PAYMENT_DATE + " " + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;

        Policy expectedPolicy = new Policy(VALID_POLICY_NAME, VALID_DATE_1, VALID_DATE_2, VALID_INSURANCE_PAYMENT);

        assertEquals(expectedPolicy, ParserUtil.parsePolicy(validPolicyArgument));
    }

    @Test
    public void parsePolicies_invalidIndex_throwsParseException() throws ParseException {
        String policyArgumentWithInvalidIndex = "c " + PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " "
                + PREFIX_NEXT_PAYMENT_DATE + VALID_INSURANCE_PAYMENT_DATE + " "
                + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;

        Collection<String> policies = List.of(policyArgumentWithInvalidIndex, EDIT_POLICY_LIFE_1);

        assertThrows(ParseException.class, () -> ParserUtil.parsePolicies(policies));
    }

    @Test
    public void parsePolicies_invalidDuplicateIndex_throwsParseException() throws ParseException {
        String policyArgumentWithDuplicateIndex = "1 " + PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " "
                + PREFIX_NEXT_PAYMENT_DATE + VALID_INSURANCE_PAYMENT_DATE + " "
                + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;

        Collection<String> policies = List.of(EDIT_POLICY_LIFE_1, policyArgumentWithDuplicateIndex);

        assertThrows(ParseException.class, () -> ParserUtil.parsePolicies(policies));
    }
    @Test
    public void parsePolicies_invalidArgumentWithNoName_throwsParseException() throws ParseException {
        String policyArgumentWithNoName = "2 "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " "
                + PREFIX_NEXT_PAYMENT_DATE + VALID_INSURANCE_PAYMENT_DATE + " "
                + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;

        Collection<String> policies = List.of(policyArgumentWithNoName, EDIT_POLICY_LIFE_1);

        assertThrows(ParseException.class, () -> ParserUtil.parsePolicies(policies));
    }

    @Test
    public void parsePolicies_validArgument_returnIndexPolicyMap() throws ParseException {
        String validPolicyArgument = "1 " + PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
                + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " " + PREFIX_NEXT_PAYMENT_DATE
                + VALID_INSURANCE_PAYMENT_DATE + " " + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;


        Collection<String> policies = List.of(validPolicyArgument);

        Policy expectedPolicy = new Policy(VALID_POLICY_NAME, VALID_DATE_1, VALID_DATE_2, VALID_INSURANCE_PAYMENT);
        Index expectedIndex = Index.fromOneBased(1);

        Map<Index, Policy> policyMap = ParserUtil.parsePolicies(policies);

        Set<Map.Entry<Index, Policy>> policyEntrySet = policyMap.entrySet();

        Map.Entry<Index, Policy> policyEntry = policyEntrySet.iterator().next();

        assertEquals(expectedIndex.getZeroBased(), policyEntry.getKey().getZeroBased());
        assertEquals(expectedPolicy, policyEntry.getValue());
    }
}
