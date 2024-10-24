package seedu.address.model.listing;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ListingBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PASIR_RIS;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.TAMPINES;

public class ListingTest {
    @Test
    public void isSameListing() {
        // name address and seller

        // same object -> returns true
        assertTrue(PASIR_RIS.isSameListing(PASIR_RIS));

        // null -> returns false
        assertFalse(PASIR_RIS.isSameListing(null));

        // same name, address, seller all other attributes different -> returns true
        Listing editedListing = new ListingBuilder(PASIR_RIS).withArea(TAMPINES.getArea())
                .withPrice(TAMPINES.getPrice()).build();
        assertTrue(PASIR_RIS.isSameListing(editedListing));

        // different name, all other attributes same -> returns false
        editedListing = new ListingBuilder(PASIR_RIS).withName(TAMPINES.getName()).build();
        assertFalse(PASIR_RIS.isSameListing(editedListing));

        // different address, all other attributes same -> returns false
        editedListing = new ListingBuilder(PASIR_RIS).withAddress(TAMPINES.getAddress()).build();
        assertFalse(PASIR_RIS.isSameListing(editedListing));

        // different seller, all other attributes same -> returns false
        editedListing = new ListingBuilder(PASIR_RIS).withSeller(TAMPINES.getSeller()).build();
        assertFalse(PASIR_RIS.isSameListing(editedListing));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_PASIR_RIS + " ";
        editedListing = new ListingBuilder(PASIR_RIS).withName(nameWithTrailingSpaces).build();
        assertFalse(PASIR_RIS.isSameListing(editedListing));
    }

    @Test
    public void equals() {

        // same values -> return true
        Listing listingCopy = new ListingBuilder(PASIR_RIS).build();
        assertTrue(PASIR_RIS.equals(listingCopy));

        // same object -> return true
        assertTrue(PASIR_RIS.equals(PASIR_RIS));

        // null -> return false
        assertFalse(PASIR_RIS.equals(null));

        // different type -> return false
        assertFalse(PASIR_RIS.equals(5));

        // different listing -> return false
        assertFalse(PASIR_RIS.equals(TAMPINES));

        // different name -> return false
        Listing editedListing = new ListingBuilder(PASIR_RIS).withName(TAMPINES.getName()).build();
        assertFalse(PASIR_RIS.equals(editedListing));

        // different address -> return false
        editedListing = new ListingBuilder(PASIR_RIS).withAddress(TAMPINES.getAddress()).build();
        assertFalse(PASIR_RIS.equals(editedListing));

        // different seller -> return false
        editedListing = new ListingBuilder(PASIR_RIS).withSeller(TAMPINES.getSeller()).build();
        assertFalse(TAMPINES.equals(editedListing));
    }

    @Test
    public void toStringMethod() {
        String expected = Listing.class.getCanonicalName()
                + "{name=" + PASIR_RIS.getName()
                + ", address=" + PASIR_RIS.getAddress()
                + ", area=" + PASIR_RIS.getArea()
                + ", region=" + PASIR_RIS.getRegion()
                + ", seller=" + PASIR_RIS.getSeller() + "}";
        assertEquals(expected, PASIR_RIS.toString());
    }
}
