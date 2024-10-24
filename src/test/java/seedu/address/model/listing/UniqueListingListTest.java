package seedu.address.model.listing;

import org.junit.jupiter.api.Test;
import seedu.address.model.listing.exceptions.DuplicateListingException;
import seedu.address.model.listing.exceptions.ListingNotFoundException;
import seedu.address.testutil.ListingBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.TAMPINES;

public class UniqueListingListTest {

    private final UniqueListingList uniqueListingList = new UniqueListingList();

    @Test
    public void contains_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.contains(null));
    }

    @Test
    public void contains_listingNotInList_returnsFalse() {
        assertFalse(uniqueListingList.contains(PASIR_RIS));
    }

    @Test
    public void contains_ListingInList_returnsTrue() {
        uniqueListingList.add(PASIR_RIS);
        assertTrue(uniqueListingList.contains(PASIR_RIS));
    }

    @Test
    public void contains_listingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueListingList.add(PASIR_RIS);
        Listing editedListing = new ListingBuilder(PASIR_RIS).build();
        assertTrue(uniqueListingList.contains(editedListing));
    }

    @Test
    public void add_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.add(null));
    }

    @Test
    public void add_duplicateListing_throwsDuplicateListingException() {
        uniqueListingList.add(PASIR_RIS);
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.add(PASIR_RIS));
    }

    @Test
    public void setListing_nullTargetListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListing(null, PASIR_RIS));
    }

    @Test
    public void setListing_nullEditedListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListing(PASIR_RIS, null));
    }

    @Test
    public void setListing_targetListingNotInList_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> uniqueListingList.setListing(PASIR_RIS, PASIR_RIS));
    }

    @Test
    public void setListing_editedListingIsSameListing_success() {
        uniqueListingList.add(PASIR_RIS);
        uniqueListingList.setListing(PASIR_RIS,PASIR_RIS);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(PASIR_RIS);
        assertEquals(uniqueListingList, expectedUniqueListingList);
    }

    @Test
    public void setListing_editedListingHasSameIdentity_success() {
        uniqueListingList.add(PASIR_RIS);
        Listing editedListing = new ListingBuilder(PASIR_RIS).build();
        uniqueListingList.setListing(PASIR_RIS, editedListing);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(editedListing);
        assertEquals(uniqueListingList, expectedUniqueListingList);
    }

    @Test
    public void setListing_editedListingHasDifferentIdentity_success() {
        uniqueListingList.add(PASIR_RIS);
        uniqueListingList.setListing(PASIR_RIS, TAMPINES);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(TAMPINES);
        assertEquals(uniqueListingList, expectedUniqueListingList);
    }

    @Test
    public void setListing_editedListingHasNonUniqueIdentity_throwsDuplicateListingException() {
        uniqueListingList.add(PASIR_RIS);
        uniqueListingList.add(TAMPINES);
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.setListing(PASIR_RIS, TAMPINES));
    }

    @Test
    public void remove_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.remove(null));
    }

    @Test
    public void remove_listingDoesNotExist_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> uniqueListingList.remove(PASIR_RIS));
    }

    @Test
    public void remove_existingListing_removesListing() {
        uniqueListingList.add(PASIR_RIS);
        uniqueListingList.remove(PASIR_RIS);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListings_nullUniqueListingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListings((UniqueListingList) null));
    }

    @Test
    public void setListings_uniqueListingList_replacesOwnListWithProvidedUniqueListingList() {
        uniqueListingList.add(PASIR_RIS);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(TAMPINES);
        uniqueListingList.setListings(expectedUniqueListingList);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListings((List<Listing>) null));
    }

    @Test
    public void setListings_list_replacesOwnListWithProvidedList() {
        uniqueListingList.add(PASIR_RIS);
        List<Listing> listingList = Collections.singletonList(TAMPINES);
        uniqueListingList.setListings(listingList);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(TAMPINES);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListings_listWithDuplicateListings_throwsDuplicateListingException() {
        List<Listing> listWithDuplicateListings = Arrays.asList(PASIR_RIS, PASIR_RIS);
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.setListings(listWithDuplicateListings));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueListingList.asUnmodifiableObservableList().toString(), uniqueListingList.toString());
    }
}
