package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_FLUFFY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PawPatrol;
import seedu.address.model.pet.Pet;

/**
 * A utility class containing a list of {@code Pet} objects to be used in tests.
 */
public class TypicalPets {

    public static final Pet MAX = new PetBuilder().withName("Max")
            .withSpecies("Dog").withBreed("Golden Retriever")
            .withAge("7").withSex("M").build();
    public static final Pet MILO = new PetBuilder().withName("Milo")
            .withSpecies("Cat").withBreed("Tabby")
            .withAge("1").withSex("M").build();
    public static final Pet BISON = new PetBuilder().withName("Bison")
            .withSpecies("Dog").withBreed("Irish Wolfhound")
            .withAge("18").withSex("M").build();
    public static final Pet DAISY = new PetBuilder().withName("Daisy")
            .withSpecies("Rabbit").withBreed("Lionhead")
            .withAge("5").withSex("F").build();
    public static final Pet RUBY = new PetBuilder().withName("Ruby")
            .withSpecies("Hamster").withBreed("Roborovski")
            .withAge("2").withSex("F").build();

    // Manually added - Pet's details found in {@code CommandTestUtil}
    public static final Pet FLUFFY = new PetBuilder().withName(VALID_NAME_FLUFFY)
            .withSpecies(VALID_SPECIES_FLUFFY).withBreed(VALID_BREED_FLUFFY)
            .withAge(VALID_AGE_FLUFFY).withSex(VALID_SEX_FLUFFY).build();
    public static final Pet BELLA = new PetBuilder().withName(VALID_NAME_BELLA)
            .withSpecies(VALID_SPECIES_BELLA).withBreed(VALID_BREED_BELLA)
            .withAge(VALID_AGE_BELLA).withSex(VALID_SEX_BELLA).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPets() {
    } // prevents instantiation

    /**
     * Returns an {@code PawPatrol} with all the typical pets.
     */
    public static PawPatrol getTypicalPawPatrol() {
        PawPatrol ab = new PawPatrol();
        for (Pet pet : getTypicalPets()) {
            ab.addPet(pet);
        }
        return ab;
    }

    public static List<Pet> getTypicalPets() {
        return new ArrayList<>(Arrays.asList(MAX, MILO, BISON, DAISY, RUBY));
    }
}
