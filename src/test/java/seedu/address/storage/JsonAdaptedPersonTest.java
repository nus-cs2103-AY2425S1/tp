package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import seedu.address.model.person.GradYear;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ROOM_NUMBER = "01-01";
    private static final String INVALID_EMERGENCY_NAME = "R@chel";
    private static final String INVALID_EMERGENCY_PHONE = "+651234";
    private static final String INVALID_GRAD_YEAR = "123";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_ROOM_NUMBER = BENSON.getRoomNumber().get().toString();
    private static final String VALID_EMERGENCY_NAME = BENSON.getEmergencyContactName().get().toString();
    private static final String VALID_EMERGENCY_PHONE = BENSON.getEmergencyContactPhone().get().toString();
    private static final String VALID_GRAD_YEAR = BENSON.getGradYear().get().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = Name.CHAR_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER,
                VALID_ADDRESS, VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ROOM_NUMBER,
                VALID_ADDRESS, VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ROOM_NUMBER,
                VALID_ADDRESS, VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRoomNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = RoomNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRoomNumber_returnsPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        assertFalse(person.toModelType().getRoomNumber().isPresent());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER,
                        INVALID_ADDRESS, VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER,
                null, VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmergencyName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        INVALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = Name.CHAR_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmergencyName_returnsPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        null, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        assertFalse(person.toModelType().getEmergencyContactName().isPresent());
    }

    @Test
    public void toModelType_invalidEmergencyPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, INVALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmergencyPhone_returnsPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, null, VALID_GRAD_YEAR, VALID_TAGS);
        assertFalse(person.toModelType().getEmergencyContactPhone().isPresent());
    }

    @Test
    public void toModeType_invalidGradYear_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, INVALID_GRAD_YEAR, VALID_TAGS);
        String expectedMessage = GradYear.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGradYear_returnsPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, null, VALID_TAGS);
        assertFalse(person.toModelType().getGradYear().isPresent());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOM_NUMBER, VALID_ADDRESS,
                        VALID_EMERGENCY_NAME, VALID_EMERGENCY_PHONE, VALID_GRAD_YEAR, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
