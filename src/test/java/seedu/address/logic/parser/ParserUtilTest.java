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
import seedu.address.model.person.Address;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProjectStatus;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "-651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PROJECT_STATUS = "unknown";
    private static final String INVALID_PAYMENT_STATUS = "unknown";
    private static final String INVALID_CLIENT_STATUS = "referral";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_PROJECT_STATUS = "in progress";
    // private static final String VALID_PROJECT_STATUS_2 = "completed";
    private static final String VALID_PAYMENT_STATUS = "pending";
    // private static final String VALID_PAYMENT_STATUS_2 = "paid";
    private static final String VALID_CLIENT_STATUS = "active";
    private static final String VALID_CLIENT_STATUS_2 = "unresponsive";
    private static final String VALID_CLIENT_STATUS_3 = "potential";
    private static final String VALID_CLIENT_STATUS_4 = "old";

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
    public void parseProjectStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProjectStatus((String) null));
    }

    @Test
    public void parseProjectStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProjectStatus(INVALID_PROJECT_STATUS));
    }

    @Test
    public void parseProjectStatus_validValueWithoutWhitespace_returnsProjectStatus() throws Exception {
        ProjectStatus expectedStatus = new ProjectStatus(VALID_PROJECT_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseProjectStatus(VALID_PROJECT_STATUS));
    }

    @Test
    public void parseProjectStatus_validValueWithWhitespace_returnsTrimmedProjectStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_PROJECT_STATUS + WHITESPACE;
        ProjectStatus expectedStatus = new ProjectStatus(VALID_PROJECT_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseProjectStatus(statusWithWhitespace));
    }

    @Test
    public void parsePaymentStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePaymentStatus((String) null));
    }

    @Test
    public void parsePaymentStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePaymentStatus(INVALID_PAYMENT_STATUS));
    }

    @Test
    public void parsePaymentStatus_validValueWithoutWhitespace_returnsProjectStatus() throws Exception {
        PaymentStatus expectedStatus = new PaymentStatus(VALID_PAYMENT_STATUS);
        assertEquals(expectedStatus, ParserUtil.parsePaymentStatus(VALID_PAYMENT_STATUS));
    }

    @Test
    public void parsePaymentStatus_validValueWithWhitespace_returnsTrimmedProjectStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_PAYMENT_STATUS + WHITESPACE;
        PaymentStatus expectedStatus = new PaymentStatus(VALID_PAYMENT_STATUS);
        assertEquals(expectedStatus, ParserUtil.parsePaymentStatus(statusWithWhitespace));
    }

    @Test
    public void parseClientStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientStatus((String) null));
    }

    @Test
    public void parseClientStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientStatus(INVALID_CLIENT_STATUS));
    }

    @Test
    public void parseClientStatus_validValueWithoutWhitespace_returnsClientStatus() throws Exception {
        ClientStatus expectedStatus = new ClientStatus(VALID_CLIENT_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseClientStatus(VALID_CLIENT_STATUS));
    }

    @Test
    public void parseClientStatus_validValueWithWhitespace_returnsTrimmedClientStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_CLIENT_STATUS_2 + WHITESPACE;
        ClientStatus expectedStatus = new ClientStatus(VALID_CLIENT_STATUS_2);
        assertEquals(expectedStatus, ParserUtil.parseClientStatus(statusWithWhitespace));

        String activeStatusWithWhitespace = WHITESPACE + VALID_CLIENT_STATUS + WHITESPACE;
        ClientStatus expectedActiveStatus = new ClientStatus(VALID_CLIENT_STATUS);
        assertEquals(expectedActiveStatus, ParserUtil.parseClientStatus(activeStatusWithWhitespace));

        String potentialStatusWithWhitespace = WHITESPACE + VALID_CLIENT_STATUS_3 + WHITESPACE;
        ClientStatus expectedPotentialStatus = new ClientStatus(VALID_CLIENT_STATUS_3);
        assertEquals(expectedPotentialStatus, ParserUtil.parseClientStatus(potentialStatusWithWhitespace));

        String oldStatusWithWhitespace = WHITESPACE + VALID_CLIENT_STATUS_4 + WHITESPACE;
        ClientStatus expectedOldStatus = new ClientStatus(VALID_CLIENT_STATUS_4);
        assertEquals(expectedOldStatus, ParserUtil.parseClientStatus(oldStatusWithWhitespace));
    }

    @Test
    public void parseDeadline_validValues_returnsDeadline() throws Exception {
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_EASY) {
            Deadline expected = new Deadline(deadline);
            assertEquals(expected, ParserUtil.parseDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_DIFFERENT_DELIMITERS) {
            Deadline expected = new Deadline(deadline);
            assertEquals(expected, ParserUtil.parseDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_SHORT_STRINGS) {
            Deadline expected = new Deadline(deadline);
            assertEquals(expected, ParserUtil.parseDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_BORDER_VALUES) {
            Deadline expected = new Deadline(deadline);
            assertEquals(expected, ParserUtil.parseDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_MIXED) {
            Deadline expected = new Deadline(deadline);
            assertEquals(expected, ParserUtil.parseDeadline(deadline));
        }
    }

    @Test
    public void parseDeadline_validValuesWithWhitespace_returnsDeadline() throws Exception {
        String[] deadlinesWithWhitespace = new String[] {
            "  10-10-2024",
            "10-10-2024  ",
            " 10/10/2024 ",
            "\t10|10|2024\n",
        };
        Deadline expected = new Deadline("10-10-2024");
        for (String deadline : deadlinesWithWhitespace) {
            assertEquals(expected, ParserUtil.parseDeadline(deadline));
        }
    }

}
