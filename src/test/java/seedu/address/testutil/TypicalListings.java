package seedu.address.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Listings;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Region;

/**
 * A utility class containing a list of {@code Listing} objects to be used in tests.
 */
public class TypicalListings {
    public static final Listing PASIR_RIS = new ListingBuilder().withName("Pasir Ris Condo")
            .withAddress("543 Pasir Ris Street 11").withArea(75)
            .withPrice("700000", new BigDecimal("700000")).withRegion(Region.EAST)
            .withSeller(TypicalPersons.ALICE)
            .withBuyers(TypicalPersons.DANIEL, TypicalPersons.GEORGE)
            .build();
    public static final Listing TAMPINES = new ListingBuilder().withName("Tampines HDB")
            .withAddress("456 Tampines Street 21").withArea(90)
            .withPrice("600000", new BigDecimal("600000")).withRegion(Region.EAST)
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.DANIEL, TypicalPersons.ELLE)
            .build();
    public static final Listing KENT_RIDGE = new ListingBuilder().withName("Kent Ridge Condo")
            .withAddress("543 Kent Ridge Avenue 12").withArea(90)
            .withPrice("400000", new BigDecimal("400000")).withRegion(Region.WEST)
            .withSeller(TypicalPersons.ALICE)
            .withBuyers(TypicalPersons.ELLE)
            .build();
    public static final Listing BUONA_VISTA = new ListingBuilder().withName("Buona Vista Residences")
            .withAddress("246 Buona Vista Dr 2").withArea(90)
            .withPrice("400000", new BigDecimal("400000")).withRegion(Region.WEST)
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.GEORGE, TypicalPersons.FIONA)
            .build();
    public static final Listing SENGKANG = new ListingBuilder().withName("Sengkang HDB")
            .withAddress("516 Sengkang Street 21").withArea(90)
            .withPrice("400000", new BigDecimal("400000")).withRegion(Region.NORTHEAST)
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.FIONA)
            .build();
    public static final Listing PUNGGOL = new ListingBuilder().withName("Punggol Condo")
            .withAddress("223 Punggol Dr 4").withArea(90)
            .withPrice("400000", new BigDecimal("400000")).withRegion(Region.NORTHEAST)
            .withSeller(TypicalPersons.CARL)
            .withBuyers(TypicalPersons.FIONA, TypicalPersons.ELLE)
            .build();
    public static final Listing SENTOSA = new ListingBuilder().withName("Sentosa Cove")
            .withAddress("46 Sentosa Cove").withArea(90)
            .withPrice("400000", new BigDecimal("400000")).withRegion(Region.SOUTH)
            .withSeller(TypicalPersons.CARL)
            .withBuyers(TypicalPersons.GEORGE, TypicalPersons.DANIEL)
            .build();

    public static final Listing SIMEI = new ListingBuilder().withName("Simei HDB")
            .withAddress("453 Simei Avenue 12").withArea(50)
            .withPrice("120000", new BigDecimal("120000")).withRegion(Region.EAST)
            .withSeller(TypicalPersons.ALICE)
            .withBuyers(TypicalPersons.FIONA, TypicalPersons.GEORGE)
            .build();

    public static final Listing HOUGANG = new ListingBuilder().withName("Hougang Condo")
            .withAddress("453 Hougang Street 12").withArea(50)
            .withPrice("120000", new BigDecimal("120000")).withRegion(Region.NORTHEAST)
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.GEORGE)
            .build();

    private TypicalListings() {}

    public static Listings getTypicalListings() {
        Listings listings = new Listings();
        for (Listing l : getTypicalPropertyListing()) {
            listings.addListing(l);
        }
        return listings;
    }

    public static List<Listing> getTypicalPropertyListing() {
        return new ArrayList<>(Arrays.asList(PASIR_RIS, TAMPINES, KENT_RIDGE,
                BUONA_VISTA, SENGKANG, PUNGGOL, SENTOSA));
    }

}
