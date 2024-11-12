package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.TAMPINES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.listing.exceptions.DuplicateListingException;
import seedu.address.model.listing.exceptions.ListingNotFoundException;
import seedu.address.testutil.ListingBuilder;

public class UniqueListingListTest {

    private final UniqueListingList UNIQUE_LISTING_LIST = new UniqueListingList();

    @Test
    public void contains_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.contains(null));
    }

    @Test
    public void contains_listingNotInList_returnsFalse() {
        assertFalse(UNIQUE_LISTING_LIST.contains(PASIR_RIS));
    }

    @Test
    public void contains_listingInList_returnsTrue() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);

        assertTrue(UNIQUE_LISTING_LIST.contains(PASIR_RIS));
    }

    @Test
    public void contains_listingWithSameIdentityFieldsInList_returnsTrue() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        Listing editedListing = new ListingBuilder(PASIR_RIS).build();

        assertTrue(UNIQUE_LISTING_LIST.contains(editedListing));
    }

    @Test
    public void add_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.add(null));
    }

    @Test
    public void add_duplicateListing_throwsDuplicateListingException() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);

        assertThrows(DuplicateListingException.class, () -> UNIQUE_LISTING_LIST.add(PASIR_RIS));
    }

    @Test
    public void setListing_nullTargetListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.setListing(null, PASIR_RIS));
    }

    @Test
    public void setListing_nullEditedListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.setListing(PASIR_RIS, null));
    }

    @Test
    public void setListing_targetListingNotInList_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> UNIQUE_LISTING_LIST.setListing(PASIR_RIS, PASIR_RIS));
    }

    @Test
    public void setListing_editedListingIsSameListing_success() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        UNIQUE_LISTING_LIST.setListing(PASIR_RIS, PASIR_RIS);

        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(PASIR_RIS);

        assertEquals(UNIQUE_LISTING_LIST, expectedUniqueListingList);
    }

    @Test
    public void setListing_editedListingHasSameIdentity_success() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        Listing editedListing = new ListingBuilder(PASIR_RIS).build();
        UNIQUE_LISTING_LIST.setListing(PASIR_RIS, editedListing);

        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(editedListing);

        assertEquals(UNIQUE_LISTING_LIST, expectedUniqueListingList);
    }

    @Test
    public void setListing_editedListingHasDifferentIdentity_success() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        UNIQUE_LISTING_LIST.setListing(PASIR_RIS, TAMPINES);

        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(TAMPINES);

        assertEquals(UNIQUE_LISTING_LIST, expectedUniqueListingList);
    }

    @Test
    public void setListing_editedListingHasNonUniqueIdentity_throwsDuplicateListingException() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        UNIQUE_LISTING_LIST.add(TAMPINES);

        assertThrows(DuplicateListingException.class, () -> UNIQUE_LISTING_LIST.setListing(PASIR_RIS, TAMPINES));
    }

    @Test
    public void remove_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.remove(null));
    }

    @Test
    public void remove_listingDoesNotExist_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> UNIQUE_LISTING_LIST.remove(PASIR_RIS));
    }

    @Test
    public void remove_existingListing_removesListing() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        UNIQUE_LISTING_LIST.remove(PASIR_RIS);

        UniqueListingList expectedUniqueListingList = new UniqueListingList();

        assertEquals(expectedUniqueListingList, UNIQUE_LISTING_LIST);
    }

    @Test
    public void setListings_nullUniqueListingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.setListings((UniqueListingList) null));
    }

    @Test
    public void setListings_uniqueListingList_replacesOwnListWithProvidedUniqueListingList() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);

        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(TAMPINES);

        UNIQUE_LISTING_LIST.setListings(expectedUniqueListingList);
        assertEquals(expectedUniqueListingList, UNIQUE_LISTING_LIST);
    }

    @Test
    public void setListings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_LISTING_LIST.setListings((List<Listing>) null));
    }

    @Test
    public void setListings_list_replacesOwnListWithProvidedList() {
        UNIQUE_LISTING_LIST.add(PASIR_RIS);
        List<Listing> listingList = Collections.singletonList(TAMPINES);
        UNIQUE_LISTING_LIST.setListings(listingList);

        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(TAMPINES);

        assertEquals(expectedUniqueListingList, UNIQUE_LISTING_LIST);
    }

    @Test
    public void setListings_listWithDuplicateListings_throwsDuplicateListingException() {
        List<Listing> listWithDuplicateListings = Arrays.asList(PASIR_RIS, PASIR_RIS);

        assertThrows(DuplicateListingException.class, () -> UNIQUE_LISTING_LIST.setListings(listWithDuplicateListings));
    }

    @Test
    public void toStringMethod() {
        assertEquals(UNIQUE_LISTING_LIST.asUnmodifiableObservableList().toString(), UNIQUE_LISTING_LIST.toString());
    }
}
