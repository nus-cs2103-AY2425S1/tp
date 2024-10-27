package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    private Optional<Role> role;
    private Wedding ownWedding;
    private Set<Wedding> weddingJobs = new HashSet<>();


    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        role = Optional.of(new Role(DEFAULT_ROLE));
        ownWedding = null;
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
        ownWedding = personToCopy.getOwnWedding();
        weddingJobs.addAll(personToCopy.getWeddingJobs());
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
        if (role.isEmpty()) {
            this.role = Optional.empty();
        } else {
            this.role = Optional.of(new Role(role));
        }
        return this;
    }

    /**
     * Sets the {@code Wedding} of the {@code Person} that we are building.
     *
     * @param ownWedding The wedding to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withOwnWedding(Wedding ownWedding) {
        this.ownWedding = ownWedding;
        return this;
    }

    /**
     * Adds the {@code weddingJob} to the {@code Person} that we are building.
     *
     * @param weddingJob The {@code Wedding} to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder addWeddingJob(Wedding weddingJob) {
        this.weddingJobs.add(weddingJob);
        return this;
    }

    /**
     * Sets the {@code weddingJobs} of the {@code Person} that we are building.
     *
     * @param weddingJobs The {@code Set<Wedding>} to set.
     * @return The updated {@code PersonBuilder} instance.
     */
    public PersonBuilder withWeddingJobs(Set<Wedding> weddingJobs) {
        this.weddingJobs.addAll(weddingJobs);
        return this;
    }

    /**
     * Builds and returns the {@code Person} with the specified attributes.
     *
     * @return The built {@code Person}.
     */
    public Person build() {
        Person person = new Person(name, phone, email, address, role, ownWedding);
        person.setWeddingJobs(weddingJobs);
        return person;
    }
}
