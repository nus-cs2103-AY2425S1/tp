package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_NETWORK = "invalid";
    private static final String INVALID_LABEL = " ";
    private static final String INVALID_BTC_ADDRESS = " ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_NETWORK = "BTC";
    private static final String VALID_LABEL = "My BTC Wallet";
    private static final String VALID_BTC_ADDRESS_1 = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_BTC_ADDRESS_2 = "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy";

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
    public void parsePublicAddress_nullPublicAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                ParserUtil.parsePublicAddress(null, VALID_LABEL, VALID_NETWORK));
    }

    @Test
    public void parsePublicAddress_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                ParserUtil.parsePublicAddress(VALID_BTC_ADDRESS_1, null, VALID_NETWORK));
    }

    @Test
    public void parsePublicAddress_invalidPublicAddress_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePublicAddress(INVALID_BTC_ADDRESS, VALID_LABEL, VALID_NETWORK));
    }

    @Test
    public void parsePublicAddress_invalidLabel_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePublicAddress(VALID_BTC_ADDRESS_1, INVALID_LABEL, VALID_NETWORK));
    }

    @Test
    public void parsePublicAddress_invalidNetwork_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePublicAddress(VALID_BTC_ADDRESS_1, VALID_LABEL, INVALID_NETWORK));
    }

    @Test
    public void parsePublicAddress_validInputs_returnsPublicAddress() throws Exception {
        PublicAddress expectedAddress = new BtcAddress(VALID_BTC_ADDRESS_1, VALID_LABEL);
        assertEquals(expectedAddress, ParserUtil.parsePublicAddress(VALID_BTC_ADDRESS_1, VALID_LABEL, VALID_NETWORK));
    }

    @Test
    public void parsePublicAddress_validInputsWithWhitespace_returnsPublicAddress() throws Exception {
        String addressWithWhitespace = " " + VALID_BTC_ADDRESS_1 + " ";
        String labelWithWhitespace = " " + VALID_LABEL + " ";
        PublicAddress expectedAddress = new BtcAddress(VALID_BTC_ADDRESS_1, VALID_LABEL);
        assertEquals(expectedAddress,
                ParserUtil.parsePublicAddress(addressWithWhitespace, labelWithWhitespace, VALID_NETWORK));
    }

    @Test
    public void parsePublicAddresses_nullCollection_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePublicAddresses(null));
    }

    @Test
    public void parsePublicAddresses_emptyCollection_returnsEmptyMap() throws Exception {
        assertTrue(ParserUtil.parsePublicAddresses(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parsePublicAddresses_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePublicAddresses(List.of("BTC" + VALID_BTC_ADDRESS_1)));
    }

    @Test
    public void parsePublicAddresses_invalidNetwork_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parsePublicAddresses(List.of(INVALID_NETWORK + ">" + VALID_BTC_ADDRESS_1)));
    }

    @Test
    public void parsePublicAddresses_validInputs_returnsMap() throws Exception {
        Collection<String> inputs = Arrays.asList(
                VALID_NETWORK + ">" + VALID_BTC_ADDRESS_1,
                VALID_NETWORK + ">" + VALID_BTC_ADDRESS_2
        );

        Map<Network, Set<PublicAddress>> expectedMap = new HashMap<>();
        Set<PublicAddress> btcAddresses = new HashSet<>();
        btcAddresses.add(new BtcAddress(VALID_BTC_ADDRESS_1, PublicAddress.DEFAULT_LABEL));
        btcAddresses.add(new BtcAddress(VALID_BTC_ADDRESS_2, PublicAddress.DEFAULT_LABEL));
        expectedMap.put(Network.BTC, btcAddresses);

        assertEquals(expectedMap, ParserUtil.parsePublicAddresses(inputs));
    }

    @Test
    public void parsePublicAddresses_validInputsWithWhitespace_returnsMap() throws Exception {
        Collection<String> inputs = Arrays.asList(
                " " + VALID_NETWORK + " > " + VALID_BTC_ADDRESS_1 + " ",
                " " + VALID_NETWORK + " > " + VALID_BTC_ADDRESS_2 + " "
        );

        Map<Network, Set<PublicAddress>> expectedMap = new HashMap<>();
        Set<PublicAddress> btcAddresses = new HashSet<>();
        btcAddresses.add(new BtcAddress(VALID_BTC_ADDRESS_1, PublicAddress.DEFAULT_LABEL));
        btcAddresses.add(new BtcAddress(VALID_BTC_ADDRESS_2, PublicAddress.DEFAULT_LABEL));
        expectedMap.put(Network.BTC, btcAddresses);

        assertEquals(expectedMap, ParserUtil.parsePublicAddresses(inputs));
    }

    @Test
    public void parsePublicAddressLabel_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePublicAddressLabel(null));
    }

    @Test
    public void parsePublicAddressLabel_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePublicAddressLabel(INVALID_LABEL));
    }

}
