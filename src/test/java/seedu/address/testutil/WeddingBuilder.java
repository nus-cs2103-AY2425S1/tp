package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;



/**
 * A utility class to help with building Wedding objects.
 */
public class WeddingBuilder {

    public static final String DEFAULT_WEDDING_NAME = "Amy's Wedding";
    // default address is blank string as address is optional
    public static final String DEFAULT_ADDRESS = "";
    // default date is blank string as date is optional
    public static final String DEFAULT_DATE = "";

    private WeddingName weddingName;
    private int peopleCount;
    private Person partner1;
    private Person partner2;
    private ArrayList<Person> guestList;
    private Address address;
    private String date;

    /**
     * Creates a {@code WeddingBuilder} with the default details.
     */
    public WeddingBuilder() {
        weddingName = new WeddingName(DEFAULT_WEDDING_NAME);
        address = new Address(DEFAULT_ADDRESS);
        date = DEFAULT_DATE;
    }

    /**
     * Initializes the WeddingBuilder with the data of {@code weddingToCopy}.
     */
    public WeddingBuilder(Wedding weddingToCopy) {
        weddingName = weddingToCopy.getWeddingName();
        partner1 = weddingToCopy.getPartner1();
        partner2 = weddingToCopy.getPartner2();
        address = weddingToCopy.getAddress();
        date = weddingToCopy.getDate();
    }

    /**
     * Sets the {@code WeddingName} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withName(String name) {
        this.weddingName = new WeddingName(name);
        return this;
    }

    /**
     * Sets the {@code partner1} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withPartner1(Person partner1) {
        this.partner1 = partner1;
        return this;
    }

    /**
     * Sets the {@code partner2} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withPartner2(Person partner2) {
        this.partner2 = partner2;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    public Wedding build() {
        return new Wedding(weddingName, peopleCount, partner1, partner2, guestList, address, date);
    }

}
