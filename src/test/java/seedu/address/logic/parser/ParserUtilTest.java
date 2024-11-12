package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_AMOUNT_1 = "100.005";
    private static final String INVALID_AMOUNT_2 = "1,000";
    private static final String INVALID_AMOUNT_3 = "-1000000000.01";
    private static final String INVALID_AMOUNT_4 = "1000000000.01";
    private static final String INVALID_DATE_1 = "2024-10-32";
    private static final String INVALID_DATE_2 = "2024-13-12";
    private static final String INVALID_DATE_3 = "2024-1-12";
    private static final String INVALID_DATE_4 = "2024-09-31";
    private static final String INVALID_DATE_5 = "2023-02-29";
    private static final String INVALID_YEAR_MONTH = "2020-13";
    private static final String INVALID_YEAR_MONTH_2 = "0001-00";
    private static final String INVALID_YEAR_MONTH_3 = "11-2020";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_AMOUNT_1 = "100";
    private static final String VALID_AMOUNT_2 = "100.5";
    private static final String VALID_AMOUNT_3 = "100.55";
    private static final String VALID_AMOUNT_4 = "-1000000000";
    private static final String VALID_AMOUNT_5 = "1000000000";
    private static final String VALID_DATE_1 = "2024-10-30";
    private static final String VALID_DATE_2 = "0000-10-30";
    private static final String VALID_DATE_3 = "-0001-10-30";
    private static final String VALID_DATE_4 = "2024-02-29";
    private static final String VALID_YEAR_MONTH = "2020-12";
    private static final String VALID_YEAR_MONTH_2 = "0000-01";
    private static final String VALID_YEAR_MONTH_3 = "-0001-01";

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
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount(null));
    }
    @Test
    public void parseAmount_invalidValue_throwsParseException() {
        //amount has more than 3 decimal places
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT_1));

        //amount contains invalid symbol
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT_2));

        //amount is smaller than minimum amount
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT_3));

        //amount is larger than maximum amount
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT_4));
    }

    @Test
    public void parseAmount_validValueWithoutWhitespace_returnsDouble() throws ParseException {
        //amount with no decimal places
        assertEquals(Double.parseDouble(VALID_AMOUNT_1), ParserUtil.parseAmount(VALID_AMOUNT_1));

        //amount with 1 decimal place
        assertEquals(Double.parseDouble(VALID_AMOUNT_2), ParserUtil.parseAmount(VALID_AMOUNT_2));

        //amount with 2 decimal places
        assertEquals(Double.parseDouble(VALID_AMOUNT_3), ParserUtil.parseAmount(VALID_AMOUNT_3));

        //lower boundary value
        assertEquals(Double.parseDouble(VALID_AMOUNT_4), ParserUtil.parseAmount(VALID_AMOUNT_4));

        //upper boundary value
        assertEquals(Double.parseDouble(VALID_AMOUNT_5), ParserUtil.parseAmount(VALID_AMOUNT_5));
    }

    @Test
    public void parseAmount_validValueWithWhitespace_returnsDouble() throws Exception {
        String amountWithWhitespace = WHITESPACE + VALID_AMOUNT_1 + WHITESPACE;
        assertEquals(Double.parseDouble(VALID_AMOUNT_1), ParserUtil.parseAmount(amountWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }
    @Test
    public void parseDate_invalidValue_throwsParseException() {
        //day out of range [1, 31]
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_DATE_1));

        //month out of range [1, 12]
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_DATE_2));

        //incorrect format of YYYY-M-DD
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_DATE_3));

        //invalid date which does not exist
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_DATE_4));

        //invalid date which does not exist on non-leap years
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_DATE_5));
    }
    @Test
    public void parseDate_validValueWithoutWhitespace_returnsLocalDate() throws ParseException {
        //regular date
        assertEquals(LocalDate.parse(VALID_DATE_1, DateTimeUtil.DEFAULT_DATE_PARSER),
                ParserUtil.parseDate(VALID_DATE_1));

        //year 0000
        assertEquals(LocalDate.parse(VALID_DATE_2, DateTimeUtil.DEFAULT_DATE_PARSER),
                ParserUtil.parseDate(VALID_DATE_2));

        //negative year
        assertEquals(LocalDate.parse(VALID_DATE_3, DateTimeUtil.DEFAULT_DATE_PARSER),
                ParserUtil.parseDate(VALID_DATE_3));

        //valid date only on leap year
        assertEquals(LocalDate.parse(VALID_DATE_4, DateTimeUtil.DEFAULT_DATE_PARSER),
                ParserUtil.parseDate(VALID_DATE_4));
    }
    @Test
    public void parseDate_validValueWithWhitespace_returnsLocalDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE_1 + WHITESPACE;
        assertEquals(LocalDate.parse(VALID_DATE_1, DateTimeUtil.DEFAULT_DATE_PARSER
                        .withResolverStyle(ResolverStyle.STRICT)),
                ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseYearMonth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseYearMonth(null));
    }
    @Test
    public void parseYearMonth_invalidValue_throwsParseException() {
        // invalid month
        assertThrows(ParseException.class, () -> ParserUtil.parseYearMonth(INVALID_YEAR_MONTH));
        assertThrows(ParseException.class, () -> ParserUtil.parseYearMonth(INVALID_YEAR_MONTH_2));
        // wrong format
        assertThrows(ParseException.class, () -> ParserUtil.parseYearMonth(INVALID_YEAR_MONTH_3));
    }

    @Test
    public void parseYearMonth_validValueWithoutWhitespace_returnsYearMonth() throws Exception {
        assertEquals(YearMonth.parse(VALID_YEAR_MONTH), ParserUtil.parseYearMonth(VALID_YEAR_MONTH));
        assertEquals(YearMonth.parse(VALID_YEAR_MONTH_2), ParserUtil.parseYearMonth(VALID_YEAR_MONTH_2));
        assertEquals(YearMonth.parse(VALID_YEAR_MONTH_3), ParserUtil.parseYearMonth(VALID_YEAR_MONTH_3));
    }
    @Test
    public void parseYearMonth_validValueWithWhitespace_returnsTrimmedYearMonth() throws Exception {
        String yearMonthWithWhitespace = WHITESPACE + VALID_YEAR_MONTH + WHITESPACE;
        assertEquals(YearMonth.parse(VALID_YEAR_MONTH), ParserUtil.parseYearMonth(yearMonthWithWhitespace));
        String yearMonthWithWhitespace2 = WHITESPACE + VALID_YEAR_MONTH_2 + WHITESPACE;
        assertEquals(YearMonth.parse(VALID_YEAR_MONTH_2), ParserUtil.parseYearMonth(yearMonthWithWhitespace2));
        String yearMonthWithWhitespace3 = WHITESPACE + VALID_YEAR_MONTH_3 + WHITESPACE;
        assertEquals(YearMonth.parse(VALID_YEAR_MONTH_3), ParserUtil.parseYearMonth(yearMonthWithWhitespace3));
    }
}
