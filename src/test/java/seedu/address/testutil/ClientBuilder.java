package seedu.address.testutil;

import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;

/**
 * A utility class to help with building Client objects.
 */

public class ClientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private NameWithoutNumber name;
    private Phone phone;
    private Email email;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = new NameWithoutNumber(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Initializes the ClientBuilder with the data of {@code ClientToCopy}.
     */
    public ClientBuilder(Client personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new seedu.address.model.client.NameWithoutNumber(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new seedu.address.model.client.Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new seedu.address.model.client.Email(email);
        return this;
    }

    public Buyer buildBuyer() {
        return new Buyer(name, phone, email);
    }

    public Seller buildSeller() {
        return new Seller(name, phone, email);
    }
}
