package seedu.hireme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.hireme.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.Name;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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
        assertEquals(INDEX_FIRST_INTERNSHIP_APPLICATION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_INTERNSHIP_APPLICATION, ParserUtil.parseIndex("  1  "));
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

    //    @Test
    //    public void parseAddress_null_throwsNullPointerException() {
    //        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    //    }
    //
    //    @Test
    //    public void parseAddress_invalidValue_throwsParseException() {
    //        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    //    }
    //
    //    @Test
    //    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
    //        Address expectedAddress = new Address(VALID_ADDRESS);
    //        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    //    }

    //    @Test
    //    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
    //        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
    //        Address expectedAddress = new Address(VALID_ADDRESS);
    //        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    //    }

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

}
