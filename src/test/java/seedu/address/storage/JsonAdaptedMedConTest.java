package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.MedCon;

public class JsonAdaptedMedConTest {

    private static final String VALID_MEDCON = "Diabetes";
    private static final String INVALID_MEDCON = null;

    @Test
    public void toModelType_validMedCon_returnsMedCon() throws Exception {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(VALID_MEDCON);
        MedCon expectedMedCon = new MedCon(VALID_MEDCON);
        assertEquals(expectedMedCon, jsonAdaptedMedCon.toModelType());
    }

    @Test
    public void constructor_medConObject_createsValidJsonAdaptedMedCon() {
        MedCon medCon = new MedCon(VALID_MEDCON);
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(medCon);
        assertEquals(VALID_MEDCON, jsonAdaptedMedCon.getmedConName());
    }

    @Test
    public void getmedConName_validMedCon_returnsCorrectValue() {
        JsonAdaptedMedCon jsonAdaptedMedCon = new JsonAdaptedMedCon(VALID_MEDCON);
        assertEquals(VALID_MEDCON, jsonAdaptedMedCon.getmedConName());
    }
}
