package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.MedCon;

public class JsonAdaptedMedConTest {

    private static final String VALID_MEDCON = "Diabetes";
    private static final String INVALID_MEDCON_EMPTY = "";
    private static final String INVALID_MEDCON_TOO_LONG =
            "ThisMedicalConditionNameIsWayTooLongAndExceedsThirtyCharacters";
    private static final String INVALID_MEDCON_SPECIAL_CHARS = "@Diabetes";

    @Test
    public void toModelType_validMedCon_returnsMedCon() throws Exception {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(VALID_MEDCON);
        MedCon expectedMedCon = new MedCon(VALID_MEDCON);
        assertEquals(expectedMedCon, jsonAdaptedMedCon.toModelType());
    }

    @Test
    public void toModelType_nullMedCon_throwsIllegalValueException() {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon((String) null);
        String expectedMessage = String.format("MedCon cannot be null",
                MedCon.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> jsonAdaptedMedCon.toModelType());
    }


    @Test
    public void toModelType_invalidMedConEmpty_throwsIllegalValueException() {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon("");
        String expectedMessage = "MedCon: length should not exceed the limit of 30 characters";

        // Ensure that an IllegalValueException is thrown with the expected message
        assertThrows(IllegalValueException.class, expectedMessage, () -> jsonAdaptedMedCon.toModelType());
    }


    @Test
    public void toModelType_invalidMedConTooLong_throwsIllegalValueException() {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(INVALID_MEDCON_TOO_LONG);
        assertThrows(IllegalValueException.class, "MedCon: " + MESSAGE_CONSTRAINTS_LENGTH, ()
                -> jsonAdaptedMedCon.toModelType());
    }

    @Test
    public void constructor_medConObject_createsValidJsonAdaptedMedCon() {
        MedCon medCon = new MedCon(VALID_MEDCON);
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(medCon);
        assertEquals(VALID_MEDCON.toUpperCase(), jsonAdaptedMedCon.getmedConName());
    }


    @Test
    public void getmedConName_validMedCon_returnsCorrectValue() {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(VALID_MEDCON);
        assertEquals(VALID_MEDCON, jsonAdaptedMedCon.getmedConName());
    }
}
