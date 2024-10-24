package seedu.address.testutil;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Listing objects.
 */
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

    /**
     * Constructs a {@code ListingBuilder} with the default details.
     */
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

    /**
     * Sets the {@code Name} of the {@code Listing} that we are building.
     *
     * @param name The name to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Listing} that we are building.
     *
     * @param name The {@code Name} object to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withName(Name name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Listing} that we are building.
     *
     * @param address The address to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Listing} that we are building.
     *
     * @param address The {@code Address} object to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the {@code Area} of the {@code Listing} that we are building.
     *
     * @param area The area to set (in square meters).
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withArea(int area) {
        this.area = new Area(area);
        return this;
    }

    /**
     * Sets the {@code Area} of the {@code Listing} that we are building.
     *
     * @param area The {@code Area} object to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withArea(Area area) {
        this.area = area;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Listing} that we are building.
     *
     * @param formattedValue The formatted price string.
     * @param rawValue The raw price in {@code BigDecimal}.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withPrice(String formattedValue, BigDecimal rawValue) {
        this.price = new Price(formattedValue, rawValue);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Listing} that we are building.
     *
     * @param price The {@code Price} object to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    /**
     * Sets the {@code Region} of the {@code Listing} that we are building.
     *
     * @param region The region to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withRegion(Region region) {
        this.region = region;
        return this;
    }

    /**
     * Sets the {@code Seller} of the {@code Listing} that we are building.
     *
     * @param seller The seller to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withSeller(Person seller) {
        this.seller = seller;
        return this;
    }

    /**
     * Sets the {@code Buyers} of the {@code Listing} that we are building.
     *
     * @param buyers The buyers to set.
     * @return The current {@code ListingBuilder} object.
     */
    public ListingBuilder withBuyers(Person ... buyers) {
        this.buyers = new HashSet<Person>(List.of(SampleDataUtil.getSamplePersons()));
        return this;
    }

    /**
     * Builds and returns the {@code Listing} with the current attributes.
     *
     * @return The constructed {@code Listing}.
     */
    public Listing build() {
        return new Listing(name, address, price, area, region, seller, buyers);
    }
}
