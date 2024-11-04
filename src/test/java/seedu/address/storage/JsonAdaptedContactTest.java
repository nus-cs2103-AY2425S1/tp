package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;

public class JsonAdaptedContactTest {
    private static final String INVALID_NAME = "R#chel";
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
    public void toModelType_validContactDetails_returnsContact() throws Exception {
        JsonAdaptedContact contact = new JsonAdaptedContact(BENSON);
        assertEquals(BENSON, contact.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(INVALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(null, VALID_TELEGRAM_HANDLE, VALID_EMAIL,
                VALID_STUDENT_STATUS, VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, INVALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = TelegramHandle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, null, VALID_EMAIL, VALID_STUDENT_STATUS,
                VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TelegramHandle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_TELEGRAM_HANDLE, INVALID_EMAIL, VALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_TELEGRAM_HANDLE, null,
                VALID_STUDENT_STATUS, VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidStudentStatus_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, INVALID_STUDENT_STATUS,
                        VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = StudentStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullStudentStatus_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, null,
                VALID_ROLES, VALID_NICKNAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidRoles_throwsIllegalValueException() {
        List<JsonAdaptedRole> invalidRoles = new ArrayList<>(VALID_ROLES);
        invalidRoles.add(new JsonAdaptedRole(INVALID_ROLES));
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_TELEGRAM_HANDLE, VALID_EMAIL, VALID_STUDENT_STATUS,
                        invalidRoles, VALID_NICKNAME);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

}
