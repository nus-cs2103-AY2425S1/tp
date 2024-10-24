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
import seedu.address.model.clienttype.ClientType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_CLIENT_TYPE = "#A";
    private static final String INVALID_DESC = "apple banana orange mango grape lemon peach pear strawberry blueberry "
             + "raspberry watermelon pineapple kiwi cherry lime grapefruit apricot plum coconut "
            + "fig date nectarine cantaloupe guava papaya pomegranate cranberry tangerine blackberry currant "
            + "dragonfruit lychee persimmon quince starfruit avocado passionfruit gooseberry mulberry "
            + "elderberry boysenberry kumquat jujube "
             + "yuzu ackee rambutan loquat carambola jabuticaba sapodilla medlar feijoa pawpaw salak "
            + "tamarillo lucuma BLAH BLAH BLAH BLAH BLAH BLAH BLAH";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_CLIENT_TYPE_1 = "A";
    private static final String VALID_CLIENT_TYPE_2 = "B";
    private static final String VALID_DESC = "A type";

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
    public void parseClientType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientType(null));
    }

    @Test
    public void parseClientType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientType(INVALID_CLIENT_TYPE));
    }

    @Test
    public void parseClientType_validValueWithoutWhitespace_returnsClientType() throws Exception {
        ClientType expectedClientType = new ClientType(VALID_CLIENT_TYPE_1);
        assertEquals(expectedClientType, ParserUtil.parseClientType(VALID_CLIENT_TYPE_1));
    }

    @Test
    public void parseClientType_validValueWithWhitespace_returnsTrimmedClientType() throws Exception {
        String clientTypeWithWhitespace = WHITESPACE + VALID_CLIENT_TYPE_1 + WHITESPACE;
        ClientType expectedClientType = new ClientType(VALID_CLIENT_TYPE_1);
        assertEquals(expectedClientType, ParserUtil.parseClientType(clientTypeWithWhitespace));
    }

    @Test
    public void parseClientTypes_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientTypes(null));
    }

    @Test
    public void parseClientTypes_collectionWithInvalidClientTypes_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseClientTypes(Arrays.asList(VALID_CLIENT_TYPE_1,
                        INVALID_CLIENT_TYPE)));
    }

    @Test
    public void parseClientTypes_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseClientTypes(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseClientTypes_collectionWithValidClientTypes_returnsClientTypeSet() throws Exception {
        Set<ClientType> actualClientTypeSet = ParserUtil.parseClientTypes(Arrays.asList(VALID_CLIENT_TYPE_1,
                VALID_CLIENT_TYPE_2));
        Set<ClientType> expectedClientTypeSet = new HashSet<ClientType>(Arrays
                .asList(new ClientType(VALID_CLIENT_TYPE_1), new ClientType(VALID_CLIENT_TYPE_2)));

        assertEquals(expectedClientTypeSet, actualClientTypeSet);
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESC));
    }

}
