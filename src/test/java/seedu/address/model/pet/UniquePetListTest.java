package seedu.address.model.pet;

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

import seedu.address.model.pet.exceptions.DuplicatePetException;
import seedu.address.model.pet.exceptions.PetNotFoundException;
import seedu.address.testutil.PetBuilder;

public class UniquePetListTest {

    private final UniquePetList uniquePetList = new UniquePetList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePetList.contains(BELLA));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePetList.add(BELLA);
        assertTrue(uniquePetList.contains(BELLA));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePetList.add(BELLA);
        Pet editedBella = new PetBuilder(BELLA).withAge(VALID_AGE_FLUFFY).build();
        assertFalse(uniquePetList.contains(editedBella));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePetList.add(BELLA);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.add(BELLA));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPet(null, BELLA));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPet(BELLA, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> uniquePetList.setPet(BELLA, BELLA));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePetList.add(BELLA);
        uniquePetList.setPet(BELLA, BELLA);
        UniquePetList expectedUniquePersonList = new UniquePetList();
        expectedUniquePersonList.add(BELLA);
        assertEquals(expectedUniquePersonList, uniquePetList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePetList.add(BELLA);
        Pet editedBella = new PetBuilder(BELLA).withAge(VALID_AGE_FLUFFY).build();
        uniquePetList.setPet(BELLA, editedBella);
        UniquePetList expectedUniquePersonList = new UniquePetList();
        expectedUniquePersonList.add(editedBella);
        assertEquals(expectedUniquePersonList, uniquePetList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePetList.add(BELLA);
        uniquePetList.setPet(BELLA, FLUFFY);
        UniquePetList expectedUniquePersonList = new UniquePetList();
        expectedUniquePersonList.add(FLUFFY);
        assertEquals(expectedUniquePersonList, uniquePetList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePetList.add(BELLA);
        uniquePetList.add(FLUFFY);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.setPet(BELLA, FLUFFY));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PetNotFoundException.class, () -> uniquePetList.remove(BELLA));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePetList.add(BELLA);
        uniquePetList.remove(BELLA);
        UniquePetList expectedUniquePersonList = new UniquePetList();
        assertEquals(expectedUniquePersonList, uniquePetList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPets((UniquePetList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePetList.add(BELLA);
        UniquePetList expectedUniquePersonList = new UniquePetList();
        expectedUniquePersonList.add(FLUFFY);
        uniquePetList.setPets(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePetList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePetList.setPets((List<Pet>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePetList.add(BELLA);
        List<Pet> personList = Collections.singletonList(FLUFFY);
        uniquePetList.setPets(personList);
        UniquePetList expectedUniquePersonList = new UniquePetList();
        expectedUniquePersonList.add(FLUFFY);
        assertEquals(expectedUniquePersonList, uniquePetList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Pet> listWithDuplicatePersons = Arrays.asList(BELLA, BELLA);
        assertThrows(DuplicatePetException.class, () -> uniquePetList.setPets(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePetList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePetList.asUnmodifiableObservableList().toString(), uniquePetList.toString());
    }
}
