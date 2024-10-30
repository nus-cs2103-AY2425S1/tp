package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CarModelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CarModel(null));
    }

    @Test
    public void constructor_invalidCarModel_throwsIllegalArgumentException() {
        String invalidCarModel = "";
        assertThrows(IllegalArgumentException.class, () -> new CarModel(invalidCarModel));
    }

    @Test
    public void isValidCarModel() {
        // null model
        assertThrows(NullPointerException.class, () -> CarModel.isValidCarModel(null));

        // invalid model
        assertFalse(CarModel.isValidCarModel("")); // empty string
        assertFalse(CarModel.isValidCarModel(" ")); // spaces not allowed
        assertFalse(CarModel.isValidCarModel("^")); // only non-alphanumeric characters
        assertFalse(CarModel.isValidCarModel("Kona*")); // contains non-alphanumeric characters
        assertFalse(CarModel.isValidCarModel("kona")); // first char not capitalised
        assertFalse(CarModel.isValidCarModel("kONA")); // first char not capitalised

        // valid model
        assertTrue(CarModel.isValidCarModel("12345")); // numbers only
        assertTrue(CarModel.isValidCarModel("Kona")); // alphabets only
        assertTrue(CarModel.isValidCarModel("370z")); // alphanumeric characters
        assertTrue(CarModel.isValidCarModel("Kona 1.6T")); // alphanumeric characters
        assertTrue(CarModel.isValidCarModel("KONA")); // all capital letters

        // test for length of model
        assertTrue(CarModel.isValidCarModel("A12345678901234567890123456789012345678")); // 39 characters
        assertTrue(CarModel.isValidCarModel("A123456789012345678901234567890123456789")); // 40 characters
        assertFalse(CarModel.isValidCarModel("A1234567890123456789012345678901234567890")); // 41 characters
        assertFalse(CarModel.isValidCarModel("A12345678901234567890123456789012345678901")); // 42 characters
        assertFalse(CarModel.isValidCarModel("A123456789012345678901234567890123456789012")); // 43 characters
        assertFalse(CarModel.isValidCarModel("Super Long Car Model Name For Test-123.456")); // long models
    }

    @Test
    public void equals() {
        CarModel carModel = new CarModel("Valid CarModel");

        // same values -> returns true
        assertTrue(carModel.equals(new CarModel("Valid CarModel")));

        // same object -> returns true
        assertTrue(carModel.equals(carModel));

        // null -> returns false
        assertFalse(carModel.equals(null));

        // different types -> returns false
        assertFalse(carModel.equals(5.0f));

        // different values -> returns false
        assertFalse(carModel.equals(new CarModel("Other Valid CarModel")));
    }
}
