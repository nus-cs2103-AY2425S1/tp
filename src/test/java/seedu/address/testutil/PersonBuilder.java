package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class to help with building {@code Person} objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ROLE = "Client"; // Default role example

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Role role; // Single role representing role
    private Wedding wedding;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        role = new Role(DEFAULT_ROLE); // Default role
        wedding = null; // Default wedding is null
    }

    /**
     * Initializes the {@code PersonBuilder} with the data of {@code personToCopy}.
     *
     * @param personToCopy The person to copy.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        role = personToCopy.getRole();
        wedding = personToCopy.getOwnWedding();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     *
     * @param name The name to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     *
     * @param address The address to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     *
     * @param phone The phone number to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     *
     * @param email The email to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Role} (as a {@code Tag}) of the {@code Person} that we are building.
     *
     * @param role The role to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Wedding} of the {@code Person} that we are building.
     *
     * @param wedding The wedding to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withWedding(Wedding wedding) {
        this.wedding = wedding;
        return this;
    }

    /**
     * Builds and returns the {@code Person} with the specified attributes.
     *
     * @return The built {@code Person}.
     */
    public Person build() {
        return new Person(name, phone, email, address, role, wedding);
    }
}
