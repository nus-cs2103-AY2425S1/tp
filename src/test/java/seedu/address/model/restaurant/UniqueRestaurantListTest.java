package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.restaurant.exceptions.DuplicatePersonException;
import seedu.address.model.restaurant.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueRestaurantListTest {

    private final UniqueRestaurantList uniqueRestaurantList = new UniqueRestaurantList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueRestaurantList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueRestaurantList.add(ALICE);
        assertTrue(uniqueRestaurantList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRestaurantList.add(ALICE);
        Restaurant editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueRestaurantList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueRestaurantList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueRestaurantList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueRestaurantList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueRestaurantList.add(ALICE);
        uniqueRestaurantList.setPerson(ALICE, ALICE);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(ALICE);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueRestaurantList.add(ALICE);
        Restaurant editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRestaurantList.setPerson(ALICE, editedAlice);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(editedAlice);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueRestaurantList.add(ALICE);
        uniqueRestaurantList.setPerson(ALICE, BOB);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(BOB);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueRestaurantList.add(ALICE);
        uniqueRestaurantList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueRestaurantList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueRestaurantList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueRestaurantList.add(ALICE);
        uniqueRestaurantList.remove(ALICE);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.setPersons((UniqueRestaurantList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueRestaurantList.add(ALICE);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(BOB);
        uniqueRestaurantList.setPersons(expectedUniqueRestaurantList);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRestaurantList.setPersons((List<Restaurant>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueRestaurantList.add(ALICE);
        List<Restaurant> restaurantList = Collections.singletonList(BOB);
        uniqueRestaurantList.setPersons(restaurantList);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(BOB);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Restaurant> listWithDuplicateRestaurants = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueRestaurantList.setPersons(listWithDuplicateRestaurants));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRestaurantList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueRestaurantList.asUnmodifiableObservableList().toString(), uniqueRestaurantList.toString());
    }
}
