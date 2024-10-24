package seedu.address.testutil;

import seedu.address.model.Listings;
import seedu.address.model.listing.Listing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalListings {
    public static final Listing PASIR_RIS = new ListingBuilder().withName("Pasir Ris Condo")
            .withAddress("543 Pasir Ris Street 11").withArea(75)
            .withPrice("700000", new BigDecimal("700000"))
            .withSeller(TypicalPersons.ALICE)
            .withBuyers(TypicalPersons.DANIEL, TypicalPersons.GEORGE)
            .build();
    public static final Listing TAMPINES = new ListingBuilder().withName("Tampines HDB")
            .withAddress("456 Tampines Street 21").withArea(90)
            .withPrice("600000", new BigDecimal("600000"))
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.DANIEL, TypicalPersons.ELLE)
            .build();
    public static final Listing KENT_RIDGE = new ListingBuilder().withName("Kent Ridge Condo")
            .withAddress("543 Pasir Ris Street 11").withArea(90)
            .withPrice("400000", new BigDecimal("400000"))
            .withSeller(TypicalPersons.ALICE)
            .withBuyers(TypicalPersons.ELLE)
            .build();
    public static final Listing BUONA_VISTA = new ListingBuilder().withName("Buona Vista Residences")
            .withAddress("543 Pasir Ris Street 11").withArea(90)
            .withPrice("400000", new BigDecimal("400000"))
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.GEORGE, TypicalPersons.FIONA)
            .build();
    public static final Listing SENGKANG = new ListingBuilder().withName("Sengkang HDB")
            .withAddress("543 Pasir Ris Street 11").withArea(90)
            .withPrice("400000", new BigDecimal("400000"))
            .withSeller(TypicalPersons.BENSON)
            .withBuyers(TypicalPersons.FIONA)
            .build();
    public static final Listing PUNGGOL = new ListingBuilder().withName("Punggol Condo")
            .withAddress("543 Pasir Ris Street 11").withArea(90)
            .withPrice("400000", new BigDecimal("400000"))
            .withSeller(TypicalPersons.CARL)
            .withBuyers(TypicalPersons.FIONA, TypicalPersons.ELLE)
            .build();
    public static final Listing SENTOSA = new ListingBuilder().withName("Sentosa Cove")
            .withAddress("543 Pasir Ris Street 11").withArea(90)
            .withPrice("400000", new BigDecimal("400000"))
            .withSeller(TypicalPersons.CARL)
            .withBuyers(TypicalPersons.GEORGE, TypicalPersons.DANIEL)
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
