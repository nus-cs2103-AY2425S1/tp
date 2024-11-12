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
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_IDENTITY_NUMBER = "S123Z";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_STATUS = "#HIGH";
    private static final String INVALID_LOG_DATE = "19-10-2024"; // INCORRECT FORMAT dd MMM yyyy
    private static final String INVALID_LOG_ENTRY = " ";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_IDENTITY_NUMBER = BENSON.getIdentityNumber().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();

    private static final List<JsonAdaptedLog> VALID_LOGS = BENSON.getLogs().stream()
            .map(JsonAdaptedLog::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_STATUS, VALID_LOGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_STATUS, VALID_LOGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIdentityNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_STATUS, VALID_LOGS);
        String expectedMessage = IdentityNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullIdentityNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_STATUS, VALID_LOGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IdentityNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_STATUS, VALID_LOGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_STATUS, VALID_LOGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_STATUS, VALID_LOGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, null,
                VALID_ADDRESS, VALID_STATUS, VALID_LOGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_STATUS, VALID_LOGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL,
                null, VALID_STATUS, VALID_LOGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_STATUS, VALID_LOGS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_LOGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_invalidLog_throwsIllegalValueException() {
        List<JsonAdaptedLog> invalidLogs = new ArrayList<>(VALID_LOGS);
        invalidLogs.add(new JsonAdaptedLog(INVALID_LOG_DATE, INVALID_LOG_ENTRY));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_IDENTITY_NUMBER, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_STATUS, invalidLogs);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
