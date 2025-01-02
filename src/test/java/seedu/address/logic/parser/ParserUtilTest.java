package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.tier.Tier;
import seedu.address.model.util.IncomeComparisonOperator;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_INCOME = "one thousand";
    private static final String INVALID_TIER = "#friend";
    private static final String INVALID_INCOME_COMPARISON_OPERATOR_1 = "==";
    private static final String INVALID_INCOME_COMPARISON_OPERATOR_2 = "!";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_INCOME = "1000";
    private static final String VALID_TIER_1 = "BRONZE";
    private static final String VALID_TIER_2 = "SILVER";
    private static final String VALID_INCOME_COMPARISON_OPERATOR_EQUAL = ">";
    private static final String VALID_INCOME_COMPARISON_OPERATOR_GREATER_THAN = ">";
    private static final String VALID_INCOME_COMPARISON_OPERATOR_LESS_THAN = "<";

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
        assertEquals(INDEX_FIRST_CLIENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_CLIENT, ParserUtil.parseIndex("  1  "));
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
    public void parseIncome_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIncome((String) null));
    }

    @Test
    public void parseIncome_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIncome(INVALID_INCOME));
    }

    @Test
    public void parseIncome_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Income expectedIncome = new Income(new BigInteger(VALID_INCOME));
        assertEquals(expectedIncome, ParserUtil.parseIncome(VALID_INCOME));
    }

    @Test
    public void parseIncome_validValueWithWhitespace_returnsTrimmedIncome() throws Exception {
        String incomeWithWhitespace = WHITESPACE + VALID_INCOME + WHITESPACE;
        Income expectedIncome = new Income(new BigInteger(VALID_INCOME));
        assertEquals(expectedIncome, ParserUtil.parseIncome(incomeWithWhitespace));
    }

    @Test
    public void parseTier_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTier(null));
    }

    @Test
    public void parseTier_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTier(INVALID_TIER));
    }

    @Test
    public void parseTier_validValueWithoutWhitespace_returnsTier() throws Exception {
        Tier expectedTier = new Tier(VALID_TIER_1);
        assertEquals(expectedTier, ParserUtil.parseTier(VALID_TIER_1));
    }

    @Test
    public void parseTier_validValueWithWhitespace_returnsTrimmedTier() throws Exception {
        String tierWithWhitespace = WHITESPACE + VALID_TIER_1 + WHITESPACE;
        Tier expectedTier = new Tier(VALID_TIER_1);
        assertEquals(expectedTier, ParserUtil.parseTier(tierWithWhitespace));
    }

    @Test
    public void parseIncomeComparisonOperator_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIncomeComparisonOperator(null));
    }

    @Test
    public void parseIncomeComparisonOperator_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseIncomeComparisonOperator(INVALID_INCOME_COMPARISON_OPERATOR_1));
        assertThrows(ParseException.class, () ->
                ParserUtil.parseIncomeComparisonOperator(INVALID_INCOME_COMPARISON_OPERATOR_2));
    }

    @Test
    public void parseIncomeComparisonOperator_validValueWithoutWhitespace_returnsIncomeComparisonOperator()
            throws Exception {
        IncomeComparisonOperator equalOperator = new IncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_EQUAL);
        IncomeComparisonOperator greaterThanOperator = new IncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_GREATER_THAN);
        IncomeComparisonOperator lessThanOperator = new IncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_LESS_THAN);

        assertEquals(equalOperator, ParserUtil.parseIncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_EQUAL));
        assertEquals(greaterThanOperator, ParserUtil.parseIncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_GREATER_THAN));
        assertEquals(lessThanOperator, ParserUtil.parseIncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_LESS_THAN));
    }

    @Test
    public void parseIncomeComparisonOperator_validValueWithWhitespace_returnsIncomeComparisonOperator()
            throws Exception {
        IncomeComparisonOperator equalOperator = new IncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_EQUAL);
        IncomeComparisonOperator greaterThanOperator = new IncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_GREATER_THAN);
        IncomeComparisonOperator lessThanOperator = new IncomeComparisonOperator(
                VALID_INCOME_COMPARISON_OPERATOR_LESS_THAN);

        assertEquals(equalOperator, ParserUtil.parseIncomeComparisonOperator(
                WHITESPACE + VALID_INCOME_COMPARISON_OPERATOR_EQUAL + WHITESPACE));
        assertEquals(greaterThanOperator, ParserUtil.parseIncomeComparisonOperator(
                WHITESPACE + VALID_INCOME_COMPARISON_OPERATOR_GREATER_THAN + WHITESPACE));
        assertEquals(lessThanOperator, ParserUtil.parseIncomeComparisonOperator(
                WHITESPACE + VALID_INCOME_COMPARISON_OPERATOR_LESS_THAN + WHITESPACE));
    }

}
