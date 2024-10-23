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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.TelegramHandle;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TELEGRAM_HANDLE = "+651234";
    private static final String INVALID_STUDENT_STATUS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROLES = "Freeloader";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_TELEGRAM_HANDLE = BENSON.getTelegramHandle().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_STUDENT_STATUS = BENSON.getStudentStatus().toString();
    private static final List<JsonAdaptedRole> VALID_ROLES = BENSON.getRoles().stream()
            .map(JsonAdaptedRole::new)
            .collect(Collectors.toList());
    private static final String VALID_NICKNAME = BENSON.getNickname().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = TelegramHandle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_STUDENT_STATUS,
                VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TelegramHandle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM_HANDLE, INVALID_EMAIL, VALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM_HANDLE, null, VALID_STUDENT_STATUS,
                VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStudentStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, INVALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = StudentStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStudentStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, null,
                VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRoles_throwsIllegalValueException() {
        List<JsonAdaptedRole> invalidRoles = new ArrayList<>(VALID_ROLES);
        invalidRoles.add(new JsonAdaptedRole(INVALID_ROLES));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                        invalidRoles, VALID_NICKNAME);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
