package seedu.address.testutil;

import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertDate;

/**
 * A utility class to help with building Concert objects.
 */
public class ConcertBuilder {

    public static final String DEFAULT_NAME = "Concert c1";
    public static final String DEFAULT_ADDRESS = "1 Stadium Dr, Singapore 397629";
    public static final String DEFAULT_CONCERT_DATE = "2024-10-10 1000";

    private Name name;
    private Address address;
    private ConcertDate concertDate;

    /**
     * Creates a {@code ConcertBuilder} with the default details.
     */
    public ConcertBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        concertDate = new ConcertDate(DEFAULT_CONCERT_DATE);
    }

    /**
     * Initializes the ConcertBuilder with the data of {@code concertToCopy}.
     */
    public ConcertBuilder(Concert concertToCopy) {
        name = concertToCopy.getName();
        address = concertToCopy.getAddress();
        concertDate = concertToCopy.getDate();
    }

    /**
     * Sets the {@code Name} of the {@code Concert} that we are building.
     */
    public ConcertBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Concert} that we are building.
     */
    public ConcertBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Concert} that we are building.
     */
    public ConcertBuilder withDate(String date) {
        this.concertDate = new ConcertDate(date);
        return this;
    }

    public Concert build() {
        return new Concert(name, address, concertDate);
    }

}

