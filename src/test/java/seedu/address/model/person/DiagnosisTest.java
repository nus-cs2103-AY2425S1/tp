package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DiagnosisTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Diagnosis(null));
    }

    @Test
    public void constructor_invalidDiagnosis_throwsIllegalArgumentException() {
        String invalidDiagnosis = "";
        assertThrows(IllegalArgumentException.class, () -> new Diagnosis(invalidDiagnosis));
    }

    @Test
    public void isValidDiagnosis() {
        // null Diagnosis
        assertThrows(NullPointerException.class, () -> Diagnosis.isValidDiagnosis(null));

        // invalid Diagnosis
        assertFalse(Diagnosis.isValidDiagnosis("")); // empty string
        assertFalse(Diagnosis.isValidDiagnosis("    ")); // spaces only
        assertFalse(Diagnosis.isValidDiagnosis("()-3351!*#&$!#")); // only non-alphabetic characters
        assertFalse(Diagnosis.isValidDiagnosis("This diagnosis is super duper duper duper duper duper duper "
                + "long and is most definitely longer than 100 characters")); // longer than 100 characters

        // valid Diagnosis
        assertTrue(Diagnosis.isValidDiagnosis("coughing and bleeding")); // only lowercase alphabetic characters
        assertTrue(Diagnosis.isValidDiagnosis("Coughing and Bleeding")); // mix of upper and lower case
        assertTrue(Diagnosis.isValidDiagnosis("Coughing, Bleeding and Wheezing!")); // with special characters
        assertTrue(Diagnosis.isValidDiagnosis("Coughing on 3 occasions")); // with digit
    }

    @Test
    public void equals() {
        Diagnosis diagnosis = new Diagnosis("Valid Diagnosis");

        // same values -> returns true
        assertTrue(diagnosis.equals(new Diagnosis("Valid Diagnosis")));

        // same object -> returns true
        assertTrue(diagnosis.equals(diagnosis));

        // null -> returns false
        assertFalse(diagnosis.equals(null));

        // different types -> returns false
        assertFalse(diagnosis.equals(5.0f));

        // different values -> returns false
        assertFalse(diagnosis.equals(new Diagnosis("Other Valid Diagnosis")));
    }
}
