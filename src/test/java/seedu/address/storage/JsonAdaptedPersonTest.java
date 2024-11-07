package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_HOURS = "-69";
    private static final String INVALID_SUBJECTS = "wrong";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_HOURS = BENSON.getHours().toString();
    private static final List<JsonAdaptedSubject> VALID_SUBJECTS = BENSON.getSubjects().stream()
            .map(JsonAdaptedSubject::new).collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HOURS, "Tutor", VALID_SUBJECTS);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_HOURS,
                        "Tutor", VALID_SUBJECTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(1, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HOURS, "Tutor", VALID_SUBJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_HOURS,
                        "Tutor", VALID_SUBJECTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(1, VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_HOURS, "Tutor", VALID_SUBJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_HOURS,
                        "Tutor", VALID_SUBJECTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_HOURS, "Tutor", VALID_SUBJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_HOURS,
                        "Tutor", VALID_SUBJECTS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidHours_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_HOURS,
                        "Tutor", VALID_SUBJECTS);
        String expectedMessage = Hours.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSubjects_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_HOURS,
                        "Tutor", List.of(new JsonAdaptedSubject(INVALID_SUBJECTS)));
        String expectedMessage = Subject.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HOURS, null, VALID_SUBJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Role");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(1, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HOURS, "Invalid", VALID_SUBJECTS);
        String expectedMessage = "Role must be either Tutor or Tutee";
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
