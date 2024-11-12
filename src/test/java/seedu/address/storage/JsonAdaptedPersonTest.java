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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "T1231";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ROLE = "NOT_A_PATIENT";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedRole> VALID_ROLE = BENSON.getRoles().stream()
            .map(JsonAdaptedRole::new)
            .collect(Collectors.toList());

    private static final List<String> VALID_CAREGIVERS = BENSON.getCaregiversNric().stream()
            .collect(Collectors.toList());

    private static final List<String> VALID_PATIENTS = BENSON.getPatientsNric().stream().collect(Collectors.toList());

    private static final List<JsonAdaptedNote> VALID_NOTES = BENSON.getNotes().stream()
        .map(JsonAdaptedNote::new)
        .collect(Collectors.toList());


    private static final List<JsonAdaptedAppointment> VALID_APPOINTMENT = BENSON.getAppointments()
            .stream()
            .map(JsonAdaptedAppointment::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ROLE, VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT,
                VALID_NOTES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS,
                VALID_ROLE, VALID_CAREGIVERS,
                VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS,
                VALID_ROLE, VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullnric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS,
                VALID_ROLE, VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE,
                null,
                VALID_ADDRESS, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE,
                VALID_EMAIL, null, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, invalidTags,
                VALID_ROLE, VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidRoles_throwsIllegalValueException() {
        List<JsonAdaptedRole> invalidRoles = new ArrayList<>(VALID_ROLE);
        invalidRoles.add(new JsonAdaptedRole(INVALID_ROLE)); // Invalid role
        invalidRoles.add(new JsonAdaptedRole(INVALID_ROLE)); // Invalid role
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, invalidRoles,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validPersonDetailsWithNotes_returnsPerson() throws Exception {

        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_ROLE,
                VALID_CAREGIVERS, VALID_PATIENTS, VALID_APPOINTMENT, VALID_NOTES);

        Person person = jsonPerson.toModelType();

        List<String> expectedNotes = BENSON.getNotes().stream().map(Note::getContent).collect(Collectors.toList());

        List<String> actualNotes = person.getNotes().stream().map(Note::getContent).collect(Collectors.toList());


        assertEquals(expectedNotes, actualNotes);

    }
}
