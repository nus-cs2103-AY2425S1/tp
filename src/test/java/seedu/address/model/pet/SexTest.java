package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PetBuilder;

public class SexTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sex(null));
    }

    @Test
    public void constructor_invalidSex_throwsIllegalArgumentException() {
        String invalidSex = "";
        assertThrows(IllegalArgumentException.class, () -> new Sex(invalidSex));
    }

    @Test
    public void isValidSex() {
        // null sex
        assertThrows(NullPointerException.class, () -> Sex.isValidSex(null));

        // invalid sex
        assertFalse(Sex.isValidSex("")); // empty string
        assertFalse(Sex.isValidSex(" ")); // spaces only
        assertFalse(Sex.isValidSex("male")); // more than one character
        assertFalse(Sex.isValidSex("*")); // contains non-alphanumeric characters
        assertFalse(Sex.isValidSex("G")); // contains neither 'M' nor 'F' (for male/female)

        // valid sex
        assertTrue(Sex.isValidSex("F")); // one character only and is 'M' or 'F'
        assertTrue(Name.isValidName("M")); // one character only and is 'M' or 'F'
        assertTrue(Name.isValidName("f")); // lowercase 'm' or 'f'
        assertTrue(Name.isValidName("m")); // lowercase 'm' or 'f'
    }

    @Test
    public void testToString() {
        Pet validFemalePet1 = new PetBuilder().withName("Fluffy").withSpecies("Dog").withBreed("Golden Retriever")
                .withAge("5").withSex("F").build();
        Pet validFemalePet2 = new PetBuilder().withName("Fluffy").withSpecies("Dog").withBreed("Golden Retriever")
                .withAge("5").withSex("f").build();
        Pet validMalePet1 = new PetBuilder().withName("Tubby").withSpecies("Turtle").withBreed("Pelomedusidae")
                .withAge("2").withSex("M").build();
        Pet validMalePet2 = new PetBuilder().withName("Tubby").withSpecies("Turtle").withBreed("Pelomedusidae")
                .withAge("2").withSex("m").build();

        // valid toString output
        assertEquals("Female", validFemalePet1.getSex().toString());
        assertEquals("Female", validFemalePet2.getSex().toString());
        assertEquals("Male", validMalePet1.getSex().toString());
        assertEquals("Male", validMalePet2.getSex().toString());

        // invalid toString output
        assertNotEquals("F", validFemalePet1.getSex().toString());
        assertNotEquals("f", validFemalePet2.getSex().toString());
        assertNotEquals("M", validMalePet1.getSex().toString());
        assertNotEquals("m", validMalePet2.getSex().toString());
    }

    @Test
    public void equals() {
        Sex sex = new Sex("F");

        // same values -> returns true
        assertTrue(sex.equals(new Sex("F")));

        // same object -> returns true
        assertTrue(sex.equals(sex));

        // null -> returns false
        assertFalse(sex.equals(null));

        // different types -> returns false
        assertFalse(sex.equals(5.0f));

        // different values -> returns false
        assertFalse(sex.equals(new Sex("m")));
    }
}
