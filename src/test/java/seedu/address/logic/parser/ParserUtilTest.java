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
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relation;
import seedu.address.model.person.Rsvp;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.RsvpContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_RELATION = "HW";
    private static final String INVALID_RSVP = "yes";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_COMPANY = "The Florist";
    private static final String VALID_RELATION = "H";
    private static final String VALID_RSVP = "ACCEPTED";

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
    public void parseRelation_null_returnsNull() throws ParseException {
        assertEquals(null, ParserUtil.parseRelation(null));
    }

    @Test
    public void parseRelation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRelation(INVALID_RELATION));
    }

    @Test
    public void parseRelation_validValueWithoutWhitespace_returnsRelation() throws Exception {
        Relation expectedRelation = new Relation(VALID_RELATION);
        assertEquals(expectedRelation, ParserUtil.parseRelation(VALID_RELATION));
    }

    @Test
    public void parseRelation_validValueWithWhitespace_returnsTrimmedRelation() throws Exception {
        String relationWithWhitespace = WHITESPACE + VALID_RELATION + WHITESPACE;
        Relation expectedRelation = new Relation(VALID_RELATION);
        assertEquals(expectedRelation, ParserUtil.parseRelation(relationWithWhitespace));
    }

    @Test
    public void parseRsvp_null_throwsNullPointerException() throws ParseException {
        assertEquals(null, ParserUtil.parseRsvp((String) null));
    }

    @Test
    public void parseRsvp_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRsvp(INVALID_RSVP));
    }

    @Test
    public void parseRsvp_validValueWithoutWhitespace_returnsRsvp() throws Exception {
        Rsvp expectedRsvp = new Rsvp(VALID_RSVP);
        assertEquals(expectedRsvp, ParserUtil.parseRsvp(VALID_RSVP));
    }

    @Test
    public void parseRsvp_validValueWithWhitespace_returnsTrimmedRsvp() throws Exception {
        String rsvpWithWhitespace = WHITESPACE + VALID_RSVP + WHITESPACE;
        Rsvp expectedRsvp = new Rsvp(VALID_RSVP);
        assertEquals(expectedRsvp, ParserUtil.parseRsvp(rsvpWithWhitespace));
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
    public void parseCompany_null_throwsNullPointerException() throws ParseException {
        assertEquals(null, ParserUtil.parseCompany((String) null));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseNamePredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNamePredicate((String) null));
    }

    @Test
    public void parseNamePredicate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNamePredicate(INVALID_NAME));
    }

    @Test
    public void parseNamePredicate_validValueWithoutWhitespace_success() throws Exception {
        NameContainsKeywordsPredicate expectedNamePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList(VALID_NAME.split(" ")));
        assertEquals(expectedNamePredicate, ParserUtil.parseNamePredicate(VALID_NAME));
    }

    @Test
    public void parsePhonePredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhonePredicate((String) null));
    }

    @Test
    public void parsePhonePredicate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhonePredicate(INVALID_PHONE));
    }

    @Test
    public void parsePhonePredicate_validValueWithoutWhitespace_success() throws Exception {
        PhoneContainsKeywordsPredicate expectedPhonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList(VALID_PHONE.split(" ")));
        assertEquals(expectedPhonePredicate, ParserUtil.parsePhonePredicate(VALID_PHONE));
    }

    @Test
    public void parseAddressPredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddressPredicate((String) null));
    }

    @Test
    public void parseAddressPredicate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddressPredicate(INVALID_ADDRESS));
    }

    @Test
    public void parseAddressPredicate_validValueWithoutWhitespace_success() throws Exception {
        AddressContainsKeywordsPredicate expectedAddressPredicate =
                new AddressContainsKeywordsPredicate(Arrays.asList(VALID_ADDRESS.split(" ")));
        assertEquals(expectedAddressPredicate, ParserUtil.parseAddressPredicate(VALID_ADDRESS));
    }

    @Test
    public void parseEmailPredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmailPredicate((String) null));
    }

    @Test
    public void parseEmailPredicate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmailPredicate(INVALID_EMAIL));
    }

    @Test
    public void parseEmailPredicate_validValueWithoutWhitespace_success() throws Exception {
        EmailContainsKeywordsPredicate expectedEmailPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList(VALID_EMAIL.split(" ")));
        assertEquals(expectedEmailPredicate, ParserUtil.parseEmailPredicate(VALID_EMAIL));
    }

    @Test
    public void parseRsvpPredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRsvpPredicate((String) null));
    }

    @Test
    public void parseRsvpPredicate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRsvpPredicate(INVALID_RSVP));
    }

    @Test
    public void parseRsvpPredicate_validValueWithoutWhitespace_success() throws Exception {
        RsvpContainsKeywordsPredicate expectedRsvpPredicate =
                new RsvpContainsKeywordsPredicate(Arrays.asList(VALID_RSVP.split(" ")));
        assertEquals(expectedRsvpPredicate, ParserUtil.parseRsvpPredicate(VALID_RSVP));
    }

    @Test
    public void parseCompanyPredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompanyPredicate((String) null));
    }

    @Test
    public void parseCompanyPredicate_validValueWithoutWhitespace_success() throws Exception {
        CompanyContainsKeywordsPredicate expectedCompanyPredicate =
                new CompanyContainsKeywordsPredicate(Arrays.asList(VALID_COMPANY.split(" ")));
        assertEquals(expectedCompanyPredicate, ParserUtil.parseCompanyPredicate(VALID_COMPANY));
    }

    @Test
    public void parseCompanyPredicate_validValueWithWhitespace_success() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        CompanyContainsKeywordsPredicate expectedCompanyPredicate =
                new CompanyContainsKeywordsPredicate(Arrays.asList(VALID_COMPANY.split(" ")));
        assertEquals(expectedCompanyPredicate, ParserUtil.parseCompanyPredicate(companyWithWhitespace));
    }

    @Test
    public void parseTagPredicate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTagPredicate(null));
    }

    @Test
    public void parseTagPredicate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTagPredicate(Arrays.asList(INVALID_TAG)));
    }

    @Test
    public void parseTagPredicate_validValueWithoutWhitespace_returnsTag() throws Exception {
        TagContainsKeywordsPredicate expectedTagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList(VALID_TAG_1));
        assertEquals(expectedTagPredicate, ParserUtil.parseTagPredicate(Arrays.asList(VALID_TAG_1)));
    }

    @Test
    public void parseTagPredicate_multipleTags_returnsTags() throws Exception {
        TagContainsKeywordsPredicate expectedTagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        assertEquals(expectedTagPredicate, ParserUtil.parseTagPredicate(Arrays.asList(VALID_TAG_1, VALID_TAG_2)));
    }
}
