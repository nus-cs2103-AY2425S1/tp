package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.testutil.ListingBuilder;
import seedu.address.testutil.TypicalListings;
import seedu.address.testutil.TypicalPersons;

public class ListingsTest {

    private final Listings listings = new Listings();
    private final Listing sampleListing = new ListingBuilder().withName("Sample Listing").build();

    @Test
    public void equals() {
        assertTrue(listings.equals(listings));

        assertFalse(listings.equals(null));

        assertFalse(listings.equals("string"));

        Listings sameListings = new Listings();
        sameListings.addListing(sampleListing);
        listings.addListing(sampleListing);
        assertTrue(listings.equals(sameListings));

        Listings differentListings = new Listings();
        differentListings.addListing(new ListingBuilder().withName("Different Listing")
                .withAddress("Different Address").build());
        assertFalse(listings.equals(differentListings));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(listings.hashCode(), listings.hashCode());

        Listings sameListings = new Listings();
        sameListings.addListing(sampleListing);
        listings.addListing(sampleListing);
        assertEquals(listings.hashCode(), sameListings.hashCode());

        Listings differentListings = new Listings();
        differentListings.addListing(new ListingBuilder().withName("Different Listing").build());
        assertFalse(listings.hashCode() == differentListings.hashCode());
    }

    @Test
    public void replaceBuyer_existingBuyer_replacesWithNewBuyer() {
        Listing listing = new Listing(TypicalListings.PASIR_RIS);  // Assume PASIR_RIS has DANIEL and GEORGE as buyers
        Person buyerToRemove = TypicalPersons.DANIEL;
        Person buyerToAdd = TypicalPersons.HOON;

        // Verify initial state
        assertTrue(listing.getBuyers().contains(buyerToRemove), "The listing should contain DANIEL as a buyer initially.");

        // Replace the buyer
        listing.replaceBuyer(buyerToRemove, buyerToAdd);

        // Assert that the old buyer is removed and the new buyer is added
        assertFalse(listing.getBuyers().contains(buyerToRemove), "The listing should no longer contain DANIEL.");
        assertTrue(listing.getBuyers().contains(buyerToAdd), "The listing should now contain HOON as a buyer.");
    }

    @Test
    public void replaceBuyer_nonExistingBuyer_noChange() {
        Listing listing = new Listing(TypicalListings.PASIR_RIS);
        Person buyerToRemove = TypicalPersons.AMY;  // Assume AMY is not in the buyer list
        Person buyerToAdd = TypicalPersons.HOON;

        assertFalse(listing.getBuyers().contains(buyerToRemove), "The listing should not contain AMY initially.");

        listing.replaceBuyer(buyerToRemove, buyerToAdd);

        assertFalse(listing.getBuyers().contains(buyerToRemove), "The listing should still not contain AMY.");
        assertFalse(listing.getBuyers().contains(buyerToAdd), "The listing should not contain HOON since AMY was not in the list.");
    }

    @Test
    public void modifyListingWithSeller_newSeller_returnsModifiedListing() {
        Listing originalListing = new Listing(TypicalListings.PASIR_RIS);
        Person newSeller = TypicalPersons.BOB;

        Listing modifiedListing = originalListing.modifyListingWithSeller(newSeller);

        assertEquals(newSeller, modifiedListing.getSeller(), "The modified listing should have BOB as the seller.");

        assertNotEquals(newSeller, originalListing.getSeller(), "The original listing should still have ALICE as the seller.");
        assertEquals(originalListing.getBuyers(), modifiedListing.getBuyers(), "The buyers list should be unchanged.");
        assertEquals(originalListing.getAddress(), modifiedListing.getAddress(), "The address should be unchanged.");
        assertEquals(originalListing.getName(), modifiedListing.getName(), "The name should be unchanged.");
    }


}

