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
import seedu.address.model.person.Phone;
import seedu.address.model.person.Appt;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "T012345678910112223";
    private static final String INVALID_BIRTHDATE = " 2050-10-12";
    private static final String INVALID_SEX = "female";
    private static final String INVALID_HEALTHSERVICE = "";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_SEX = BENSON.getSex().toString();
    private static final String VALID_BIRTHDATE = BENSON.getBirthdate().toString();
    private static final List<JsonAdaptedHealthService> VALID_SERVICES = BENSON.getHealthServices().stream()
            .map(JsonAdaptedHealthService::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_NRIC, VALID_SEX, VALID_BIRTHDATE, VALID_SERVICES, null, null, 
                    null, null, null, null, null, null, null, null, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_NRIC, VALID_SEX, VALID_BIRTHDATE, VALID_SERVICES, null, null, 
            null, null, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_NRIC, VALID_SEX, VALID_BIRTHDATE, VALID_SERVICES, null, null, 
                    null, null, null, null, null, null, null, null, null);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_SEX, VALID_BIRTHDATE, VALID_SERVICES, null, null, 
            null, null, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, INVALID_SEX, VALID_BIRTHDATE, VALID_SERVICES, null, null, 
                null, null, null, null, null, null, null, null, null);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, null, VALID_BIRTHDATE, VALID_SERVICES, null, null, 
            null, null, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_SEX, INVALID_BIRTHDATE, VALID_SERVICES, null, null, 
                    null, null, null, null, null, null, null, null, null);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_SEX, null, VALID_SERVICES, null, null, 
            null, null, null, null, null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedHealthService> invalidHealthServices = new ArrayList<>(VALID_SERVICES);
        invalidHealthServices.add(new JsonAdaptedHealthService(INVALID_HEALTHSERVICE));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_NRIC, VALID_SEX, VALID_BIRTHDATE, invalidHealthServices, null, null, 
                    null, null, null, null, null, null, null, null, null);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
