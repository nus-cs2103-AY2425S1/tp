package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Sex;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "T012345678910112223";
    private static final String INVALID_BIRTHDATE = " 2050-10-12";
    private static final String INVALID_SEX = "female";
    private static final String INVALID_HEALTHSERVICE = "";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_SEX = BENSON.getSex().toString();
    private static final String VALID_BIRTHDATE = BENSON.getBirthdate().toString();

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, patient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(INVALID_NAME, VALID_NRIC, VALID_SEX, VALID_BIRTHDATE,
                    null, null, null, null, null, null, null,
                    null, null, null, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(null, VALID_NRIC, VALID_SEX, VALID_BIRTHDATE,
                null, null, null, null, null, null,
                null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, INVALID_NRIC, VALID_SEX, VALID_BIRTHDATE,
                    null, null, null, null, null, null,
                    null, null, null, null, null);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(VALID_NAME, null, VALID_SEX, VALID_BIRTHDATE,
                null, null, null, null, null, null,
                null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidSex_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, INVALID_SEX, VALID_BIRTHDATE,
                    null, null, null, null, null, null,
                    null, null, null, null, null);
        String expectedMessage = Sex.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullSex_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, null, VALID_BIRTHDATE,
                null, null, null, null, null, null,
                null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_SEX, INVALID_BIRTHDATE,
                    null, null, null, null, null, null,
                    null, null, null, null, null);
        String expectedMessage = Birthdate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullBirthDate_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_SEX, null,
                null, null, null, null, null, null,
                null, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Birthdate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    /*
    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedHealthService> invalidHealthServices = new ArrayList<>(VALID_SERVICES);
        invalidHealthServices.add(new JsonAdaptedHealthService(INVALID_HEALTHSERVICE));
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_SEX, VALID_BIRTHDATE, invalidHealthServices,
                    null, null, null, null, null, null,
                    null, null, null, null, null);
        assertThrows(IllegalValueException.class, patient::toModelType);
    }
    */

}
