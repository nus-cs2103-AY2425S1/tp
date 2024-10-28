package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    public static final String INVALID_DATE = "31 December 2024";
    public static final String INVALID_VENUE = "";
    public static final String INVALID_CLIENT_INDEX = "-1";
    public static final String INVALID_CLIENT_NAME = "@my";
    public static final Collection<String> INVALID_LIST_OF_JOBS = new LinkedList<>();

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "92345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    public static final String VALID_DATE = "2024-12-31";
    public static final String VALID_VENUE = "Chijmes";
    public static final String VALID_CLIENT_INDEX = "1";
    public static final String VALID_CLIENT_NAME = "Amy";
    public static final Collection<String> VALID_LIST_OF_JOBS_WITHOUT_WHITESPACE = new LinkedList<>();

    public static final Collection<String> VALID_LIST_OF_JOBS_WITH_WHITESPACE = new LinkedList<>();

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
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Optional<Role> expectedTag = Optional.of(new Role(VALID_TAG_1));
        assertEquals(expectedTag, ParserUtil.parseRole(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Optional<Role> expectedTag = Optional.of(new Role(VALID_TAG_1));
        assertEquals(expectedTag, ParserUtil.parseRole(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_TAG));
    }

    @Test
    public void parseTags_emptyRole_throwsParseException() throws Exception {
        String emptyTag = "";
        Optional<Role> expectedTag = Optional.empty();
        assertEquals(expectedTag, ParserUtil.parseRole(emptyTag));
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Optional<Role> actualTagSet = ParserUtil.parseRole(VALID_TAG_1);
        Optional<Role> expectedTagSet = Optional.of(new Role(VALID_TAG_1));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
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
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseVenue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue((String) null));
    }

    @Test
    public void parseVenue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVenue(INVALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithoutWhitespace_returnsVenue() throws Exception {
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(VALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithWhitespace_returnsTrimmedVenue() throws Exception {
        String venueWithWhitespace = WHITESPACE + VALID_VENUE + WHITESPACE;
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(venueWithWhitespace));
    }

    @Test
    public void parseClient_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClient((String) null));
    }

    @Test
    public void parseClient_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClient(INVALID_CLIENT_INDEX));
        assertThrows(ParseException.class, () -> ParserUtil.parseClient(INVALID_CLIENT_NAME));
    }

    @Test
    public void parseClient_validValueWithoutWhitespace_returnsStringClient() throws Exception {
        String expectedStringClientIndex = VALID_CLIENT_INDEX;
        assertEquals(expectedStringClientIndex, ParserUtil.parseClient(VALID_CLIENT_INDEX));

        String expectedStringClientName = VALID_CLIENT_NAME;
        assertEquals(expectedStringClientName, ParserUtil.parseClient(VALID_CLIENT_NAME));
    }

    @Test
    public void parseClient_validValueWithWhitespace_returnsTrimmedStringClient() throws Exception {
        String clientIndexWithWhitespace = WHITESPACE + VALID_CLIENT_INDEX + WHITESPACE;
        String expectedClientIndex = VALID_CLIENT_INDEX;
        assertEquals(expectedClientIndex, ParserUtil.parseClient(clientIndexWithWhitespace));

        String clientNameWithWhitespace = WHITESPACE + VALID_CLIENT_NAME + WHITESPACE;
        String expectedClientName = VALID_CLIENT_NAME;
        assertEquals(expectedClientName, ParserUtil.parseClient(clientNameWithWhitespace));
    }

    @Test
    public void parseWeddingJobs_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeddingJobs((Set<String>) null));
    }

    @Test
    public void parseWeddingJobs_invalidValues_throwsParseException() {
        setUpInvalidListOfJobs();
        assertThrows(ParseException.class, () -> ParserUtil.parseWeddingJobs(INVALID_LIST_OF_JOBS));
    }

    @Test
    public void parseWeddingJobs_validValuesWithoutWhitespace_returnsSetOfIndexes() throws Exception {
        setUpValidListOfJobsWithoutWhitespace();
        HashSet<Index> expectedSet = new HashSet<>();
        expectedSet.add(Index.fromOneBased(1));
        expectedSet.add(Index.fromOneBased(3));
        assertEquals(expectedSet, ParserUtil.parseWeddingJobs(VALID_LIST_OF_JOBS_WITHOUT_WHITESPACE));
//        assertTrue(expectedSet.equals(ParserUtil.parseWeddingJobs(VALID_LIST_OF_JOBS_WITHOUT_WHITESPACE)));
    }

    @Test
    public void parseWeddingJobs_validValuesWithWhitespace_returnsTrimmedSetOfIndexes() throws Exception {
        setUpValidListOfJobsWithWhitespace();
        HashSet<Index> expectedSet = new HashSet<>();
        expectedSet.add(Index.fromOneBased(1));
        expectedSet.add(Index.fromOneBased(3));
        assertTrue(expectedSet.equals(ParserUtil.parseWeddingJobs(VALID_LIST_OF_JOBS_WITH_WHITESPACE)));
    }

    public static void setUpInvalidListOfJobs() {
        INVALID_LIST_OF_JOBS.add("a");
        INVALID_LIST_OF_JOBS.add("b");
    }

    public static void setUpValidListOfJobsWithoutWhitespace() {
        VALID_LIST_OF_JOBS_WITHOUT_WHITESPACE.add("1");
        VALID_LIST_OF_JOBS_WITHOUT_WHITESPACE.add("3");
    }

    public static void setUpValidListOfJobsWithWhitespace() {
        VALID_LIST_OF_JOBS_WITH_WHITESPACE.add(WHITESPACE + "1" + WHITESPACE);
        VALID_LIST_OF_JOBS_WITH_WHITESPACE.add(WHITESPACE + "3" + WHITESPACE);
    }

}
