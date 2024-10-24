package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PET_BELLA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PET_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_FLUFFY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.testutil.EditPetDescriptorBuilder;

public class EditPetDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPetDescriptor descriptorWithSameValues = new EditPetDescriptor(DESC_PET_BELLA);
        assertTrue(DESC_PET_BELLA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PET_BELLA.equals(DESC_PET_BELLA));

        // null -> returns false
        assertFalse(DESC_PET_BELLA.equals(null));

        // different types -> returns false
        assertFalse(DESC_PET_BELLA.equals(5));

        // different values -> returns false
        assertFalse(DESC_PET_BELLA.equals(DESC_PET_FLUFFY));

        // different name -> returns false
        EditPetDescriptor editedBella = new EditPetDescriptorBuilder(DESC_PET_BELLA).withName(VALID_NAME_FLUFFY)
                .build();
        assertFalse(DESC_PET_BELLA.equals(editedBella));

        // different species -> returns false
        editedBella = new EditPetDescriptorBuilder(DESC_PET_BELLA).withSpecies(VALID_SPECIES_FLUFFY).build();
        assertFalse(DESC_PET_BELLA.equals(editedBella));

        // different breed -> returns false
        editedBella = new EditPetDescriptorBuilder(DESC_PET_BELLA).withBreed(VALID_BREED_FLUFFY).build();
        assertFalse(DESC_PET_BELLA.equals(editedBella));

        // different age -> returns false
        editedBella = new EditPetDescriptorBuilder(DESC_PET_BELLA).withAge(VALID_AGE_FLUFFY).build();
        assertFalse(DESC_PET_BELLA.equals(editedBella));

        // different sex -> returns false
        editedBella = new EditPetDescriptorBuilder(DESC_PET_BELLA).withSex(VALID_SEX_FLUFFY).build();
        assertFalse(DESC_PET_BELLA.equals(editedBella));
    }

    @Test
    public void toStringMethod() {
        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();
        String expected = EditPetDescriptor.class.getCanonicalName() + "{name="
                + editPetDescriptor.getName().orElse(null) + ", species="
                + editPetDescriptor.getSpecies().orElse(null) + ", breed="
                + editPetDescriptor.getBreed().orElse(null) + ", age="
                + editPetDescriptor.getAge().orElse(null) + ", sex="
                + editPetDescriptor.getSex().orElse(null) + "}";
        assertEquals(expected, editPetDescriptor.toString());
    }
}
