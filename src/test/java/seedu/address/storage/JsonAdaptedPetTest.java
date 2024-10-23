package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.MAX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;

public class JsonAdaptedPetTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_BREED = " ";
    private static final String INVALID_SPECIES = " ";
    private static final String INVALID_AGE = "SIXTEEN";
    private static final String INVALID_SEX = "female";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = MAX.getName().toString();
    private static final String VALID_AGE = MAX.getAge().toString();
    private static final String VALID_BREED = MAX.getBreed().toString();
    private static final String VALID_SPECIES = MAX.getSpecies().toString();
    private static final String VALID_SEX = MAX.getSex().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = MAX.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPetDetails_returnsPerson() throws Exception {
        JsonAdaptedPet pet = new JsonAdaptedPet(MAX);
        assertEquals(MAX, pet.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(INVALID_NAME, VALID_SPECIES, VALID_BREED, VALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(null, VALID_SPECIES, VALID_BREED, VALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidBreed_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, INVALID_BREED, VALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = Breed.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullBreed_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, null, VALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Breed.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, INVALID_SPECIES, VALID_BREED, VALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = Species.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_NAME, null, VALID_BREED, VALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Species.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, VALID_BREED, INVALID_AGE, VALID_SEX, VALID_TAGS);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, VALID_BREED, null, VALID_SEX, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidSex_throwsIllegalValueException() {
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, VALID_BREED, VALID_AGE, INVALID_SEX, VALID_TAGS);
        String expectedMessage = Sex.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullSex_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, VALID_BREED, VALID_AGE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPet pet =
                new JsonAdaptedPet(VALID_NAME, VALID_SPECIES, VALID_BREED, VALID_AGE, VALID_SEX, invalidTags);
        assertThrows(IllegalValueException.class, pet::toModelType);
    }

}
