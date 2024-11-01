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

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TELE = "a";
    private static final String INVALID_ROLE = "owner";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TELE = "rachel";

    private static final String VALID_ROLE_1 = "vendor";
    private static final String VALID_ROLE_2 = "sponsor";

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
    public void parseTeleOnAdd_nullTelegramUsername() throws ParseException {
        TelegramUsername expectedTelegramUsername = new TelegramUsername(null);
        assertEquals(expectedTelegramUsername, ParserUtil.parseTeleOnAdd(null));
    }

    @Test
    public void parseTeleOnAdd_invalidTelegramUsername_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTeleOnAdd(INVALID_TELE));
    }

    @Test
    public void parseTeleOnAdd_validValueWithWhitespace_returnsTrimmedTele() throws ParseException {
        String teleWithWhitespace = WHITESPACE + VALID_TELE + WHITESPACE;
        TelegramUsername expectedTelegramUsername = new TelegramUsername(VALID_TELE);
        assertEquals(expectedTelegramUsername, ParserUtil.parseTeleOnAdd(teleWithWhitespace));
    }

    @Test
    public void parseTeleOnAdd_validValueWithoutWhitespace_returnsTele() throws Exception {
        TelegramUsername expectedTelegramUsername = new TelegramUsername(VALID_TELE);
        assertEquals(expectedTelegramUsername, ParserUtil.parseTeleOnAdd(VALID_TELE));
    }

    @Test
    public void parseTeleOnEdit_emptyTelegramUsername_removesTele() throws ParseException {
        TelegramUsername expectedTelegramUsername = new TelegramUsername(null);
        assertEquals(expectedTelegramUsername, ParserUtil.parseTeleOnEdit(""));
    }

    @Test
    public void parseTeleOnEdit_invalidTelegramUsername_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTeleOnEdit(INVALID_TELE));
    }

    @Test
    public void parseTeleOnEdit_validValueWithWhitespace_returnsTrimmedTele() throws ParseException {
        String teleWithWhitespace = WHITESPACE + VALID_TELE + WHITESPACE;
        TelegramUsername expectedTelegramUsername = new TelegramUsername(VALID_TELE);
        assertEquals(expectedTelegramUsername, ParserUtil.parseTeleOnEdit(teleWithWhitespace));
    }

    @Test
    public void parseTeleOnEdit_validValueWithoutWhitespace_returnsTele() throws Exception {
        TelegramUsername expectedTelegramUsername = new TelegramUsername(VALID_TELE);
        assertEquals(expectedTelegramUsername, ParserUtil.parseTeleOnEdit(VALID_TELE));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole((String) null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsEmail() throws Exception {
        RoleHandler roleHandler = new RoleHandler();

        Role expectedRole = roleHandler.getRole(VALID_ROLE_1);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE_1));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE_1 + WHITESPACE;
        RoleHandler roleHandler = new RoleHandler();
        Role expectedRole = RoleHandler.getRole(roleWithWhitespace);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseRoles_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRoles(null));
    }

    @Test
    public void parseRoles_collectionWithInvalidRoles_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRoles(Arrays.asList(VALID_ROLE_1, INVALID_ROLE)));
    }
    @Test
    public void parseRoles_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseRoles(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseRoles_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Role> actualRoleSet = ParserUtil.parseRoles(Arrays.asList(VALID_ROLE_1, VALID_ROLE_2));
        RoleHandler roleHandler = new RoleHandler();
        Set<Role> expectedRoleSet = new HashSet<Role>(Arrays.asList(roleHandler.getRole(VALID_ROLE_1),
                roleHandler.getRole(VALID_ROLE_2)));

        assertEquals(expectedRoleSet, actualRoleSet);
    }

    @Test
    public void parseEvent_validEventStringWithoutWhitespace_success() throws ParseException {
        String input = "Birthday Party on 12/12/2024";
        Event event = ParserUtil.parseEvent(input);
        assertEquals(new Event(input), event);
    }

    @Test
    public void parseEvent_validEventStringWithWhitespace_success() throws ParseException {
        String input = WHITESPACE + "Birthday Party on 12/12/2024" + WHITESPACE;
        Event event = ParserUtil.parseEvent(input);
        assertEquals(new Event("Birthday Party on 12/12/2024"), event);
    }

    @Test
    public void parseEvent_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            ParserUtil.parseEvent("");
        });
    }

    @Test
    public void parseEvent_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            ParserUtil.parseEvent(null);
        });
    }

    @Test
    public void parseStringOfIndices_null_doesNothing() throws ParseException {
        HashSet<Index> indices = new HashSet<>();
        ParserUtil.parseStringOfIndices(indices, null);
        assertEquals(indices, new HashSet<Index>());
    }

    @Test
    public void parseStringOfIndices_invalidIndex_throwsParseException() throws ParseException {
        HashSet<Index> indices = new HashSet<>();

        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, "0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, "-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, "baby"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, "10.5, 2, tree"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, ""));
        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, "1, "));
    }

    @Test
    public void parseStringOfIndices_inputWithWhiteSpace_parsedSuccessfully() throws ParseException {
        HashSet<Index> indices = new HashSet<>();
        HashSet<Index> expectedIndices = new HashSet<>();

        expectedIndices.add(Index.fromOneBased(1));
        expectedIndices.add(Index.fromZeroBased(2));
        expectedIndices.add(Index.fromZeroBased(1));
        expectedIndices.add(Index.fromZeroBased(3));
        expectedIndices.add(Index.fromZeroBased(4));
        expectedIndices.add(Index.fromZeroBased(6));
        expectedIndices.add(Index.fromZeroBased(8));

        ParserUtil.parseStringOfIndices(indices, "  1,  3  ");
        ParserUtil.parseStringOfIndices(indices, "2,  4  ,5,7  ,  9");

        assertEquals(indices, expectedIndices);
    }

    @Test
    public void parseStringOfIndices_inputWithoutWhiteSpace_parsedSuccessfully() throws ParseException {
        HashSet<Index> indices = new HashSet<>();
        HashSet<Index> expectedIndices = new HashSet<>();

        expectedIndices.add(Index.fromOneBased(1));
        expectedIndices.add(Index.fromZeroBased(2));

        ParserUtil.parseStringOfIndices(indices, "1,3");

        assertEquals(indices, expectedIndices);
    }

    @Test
    public void parseStringOfIndices_duplicateIndices_throwsParseException() {
        HashSet<Index> indices = new HashSet<>();

        assertThrows(ParseException.class, () -> ParserUtil.parseStringOfIndices(indices, "1,1"));
    }
}
