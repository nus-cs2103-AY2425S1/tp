package seedu.address.testutil;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.LastPaidDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicFilePath;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY = "11 09 2001";
    public static final Boolean DEFAULT_HASPAID = false;
    public static final String DEFAULT_LASTPAIDDATE = "01 01 0000";
    public static final String DEFAULT_FREQUENCY = "0";
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Birthday birthday;
    private Set<Tag> tags;
    private Boolean hasPaid;
    private LastPaidDate lastPaidDate;
    private Frequency frequency;
    private ProfilePicFilePath profilePicFilePath;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        tags = new HashSet<>();
        hasPaid = DEFAULT_HASPAID;
        lastPaidDate = new LastPaidDate(DEFAULT_LASTPAIDDATE);
        frequency = new Frequency(DEFAULT_FREQUENCY);
        profilePicFilePath = ProfilePicFilePath.getDefaultProfilePic();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        birthday = personToCopy.getBirthday();
        tags = new HashSet<>(personToCopy.getTags());
        hasPaid = personToCopy.getHasPaid();
        lastPaidDate = personToCopy.getLastPaidDate();
        frequency = personToCopy.getFrequency();
        profilePicFilePath = personToCopy.getProfilePicFilePath();
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
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
        return this;
    }

    /**
     * Sets the {@code hasPaid} of the {@code Person} that we are building.
     */
    public PersonBuilder withHasPaid(Boolean hasPaid) {
        this.hasPaid = hasPaid;
        return this;
    }

    /**
     * Sets the {@code LastPaidDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withLastPaidDate(String lastPaidDate) {
        this.lastPaidDate = new LastPaidDate(lastPaidDate);
        return this;
    }

    /**
     * Sets the {@code Frequency} of the {@code Person} that we are building.
     */
    public PersonBuilder withFrequency(String frequency) {
        this.frequency = new Frequency(frequency);
        return this;
    }

    /**
     * Sets the {@code ProfilePicFilePath} of the {@code Person} that we are building.
     */
    public PersonBuilder withFrequency(Path path) {
        this.profilePicFilePath = new ProfilePicFilePath(path);
        return this;
    }

    /**
     * Builds the person object.
     */
    public Person build() {
        return new Person(name, phone, email, address, birthday, tags, hasPaid,
                lastPaidDate, frequency, profilePicFilePath);
    }

}
