package seedu.address.model.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_FLUFFY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.BELLA;
import static seedu.address.testutil.TypicalPets.FLUFFY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.pet.Pet;
import seedu.address.model.pet.exceptions.DuplicatePetException;
import seedu.address.model.pet.exceptions.PetNotFoundException;
import seedu.address.testutil.PetBuilder;

public class LinkedPetListTest {

    private final LinkedPetList linkedPetList = new LinkedPetList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(linkedPetList.contains(BELLA));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        linkedPetList.add(BELLA);
        assertTrue(linkedPetList.contains(BELLA));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        linkedPetList.add(BELLA);
        Pet editedBella = new PetBuilder(BELLA).withAge(VALID_AGE_FLUFFY).build();
        assertFalse(linkedPetList.contains(editedBella));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        linkedPetList.add(BELLA);
        assertThrows(DuplicatePetException.class, () -> linkedPetList.add(BELLA));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.setPet(null, BELLA));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.setPet(BELLA, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> linkedPetList.setPet(BELLA, BELLA));
    }

    @Test
    public void resetList_success() {
        linkedPetList.add(BELLA);
        linkedPetList.add(FLUFFY);
        linkedPetList.resetList();
        assertEquals(linkedPetList.getList().size(), 0);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        linkedPetList.add(BELLA);
        linkedPetList.setPet(BELLA, BELLA);
        LinkedPetList expectedlinkedPersonList = new LinkedPetList();
        expectedlinkedPersonList.add(BELLA);
        assertEquals(expectedlinkedPersonList, linkedPetList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        linkedPetList.add(BELLA);
        Pet editedBella = new PetBuilder(BELLA).withAge(VALID_AGE_FLUFFY).build();
        linkedPetList.setPet(BELLA, editedBella);
        LinkedPetList expectedlinkedPersonList = new LinkedPetList();
        expectedlinkedPersonList.add(editedBella);
        assertEquals(expectedlinkedPersonList, linkedPetList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        linkedPetList.add(BELLA);
        linkedPetList.setPet(BELLA, FLUFFY);
        LinkedPetList expectedlinkedPetList = new LinkedPetList();
        expectedlinkedPetList.add(FLUFFY);
        assertEquals(expectedlinkedPetList, linkedPetList);
    }

    @Test
    public void setPerson_editedPersonHasNonlinkedIdentity_throwsDuplicatePersonException() {
        linkedPetList.add(BELLA);
        linkedPetList.add(FLUFFY);
        assertThrows(DuplicatePetException.class, () -> linkedPetList.setPet(BELLA, FLUFFY));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> linkedPetList.remove(BELLA));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        linkedPetList.add(BELLA);
        linkedPetList.remove(BELLA);
        LinkedPetList expectedlinkedPetList = new LinkedPetList();
        assertEquals(expectedlinkedPetList, linkedPetList);
    }

    @Test
    public void setPersons_nulllinkedPersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.setPets((LinkedPetList) null));
    }

    @Test
    public void setPersons_linkedPersonList_replacesOwnListWithProvidedlinkedPersonList() {
        linkedPetList.add(BELLA);
        LinkedPetList expectedlinkedPetList = new LinkedPetList();
        expectedlinkedPetList.add(FLUFFY);
        linkedPetList.setPets(expectedlinkedPetList);
        assertEquals(expectedlinkedPetList, linkedPetList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedPetList.setPets((List<Pet>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        linkedPetList.add(BELLA);
        List<Pet> personList = Collections.singletonList(FLUFFY);
        linkedPetList.setPets(personList);
        LinkedPetList expectedlinkedPetList = new LinkedPetList();
        expectedlinkedPetList.add(FLUFFY);
        assertEquals(expectedlinkedPetList, linkedPetList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Pet> listWithDuplicatePersons = Arrays.asList(BELLA, BELLA);
        assertThrows(DuplicatePetException.class, () -> linkedPetList.setPets(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> linkedPetList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(linkedPetList.asUnmodifiableObservableList().toString(), linkedPetList.toString());
    }
}
