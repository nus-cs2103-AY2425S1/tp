package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_FLUFFY;
import static seedu.address.testutil.TypicalPets.BELLA;
import static seedu.address.testutil.TypicalPets.FLUFFY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PetBuilder;

public class PetTest {

    @Test
    public void isSamePet() {
        // same object -> returns true
        assertTrue(BELLA.isSamePet(BELLA));

        // name differs in case, all other attributes same -> returns true
        Pet editedFluffy = new PetBuilder(FLUFFY).withName(VALID_NAME_FLUFFY.toLowerCase()).build();
        assertTrue(FLUFFY.isSamePet(editedFluffy));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_FLUFFY + " ";
        editedFluffy = new PetBuilder(FLUFFY).withName(nameWithTrailingSpaces).build();
        assertTrue(FLUFFY.isSamePet(editedFluffy));

        // null -> returns false
        assertFalse(BELLA.isSamePet(null));

        // same name, all other attributes different -> returns false
        Pet editedBella = new PetBuilder(FLUFFY).withSpecies(VALID_SPECIES_FLUFFY).withBreed(VALID_BREED_FLUFFY)
                .withAge(VALID_AGE_FLUFFY).withSex(VALID_SEX_FLUFFY).build();
        assertFalse(BELLA.isSamePet(editedBella));

        // different name, all other attributes same -> returns false
        editedBella = new PetBuilder(BELLA).withName(VALID_NAME_FLUFFY).build();
        assertFalse(BELLA.isSamePet(editedBella));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pet bellaCopy = new PetBuilder(BELLA).build();
        assertTrue(BELLA.equals(bellaCopy));

        // same object -> returns true
        assertTrue(BELLA.equals(BELLA));

        // null -> returns false
        assertFalse(BELLA.equals(null));

        // different type -> returns false
        assertFalse(BELLA.equals(5));

        // different Pet -> returns false
        assertFalse(BELLA.equals(FLUFFY));

        // different name -> returns false
        Pet editedBella = new PetBuilder(BELLA).withName(VALID_NAME_FLUFFY).build();
        assertFalse(BELLA.equals(editedBella));

        // different species -> returns false
        editedBella = new PetBuilder(BELLA).withSpecies(VALID_SPECIES_FLUFFY).build();
        assertFalse(BELLA.equals(editedBella));

        // different breed -> returns false
        editedBella = new PetBuilder(BELLA).withBreed(VALID_BREED_FLUFFY).build();
        assertFalse(BELLA.equals(editedBella));

        // different age -> returns false
        editedBella = new PetBuilder(BELLA).withAge(VALID_AGE_FLUFFY).build();
        assertFalse(BELLA.equals(editedBella));

        // different sex -> returns false
        editedBella = new PetBuilder(BELLA).withSex(VALID_SEX_FLUFFY).build();
        assertFalse(BELLA.equals(editedBella));
    }

    @Test
    public void toStringMethod() {
        String expected = Pet.class.getCanonicalName() + "{uniqueId=" + BELLA.getUniqueID()
                + ", name=" + BELLA.getName() + ", species=" + BELLA.getSpecies() + ", breed=" + BELLA.getBreed()
                + ", age=" + BELLA.getAge() + ", sex=" + BELLA.getSex() + "}";
        assertEquals(expected, BELLA.toString());
    }
}
