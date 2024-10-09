package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BreedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Breed(null));
    }

    @Test
    public void constructor_invalidBreed_throwsIllegalArgumentException() {
        String invalidBreed = "";
        assertThrows(IllegalArgumentException.class, () -> new Breed(invalidBreed));
    }

    @Test
    public void isValidBreed() {
        // null breed
        assertThrows(NullPointerException.class, () -> Breed.isValidBreed(null));

        // invalid breed
        assertFalse(Breed.isValidBreed("")); // empty string
        assertFalse(Breed.isValidBreed(" ")); // spaces only
        assertFalse(Breed.isValidBreed("12345")); // numbers only
        assertFalse(Breed.isValidBreed("^")); // only non-alphanumeric characters
        assertFalse(Breed.isValidBreed("dog*")); // contains non-alphanumeric characters

        // valid breed
        assertTrue(Breed.isValidBreed("siamese")); // alphabets only
        assertTrue(Breed.isValidBreed("Dwarf Hamster")); // with capital letters
        assertTrue(Breed.isValidBreed("3 toed sloth")); // alphanumeric characters
        assertTrue(Breed.isValidBreed("bengal-cat")); // with hyphen
        assertTrue(Breed.isValidBreed("labrador retriever")); // long names
    }

    @Test
    public void equals() {
        Breed breed = new Breed("Valid Breed");

        // same values -> returns true
        assertTrue(breed.equals(new Breed("Valid Breed")));

        // same object -> returns true
        assertTrue(breed.equals(breed));

        // null -> returns false
        assertFalse(breed.equals(null));

        // different types -> returns false
        assertFalse(breed.equals(5.0f));

        // different values -> returns false
        assertFalse(breed.equals(new Breed("Other Valid Breed")));
    }
}
