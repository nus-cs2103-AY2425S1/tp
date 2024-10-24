package seedu.address.testutil;

import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListingBuilder {

    public static final String DEFAULT_NAME = "Geylang HDB";
    public static final String DEFAULT_ADDRESS = "123 Geylang Lor 21";
    public static final int DEFAULT_AREA = 90;
    public static final String DEFAULT_FORMATTED_PRICE = "300000";
    public static final BigDecimal DEFAULT_RAW_PRICE = new BigDecimal(DEFAULT_FORMATTED_PRICE);
    public static final Region DEFAULT_REGION = Region.CENTRAL;
    public static final Person DEFAULT_SELLER = TypicalPersons.ALICE;

    private Name name;
    private Address address;
    private Area area;
    private Region region;
    private Price price;
    private Person seller;
    private Set<Person> buyers;

    public ListingBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        area = new Area(DEFAULT_AREA);
        price = new Price(DEFAULT_FORMATTED_PRICE, DEFAULT_RAW_PRICE);
        region = DEFAULT_REGION;
        seller = DEFAULT_SELLER;
        buyers = new HashSet<>();
    }

    /**
     * Initializes the ListingBuilder with the data of {@code listingToCopy}.
     */
    public ListingBuilder(Listing listingToCopy) {
        name = listingToCopy.getName();
        address = listingToCopy.getAddress();
        area = listingToCopy.getArea();
        price = listingToCopy.getPrice();
        region = listingToCopy.getRegion();
        seller = listingToCopy.getSeller();
        buyers = new HashSet<>(listingToCopy.getBuyers());
    }

    public ListingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public ListingBuilder withName(Name name) {
        this.name = name;
        return this;
    }

    public ListingBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public ListingBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public ListingBuilder withArea(int area) {
        this.area = new Area(area);
        return this;
    }

    public ListingBuilder withArea(Area area) {
        this.area = area;
        return this;
    }

    public ListingBuilder withPrice(String formattedValue, BigDecimal rawValue) {
        this.price = new Price(formattedValue, rawValue);
        return this;
    }

    public ListingBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    public ListingBuilder withRegion(Region region) {
        this.region = region;
        return this;
    }

    public ListingBuilder withSeller(Person seller) {
        this.seller = seller;
        return this;
    }

    public ListingBuilder withBuyers(Person ... buyers) {
        this.buyers = new HashSet<Person>(List.of(SampleDataUtil.getSamplePersons()));
        return this;
    }

    public Listing build() {
        return new Listing(name, address, price, area, region, seller, buyers);
    }
}
