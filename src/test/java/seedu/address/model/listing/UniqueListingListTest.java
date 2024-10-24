package seedu.address.model.listing;

import org.junit.jupiter.api.Test;
import seedu.address.model.listing.exceptions.DuplicateListingException;
import seedu.address.testutil.ListingBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;

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
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListing(PASIR_RIS, PASIR_RIS));
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

}
