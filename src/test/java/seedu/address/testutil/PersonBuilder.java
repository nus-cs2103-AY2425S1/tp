package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.role.Role;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TELEGRAM_USERNAME = "amybee";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private TelegramUsername telegramUsername;

    private Set<Role> roles;


    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        telegramUsername = new TelegramUsername(DEFAULT_TELEGRAM_USERNAME);

        roles = new HashSet<>();

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        telegramUsername = personToCopy.getTelegramUsername();
        roles = new HashSet<>(personToCopy.getRoles());

    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withRoles(String ... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }


    /**
     * Sets the {@code TelegramUsername} of the {@code Person} being built.
     *
     * @param telegramUsername The Telegram username to be assigned to the {@code Person}.
     * @return The current {@code PersonBuilder} instance with the updated Telegram username.
     */
    public PersonBuilder withTelegramUsername(String telegramUsername) {
        this.telegramUsername = new TelegramUsername(telegramUsername);
        return this;
    }

    /**
     * Builds a {@code Person} object with the given parameters.
     *
     * @return The {@code Person} object with the given parameters.
     */
    public Person build() {
        return new Person(name, phone, email, address, telegramUsername, roles);

    }

}
