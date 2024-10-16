package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SpeciesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Species(null));
    }

    @Test
    public void constructor_invalidSpecies_throwsIllegalArgumentException() {
        String invalidSpecies = "";
        assertThrows(IllegalArgumentException.class, () -> new Species(invalidSpecies));
    }

    @Test
    public void isValidSpecies() {
        // null species
        assertThrows(NullPointerException.class, () -> Species.isValidSpecies(null));

        // invalid species
        assertFalse(Species.isValidSpecies("")); // empty string
        assertFalse(Species.isValidSpecies(" ")); // spaces only
        assertFalse(Species.isValidSpecies("12345")); // numbers only
        assertFalse(Species.isValidSpecies("^")); // only non-alphanumeric characters
        assertFalse(Species.isValidSpecies("1stparrot")); // alphanumeric characters
        assertFalse(Species.isValidSpecies("parrot the first")); // more than one word
        assertFalse(Species.isValidSpecies("dog*")); // contains non-alphanumeric characters

        // valid species
        assertTrue(Species.isValidSpecies("cat")); // alphabets only
        assertTrue(Species.isValidSpecies("Hamster")); // with capital letters
        assertTrue(Species.isValidSpecies("Parakeet")); // long names
    }

    @Test
    public void equals() {
        Species species = new Species("Valid Species");

        // same values -> returns true
        assertTrue(species.equals(new Species("Valid Species")));

        // same object -> returns true
        assertTrue(species.equals(species));

        // null -> returns false
        assertFalse(species.equals(null));

        // different types -> returns false
        assertFalse(species.equals(5.0f));

        // different values -> returns false
        assertFalse(species.equals(new Species("Other Valid Species")));
    }
}
