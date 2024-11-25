package seedu.hireme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.hireme.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;
import seedu.hireme.model.internshipapplication.Status;

public class ParserUtilTest {
    public static final String INVALID_COMPANY_NAME = "!!";
    public static final String INVALID_EMAIL = "example.com";
    public static final String INVALID_SORTING_ORDER = "??";
    public static final String INVALID_ROLE = ".";
    public static final String INVALID_DATE = "4 Sep 1998";
    public static final String INVALID_STATUS = "bad-status";

    public static final String VALID_NAME = "Rachel Walker";
    public static final String VALID_EMAIL = "rachel@example.com";
    public static final String VALID_SORTING_ORDER = "earliest";
    public static final String VALID_COMPANY_NAME = GOOGLE.getCompany().getName().toString();
    public static final String VALID_COMPANY_EMAIL = GOOGLE.getCompany().getEmail().toString();
    public static final String VALID_ROLE = GOOGLE.getRole().toString();
    public static final String VALID_DATE = GOOGLE.getDateOfApplication().toString();
    public static final String VALID_STATUS = GOOGLE.getStatus().toString();
    public static final String WHITESPACE = " \t\r\n";

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
        assertEquals(INDEX_FIRST_INTERNSHIP_APPLICATION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_INTERNSHIP_APPLICATION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_COMPANY_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_COMPANY_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_COMPANY_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_COMPANY_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_COMPANY_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_COMPANY_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_COMPANY_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_COMPANY_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_COMPANY_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test

    public void parseSortingOrder_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortingOrder(null));
    }

    @Test
    public void parseSortingOrder_emptyValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortingOrder(""));
    }

    @Test
    public void parseSortingOrder_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortingOrder(INVALID_SORTING_ORDER));
    }

    @Test
    public void parseSortingOrder_invalidNumberOfArguments_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortingOrder(VALID_SORTING_ORDER + " " + "extra"));
    }

    @Test
    public void parseSortingOrder_validValueWithoutWhitespace_returnsSortingOrder() throws Exception {
        assertDoesNotThrow(() -> AssertionError.class);
        assertEquals(true, ParserUtil.parseSortingOrder(VALID_SORTING_ORDER));
    }

    @Test
    public void parseSortingOrder_validValueWithWhitespace_returnsSortingOrder() throws Exception {
        String sortingOrderWithWhitespace = WHITESPACE + VALID_SORTING_ORDER + WHITESPACE;
        assertDoesNotThrow(() -> AssertionError.class);
        assertEquals(true, ParserUtil.parseSortingOrder(sortingOrderWithWhitespace));
    }

    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));
    }

    @Test
    public void parseStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(INVALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithoutWhitespace_returnsDate() throws Exception {
        Status expectedStatus = Status.getValueOf(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(VALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        Status expectedStatus = Status.getValueOf(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(statusWithWhitespace));
    }

}
