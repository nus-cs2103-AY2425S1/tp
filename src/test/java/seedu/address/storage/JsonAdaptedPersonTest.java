package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Ward;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "Rachel!";
    private static final String INVALID_ID = "P12^45";
    private static final String INVALID_WARD = "";
    private static final String INVALID_DIAGNOSIS = "$<>";
    private static final String INVALID_MEDICATION = "@##";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ID = BENSON.getId().toString();
    private static final String VALID_WARD = BENSON.getWard().toString();
    private static final String VALID_DIAGNOSIS = BENSON.getDiagnosis().toString();
    private static final String VALID_MEDICATION = BENSON.getMedication().toString();
    private static final String VALID_NOTES = BENSON.getNotes().toString();
    private static final JsonAdaptedAppointment VALID_APPOINTMENT = new JsonAdaptedAppointment(BENSON.getAppointment());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_ID, VALID_WARD, VALID_DIAGNOSIS, VALID_MEDICATION,
                        VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson((String) null, VALID_ID, VALID_WARD, VALID_DIAGNOSIS,
                VALID_MEDICATION, VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_ID, VALID_WARD, VALID_DIAGNOSIS, VALID_MEDICATION,
                        VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_WARD, VALID_DIAGNOSIS,
                VALID_MEDICATION, VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWard_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ID, INVALID_WARD, VALID_DIAGNOSIS, VALID_MEDICATION,
                        VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = Ward.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullWard_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_ID, null, VALID_DIAGNOSIS,
                VALID_MEDICATION, VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ward.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDiagnosis_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ID, VALID_WARD, INVALID_DIAGNOSIS, VALID_MEDICATION,
                        VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = Diagnosis.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDiagnosis_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_ID, VALID_WARD, null,
                VALID_MEDICATION, VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Diagnosis.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMedication_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_ID, VALID_WARD, VALID_DIAGNOSIS, INVALID_MEDICATION,
                        VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = Medication.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMedication_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_ID, VALID_WARD,
                VALID_MEDICATION, null, VALID_NOTES, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Medication.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
