package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Model(null));
    }

    @Test
    public void constructor_invalidModel_throwsIllegalArgumentException() {
        String invalidModel = "";
        assertThrows(IllegalArgumentException.class, () -> new Model(invalidModel));
    }

    @Test
    public void isValidModel() {
        // null model
        assertThrows(NullPointerException.class, () -> Model.isValidModel(null));

        // invalid model
        assertFalse(Model.isValidModel("")); // empty string
        assertFalse(Model.isValidModel(" ")); // spaces not allowed
        assertFalse(Model.isValidModel("^")); // only non-alphanumeric characters
        assertFalse(Model.isValidModel("Kona*")); // contains non-alphanumeric characters
        assertFalse(Model.isValidModel("kona")); // first char not capitalised
        assertFalse(Model.isValidModel("kONA")); // first char not capitalised

        // valid model
        assertTrue(Model.isValidModel("12345")); // numbers only
        assertTrue(Model.isValidModel("Kona")); // alphabets only
        assertTrue(Model.isValidModel("370z")); // alphanumeric characters
        assertTrue(Model.isValidModel("Kona 1.6T")); // alphanumeric characters
        assertTrue(Model.isValidModel("KONA")); // all capital letters
        assertTrue(Model.isValidModel("Super Long Car Model Name For Kona 1.6T For Test")); // long models
    }

    @Test
    public void equals() {
        Model model = new Model("Valid Model");

        // same values -> returns true
        assertTrue(model.equals(new Model("Valid Model")));

        // same object -> returns true
        assertTrue(model.equals(model));

        // null -> returns false
        assertFalse(model.equals(null));

        // different types -> returns false
        assertFalse(model.equals(5.0f));

        // different values -> returns false
        assertFalse(model.equals(new Model("Other Valid Model")));
    }
}
