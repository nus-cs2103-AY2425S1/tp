package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String[] DEFAULT_TAGS = {"Admin"};
    public static final String DEFAULT_NICKNAME = "";

    private Name name;
    private Telegram phone;
    private Email email;
    private Address address;
    private Set<Role> roles;
    private Nickname nickname;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Telegram(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        roles = new HashSet<>(Arrays.stream(DEFAULT_TAGS).map(Role::new).toList());
        nickname = new Nickname(DEFAULT_NICKNAME);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getTelegram();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        roles = new HashSet<>(personToCopy.getTags());
        nickname = personToCopy.getNickname();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.roles = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String phone) {
        this.phone = new Telegram(phone);
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
     * Sets the {@code Nickname} of the {@code Person} that we are building.
     */
    public PersonBuilder withNickname(String nickname) {
        this.nickname = new Nickname(nickname);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, roles, nickname);
    }

}
