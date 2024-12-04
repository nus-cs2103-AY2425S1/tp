package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

class SampleDataUtilTest {

    @Test
    void getSamplePersons_correctSampleData() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Check if the number of sample persons is correct
        assertEquals(6, samplePersons.length);

        // Verify the details of the first sample person (Alex Yeoh)
        Person alexYeoh = samplePersons[0];
        assertEquals("Alex Yeoh", alexYeoh.getName().fullName);
        assertEquals("87438807", alexYeoh.getPhone().value);
        assertEquals("alexyeoh@example.com", alexYeoh.getEmail().value);
        assertTrue(alexYeoh.getTags().contains(new Tag("friends")));

        // Verify the details of the last sample person (Roy Balakrishnan)
        Person royBalakrishnan = samplePersons[5];
        assertEquals("Roy Balakrishnan", royBalakrishnan.getName().fullName);
        assertEquals("92624417", royBalakrishnan.getPhone().value);
        assertEquals("royb@example.com", royBalakrishnan.getEmail().value);
        assertTrue(royBalakrishnan.getTags().contains(new Tag("colleagues")));
    }

    @Test
    void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Check if the AddressBook contains all the sample persons
        for (Person samplePerson : samplePersons) {
            assertTrue(sampleAddressBook.getPersonList().contains(samplePerson));
        }

        // Check if the number of persons in the AddressBook is correct
        assertEquals(6, sampleAddressBook.getPersonList().size());
    }

    @Test
    void getTagSet_createsCorrectTagSet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "family");

        // Check if the Tag set contains the correct tags
        assertTrue(tagSet.contains(new Tag("friends")));
        assertTrue(tagSet.contains(new Tag("family")));

        // Check the size of the Tag set
        assertEquals(2, tagSet.size());
    }
    @Test
    void sampleListings_correctSampleData() {
        Listing[] sampleListings = SampleDataUtil.sampleListings();

        // Check if the number of sample listings is correct
        assertEquals(1, sampleListings.length);

        // Verify the details of the sample listing
        Listing listing = sampleListings[0];
        assertEquals("RC4", listing.getName().fullName);
        assertEquals("134 Clementi Ave", listing.getAddress().value);
        assertEquals("200000", listing.getPrice().getFormattedValue());
        assertEquals("100", listing.getArea().getArea());
        assertEquals(Region.WEST, listing.getRegion());

        Person seller = listing.getSeller();
        assertEquals("Bernice Yu", seller.getName().fullName);
        assertEquals("99272758", seller.getPhone().value);
        assertEquals("berniceyu@example.com", seller.getEmail().value);
        assertTrue(seller.getTags().contains(new Tag("friends")));
        assertTrue(seller.getTags().contains(new Tag("colleagues")));

        Set<Person> buyers = listing.getBuyers();
        assertTrue(buyers.isEmpty());
    }

    @Test
    void getSampleListings_containsCorrectSampleListings() {
        ReadOnlyListings sampleListings = SampleDataUtil.getSampleListings();
        Listing[] sampleListingsArray = SampleDataUtil.sampleListings();

        // Check if the Listings contains all the sample listings
        for (Listing sampleListing : sampleListingsArray) {
            assertTrue(sampleListings.getListingList().contains(sampleListing));
        }

        // Check if the number of listings in the Listings is correct
        assertEquals(1, sampleListings.getListingList().size());
    }

}
