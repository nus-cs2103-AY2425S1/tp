package seedu.address.testutil;

import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;

/**
 * A utility class to help with building Owner objects.
 */
public class OwnerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_IC_NUMBER = "S1234567D";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private IdentificationCardNumber icNumber;

    /**
     * Creates a {@code OwnerBuilder} with the default details.
     */
    public OwnerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        icNumber = new IdentificationCardNumber(DEFAULT_IC_NUMBER);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public OwnerBuilder(Owner personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        icNumber = personToCopy.getIdentificationNumber();
    }

    /**
     * Sets the {@code Name} of the {@code Owner} that we are building.
     */
    public OwnerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Owner} that we are building.
     */
    public OwnerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Owner} that we are building.
     */
    public OwnerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Owner} that we are building.
     */
    public OwnerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code IdentificationCardNumber} of the {@code Owner} that we are building.
     */
    public OwnerBuilder withIcNumber(String icNumber) {
        this.icNumber = new IdentificationCardNumber(icNumber);
        return this;
    }

    public Owner build() {
        return new Owner(icNumber, name, phone, email, address);
    }

}
