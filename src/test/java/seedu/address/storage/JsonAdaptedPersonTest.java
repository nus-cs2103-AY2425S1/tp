package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "     \t   "; // entirely whitespace
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "   \t ";
    private static final String INVALID_DATEOFLASTVISIT = "01/01/2024"; // must be separated by dashes
    private static final String INVALID_EMERGENCY_CONTACT = INVALID_PHONE;

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_SPECIAL_CHARACTER_NAME = "Jane-Mary Sue/anne";
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().get().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().get().toString();
    private static final String VALID_EMPTY_FIELD = "";
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream().map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_DATEOFLASTVISIT = BENSON.getDateOfLastVisit().get().toString();
    private static final String VALID_EMERGENCY_CONTACT = VALID_PHONE;
    private static final String VALID_REMARK = BENSON.getRemark().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());

        assertEquals(
                new JsonAdaptedPerson(VALID_SPECIAL_CHARACTER_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_DATEOFLASTVISIT,
                        VALID_EMERGENCY_CONTACT, VALID_REMARK).toModelType().getName(),
                new Name(VALID_SPECIAL_CHARACTER_NAME));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                        VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_DATEOFLASTVISIT, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_DATEOFLASTVISIT, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_DATEOFLASTVISIT, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_noEmail_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMPTY_FIELD,
                VALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE).withEmail()
                .withAddress(VALID_ADDRESS).withTags(validTagsInString)
                .withDateOfLastVisit(VALID_DATEOFLASTVISIT).withEmergencyContact(VALID_EMERGENCY_CONTACT)
                .withRemark(VALID_REMARK).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_DATEOFLASTVISIT, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_noAddress_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_EMPTY_FIELD, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL).withAddress().withTags(validTagsInString)
                .withDateOfLastVisit(VALID_DATEOFLASTVISIT).withEmergencyContact(VALID_EMERGENCY_CONTACT)
                .withRemark(VALID_REMARK).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                invalidTags, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfLastVisit_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, INVALID_DATEOFLASTVISIT, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = DateOfLastVisit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfLastVisit_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateOfLastVisit.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_noDateOfLastVisit_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_EMPTY_FIELD, VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL).withAddress(VALID_ADDRESS).withTags(validTagsInString)
                .withDateOfLastVisit().withEmergencyContact(VALID_EMERGENCY_CONTACT)
                .withRemark(VALID_REMARK).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_noEmergencyContact_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_DATEOFLASTVISIT, VALID_EMPTY_FIELD, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL).withAddress(VALID_ADDRESS).withTags(validTagsInString)
                .withDateOfLastVisit(VALID_DATEOFLASTVISIT).withEmergencyContact().build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidEmergencyContact_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_DATEOFLASTVISIT, INVALID_EMERGENCY_CONTACT, VALID_REMARK);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmergencyContact_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_DATEOFLASTVISIT, null, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                EmergencyContact.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_noAddressNoEmail_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMPTY_FIELD,
                VALID_EMPTY_FIELD, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE).withEmail()
                .withAddress().withTags(validTagsInString).withDateOfLastVisit(VALID_DATEOFLASTVISIT)
                .withEmergencyContact(VALID_EMERGENCY_CONTACT).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_noAddressNoDateOfLastVisit_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_EMPTY_FIELD, VALID_TAGS, VALID_EMPTY_FIELD,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL).withAddress().withTags(validTagsInString).withDateOfLastVisit()
                .withEmergencyContact(VALID_EMERGENCY_CONTACT).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_noEmailNoDateOfLastVisit_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMPTY_FIELD,
                VALID_ADDRESS, VALID_TAGS, VALID_EMPTY_FIELD,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE).withEmail()
                .withAddress(VALID_ADDRESS).withTags(validTagsInString).withDateOfLastVisit()
                .withEmergencyContact(VALID_EMERGENCY_CONTACT).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_noAddressNoEmailNoDateOfLastVisit_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMPTY_FIELD,
                VALID_EMPTY_FIELD, VALID_TAGS, VALID_EMPTY_FIELD,
                VALID_EMERGENCY_CONTACT, VALID_REMARK);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE).withEmail()
                .withAddress().withTags(validTagsInString).withDateOfLastVisit()
                .withEmergencyContact(VALID_EMERGENCY_CONTACT).build();
        assertEquals(expectedPerson, person.toModelType());
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_noRemark_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_DATEOFLASTVISIT,
                VALID_EMERGENCY_CONTACT, VALID_EMPTY_FIELD);
        String[] validTagsInString = VALID_TAGS.stream().map(tag -> tag.getTagName()).toArray(String[]::new);
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL).withAddress(VALID_ADDRESS).withTags(validTagsInString)
                .withDateOfLastVisit(VALID_DATEOFLASTVISIT)
                .withEmergencyContact(VALID_EMERGENCY_CONTACT).withRemark("").build();
        assertEquals(expectedPerson, person.toModelType());
    }
}
