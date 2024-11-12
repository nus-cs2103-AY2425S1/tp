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

    private final Listings LISTINGS = new Listings();
    private final Listing SAMPLE_LISTING = new ListingBuilder().withName("Sample Listing").build();

    @Test
    public void equals() {
        assertTrue(LISTINGS.equals(LISTINGS));

        assertFalse(LISTINGS.equals(null));

        assertFalse(LISTINGS.equals("string"));

        Listings sameListings = new Listings();
        sameListings.addListing(SAMPLE_LISTING);
        LISTINGS.addListing(SAMPLE_LISTING);
        assertTrue(LISTINGS.equals(sameListings));

        Listings differentListings = new Listings();
        differentListings.addListing(new ListingBuilder().withName("Different Listing")
                .withAddress("Different Address").build());
        assertFalse(LISTINGS.equals(differentListings));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(LISTINGS.hashCode(), LISTINGS.hashCode());

        Listings sameListings = new Listings();
        sameListings.addListing(SAMPLE_LISTING);
        LISTINGS.addListing(SAMPLE_LISTING);
        assertEquals(LISTINGS.hashCode(), sameListings.hashCode());

        Listings differentListings = new Listings();
        differentListings.addListing(new ListingBuilder().withName("Different Listing").build());
        assertFalse(LISTINGS.hashCode() == differentListings.hashCode());
    }

    @Test
    public void replaceBuyer_existingBuyer_replacesWithNewBuyer() {
        Listing listing = new Listing(TypicalListings.PASIR_RIS);
        Person buyerToRemove = TypicalPersons.DANIEL;
        Person buyerToAdd = TypicalPersons.HOON;

        assertTrue(listing.getBuyers().contains(buyerToRemove),
                "The listing should contain DANIEL as a buyer initially.");

        listing.replaceBuyer(buyerToRemove, buyerToAdd);

        assertFalse(listing.getBuyers().contains(buyerToRemove),
                "The listing should no longer contain DANIEL.");
        assertTrue(listing.getBuyers().contains(buyerToAdd),
                "The listing should now contain HOON as a buyer.");
    }

    @Test
    public void replaceBuyer_nonExistingBuyer_noChange() {
        Listing listing = new Listing(TypicalListings.PASIR_RIS);
        Person buyerToRemove = TypicalPersons.AMY;
        Person buyerToAdd = TypicalPersons.HOON;

        assertFalse(listing.getBuyers().contains(buyerToRemove),
                "The listing should not contain AMY initially.");

        listing.replaceBuyer(buyerToRemove, buyerToAdd);

        assertFalse(listing.getBuyers().contains(buyerToRemove),
                "The listing should still not contain AMY.");
        assertFalse(listing.getBuyers().contains(buyerToAdd),
                "The listing should not contain HOON since AMY was not in the list.");
    }

    @Test
    public void modifyListingWithSeller_newSeller_returnsModifiedListing() {
        Listing originalListing = new Listing(TypicalListings.PASIR_RIS);
        Person newSeller = TypicalPersons.BOB;

        Listing modifiedListing = originalListing.modifyListingWithSeller(newSeller);

        assertEquals(newSeller, modifiedListing.getSeller(), "The modified listing should have BOB as the seller.");

        assertNotEquals(newSeller, originalListing.getSeller(),
                "The original listing should still have ALICE as the seller.");
        assertEquals(originalListing.getBuyers(), modifiedListing.getBuyers(), "The buyers list should be unchanged.");
        assertEquals(originalListing.getAddress(), modifiedListing.getAddress(), "The address should be unchanged.");
        assertEquals(originalListing.getName(), modifiedListing.getName(), "The name should be unchanged.");
    }


}

