package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Days;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;


public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_COST_AMOUNT = "+123.12";
    private static final String INVALID_HOUR = "+0.75";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_COST_AMOUNT = "300.25";
    private static final String VALID_HOUR = "12.5";

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
        assertEquals(INDEX_FIRST_STUDENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_STUDENT, ParserUtil.parseIndex("  1  "));
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
    public void parseRate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRate((String) null));
    }

    @Test
    public void parseRate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRate(INVALID_COST_AMOUNT));
    }

    @Test
    public void parseRate_validValueWithoutWhitespace_returnsRate() throws Exception {
        Rate expectedRate = new Rate(VALID_COST_AMOUNT);
        assertEquals(expectedRate, ParserUtil.parseRate(VALID_COST_AMOUNT));
    }

    @Test
    public void parseRate_validValueWithWhitespace_returnsTrimmedRate() throws Exception {
        Rate expectedRate = new Rate(VALID_COST_AMOUNT);
        assertEquals(expectedRate, ParserUtil.parseRate(WHITESPACE + VALID_COST_AMOUNT));
    }

    @Test
    public void parseOwedAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOwedAmount((String) null));
    }

    @Test
    public void parseOwedAmount_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOwedAmount(INVALID_COST_AMOUNT));
    }

    @Test
    public void parseOwedAmount_validValueWithWhitespace_returnsTrimmedOwedAmount() throws Exception {
        OwedAmount expectedOwedAmount = new OwedAmount(VALID_COST_AMOUNT);
        assertEquals(expectedOwedAmount, ParserUtil.parseOwedAmount(VALID_COST_AMOUNT + WHITESPACE));
    }

    @Test
    public void parseOwedAmount_validValueWithoutWhitespace_returnsOwedAmount() throws Exception {
        OwedAmount expectedOwedAmount = new OwedAmount(VALID_COST_AMOUNT);
        assertEquals(expectedOwedAmount, ParserUtil.parseOwedAmount(VALID_COST_AMOUNT));
    }

    @Test
    public void parseHour_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHour((String) null));
    }

    @Test
    public void parseHour_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseHour(INVALID_HOUR));
    }

    @Test
    public void parseHour_validValueWithourWhitespace_returnsHour() throws Exception {
        double expectedHour = Double.parseDouble(VALID_HOUR);
        assertEquals(expectedHour, ParserUtil.parseHour(VALID_HOUR));
    }

    @Test
    public void parseHour_validValueWithWhitespace_returnsTrimmedHour() throws Exception {
        String hourWithWhitespace = WHITESPACE + VALID_HOUR + WHITESPACE;
        double expectedHour = Double.parseDouble(VALID_HOUR);
        assertEquals(expectedHour, ParserUtil.parseHour(hourWithWhitespace));
    }
    @Test
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount(null));
    }

    @Test
    public void parseAmount_invalidValue_throwsParseException() {
        // Invalid values: negative numbers, zero, and non-numeric values
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount("-10.00")); // negative number
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount("0")); // zero
        assertThrows(NumberFormatException.class, () -> ParserUtil.parseAmount("abc")); // non-numeric value
    }

    @Test
    public void parseAmount_validValueWithoutWhitespace_returnsAmount() throws Exception {
        // Valid amount with no whitespace
        double expectedAmount = 10.00;
        assertEquals(expectedAmount, ParserUtil.parseAmount("10.00"), 0.001);
    }

    @Test
    public void parseAmount_validValueWithWhitespace_returnsTrimmedAmount() throws Exception {
        // Valid amount with leading and trailing whitespace
        double expectedAmount = 10.00;
        String amountWithWhitespace = "  10.00  ";
        assertEquals(expectedAmount, ParserUtil.parseAmount(amountWithWhitespace), 0.001);
    }

    @Test
    public void parseDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDay((String) null));
    }

    @Test
    public void parseDay_emptyString_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ParserUtil.parseDay(""));
    }

    @Test
    public void parseDay_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay("Monday!"));
    }

    @Test
    public void parseDay_validValueWithoutWhitespace_returnsDay() throws Exception {
        Days expectedDay = Days.valueOf("MONDAY");
        assertEquals(expectedDay, ParserUtil.parseDay("MONDAY   "));
    }

    @Test
    public void parseNameStrings_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNameStrings(null));
    }

    @Test
    public void parseNameStrings_isEmpty_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ParserUtil.parseNameStrings(List.of()));
    }

    @Test
    public void parseNameStrings_containsEmptyString_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ParserUtil.parseNameStrings(List.of("Alice", "")));
    }


    @Test
    public void parseNameStrings_collectionWithInvalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNameStrings(List.of("Alice!", "Bob", INVALID_NAME)));
    }

    @Test
    public void parseNameStrings_collectionWithValidValue_returnsNameSet() throws Exception {
        Set<String> expectedNameSet = Set.of("Alice", "Bob");
        assertEquals(expectedNameSet, ParserUtil.parseNameStrings(List.of("Alice", "Bob")));
    }

    @Test
    public void parseDays_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDays(null));
    }

    @Test
    public void parseDays_isEmpty_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ParserUtil.parseDays(List.of("")));
    }

    @Test
    public void parseDays_containsEmptyString_throwsAssertionError() {
        assertThrows(AssertionError.class, () ->
                ParserUtil.parseDays(List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "")));
    }

    @Test
    public void parseDays_collectionWithInvalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDays(
                List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAYs", "SATURDAY", "SUNDAY")));
    }

    @Test
    public void parseDays_collectionWithValidValue_returnsDaySet() throws Exception {
        Set<Days> expectedDaySet = Set.of(
                Days.MONDAY, Days.TUESDAY, Days.WEDNESDAY, Days.THURSDAY, Days.FRIDAY, Days.SATURDAY, Days.SUNDAY);
        assertEquals(expectedDaySet, ParserUtil.parseDays(
                List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")));
    }

}
