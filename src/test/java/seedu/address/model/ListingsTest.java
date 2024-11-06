package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.listing.Listing;
import seedu.address.testutil.ListingBuilder;

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
}

