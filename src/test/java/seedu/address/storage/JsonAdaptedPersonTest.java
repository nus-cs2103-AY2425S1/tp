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
import seedu.address.model.person.Log;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Triage;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NRIC = "1234A6789";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_TRIAGE = "hi";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_TRIAGE = BENSON.getTriage().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<String> VALID_LOG_ENTRIES = BENSON.getLogEntries().getLogs().stream()
            .map(Log::toString)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            BENSON.getName().toString(),
            BENSON.getPhone().toString(),
            BENSON.getEmail().toString(),
            BENSON.getNric().toString(),
            BENSON.getAddress().toString(),
            BENSON.getTriage().toString(),
            BENSON.getRemark().toString(),
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            INVALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            null,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            INVALID_PHONE,
            VALID_EMAIL,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            null,
            VALID_EMAIL,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            INVALID_EMAIL,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            null,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            INVALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            null,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_NRIC,
            INVALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_NRIC,
            null,
            VALID_TRIAGE,
            VALID_REMARK,
            VALID_TAGS,
            null,
            VALID_LOG_ENTRIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_NRIC,
            VALID_ADDRESS,
            VALID_TRIAGE,
            VALID_REMARK,
            invalidTags,
            null,
            VALID_LOG_ENTRIES
        );
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidTriage_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_NRIC,
                VALID_ADDRESS,
                INVALID_TRIAGE,
                VALID_REMARK,
                VALID_TAGS,
                null,
                VALID_LOG_ENTRIES
        );
        String expectedMessage = Triage.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTriage_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_NRIC,
                VALID_ADDRESS,
                null,
                VALID_REMARK,
                VALID_TAGS,
                null,
                VALID_LOG_ENTRIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Triage.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
