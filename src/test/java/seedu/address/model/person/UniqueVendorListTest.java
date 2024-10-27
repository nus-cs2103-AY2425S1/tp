package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.ALISON;
import static seedu.address.testutil.TypicalVendors.BORIS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateVendorException;
import seedu.address.model.person.exceptions.VendorNotFoundException;
import seedu.address.testutil.VendorBuilder;

public class UniqueVendorListTest {
    private final UniqueVendorList uniqueVendorList = new UniqueVendorList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueVendorList.contains(ALISON));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueVendorList.add(ALISON);
        assertTrue(uniqueVendorList.contains(ALISON));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVendorList.add(ALISON);
        Vendor edited = new VendorBuilder().withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueVendorList.contains(edited));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.add(null));
    }

    @Test
    public void add_duplicateVendor_throwsDuplicateVendorException() {
        uniqueVendorList.add(ALISON);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.add(ALISON));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(null, ALISON));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(ALISON, null));
    }

    @Test
    public void setVendor_targetVendorNotInList_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.setVendor(ALISON, ALISON));
    }

    @Test
    public void setVendor_editedVendorIsSameVendor_success() {
        uniqueVendorList.add(ALISON);
        uniqueVendorList.setVendor(ALISON, ALISON);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(ALISON);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasSameIdentity_success() {
        uniqueVendorList.add(ALISON);
        Vendor edited = new VendorBuilder().withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueVendorList.setVendor(ALISON, edited);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(edited);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasDifferentIdentity_success() {
        uniqueVendorList.add(ALISON);
        uniqueVendorList.setVendor(ALISON, BORIS);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(BORIS);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasNonUniqueIdentity_throwsDuplicateVendorException() {
        uniqueVendorList.add(ALISON);
        uniqueVendorList.add(BORIS);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendor(ALISON, BORIS));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.remove(null));
    }

    @Test
    public void remove_vendorDoesNotExist_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.remove(ALISON));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueVendorList.add(ALISON);
        uniqueVendorList.remove(ALISON);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setPersons_nullUniqueVendorList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((UniqueVendorList) null));
    }

    @Test
    public void setVendors_uniqueVendorList_replacesOwnListWithProvidedUniqueVendorList() {
        uniqueVendorList.add(ALISON);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(BORIS);
        uniqueVendorList.setVendors(expectedUniqueVendorList);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((List<Vendor>) null));
    }

    @Test
    public void setVendors_list_replacesOwnListWithProvidedList() {
        uniqueVendorList.add(ALISON);
        List<Vendor> personList = Collections.singletonList(BORIS);
        uniqueVendorList.setVendors(personList);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(BORIS);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_listWithDuplicateVendors_throwsDuplicateVendorException() {
        List<Vendor> listWithDuplicateVendors = Arrays.asList(ALISON, ALISON);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendors(listWithDuplicateVendors));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueVendorList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueVendorList.asUnmodifiableObservableList().toString(), uniqueVendorList.toString());
    }
}
