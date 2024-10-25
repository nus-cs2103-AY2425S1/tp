package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DateOfCreation;
import seedu.address.model.person.Email;
import seedu.address.model.person.History;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PropertyList; // Import PropertyList
import seedu.address.model.person.Remark;
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
    public static final String DEFAULT_REMARK = "";
    public static final DateOfCreation DEFAULT_DATE_OF_CREATION = new DateOfCreation(LocalDate.now());
    public static final String DEFAULT_BIRTHDAY = "2001-01-01";
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private DateOfCreation dateOfCreation;
    private History history;
    private Birthday birthday;
    private Set<Tag> tags;
    private PropertyList propertyList; // New PropertyList attribute

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = Remark.EMPTY_REMARK;
        dateOfCreation = DEFAULT_DATE_OF_CREATION;
        history = new History(dateOfCreation.getDateOfCreation());
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        tags = new HashSet<>();
        propertyList = new PropertyList(); // Initialize PropertyList with default
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        dateOfCreation = personToCopy.getDateOfCreation();
        history = personToCopy.getHistory();
        birthday = personToCopy.getBirthday();
        tags = new HashSet<>(personToCopy.getTags());
        propertyList = personToCopy.getPropertyList(); // Copy PropertyList
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
    public PersonBuilder withTags(String... tags) {
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
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateOfCreation} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = new DateOfCreation(LocalDate.parse(dateOfCreation));
        this.history = new History(LocalDate.parse(dateOfCreation));
        return this;
    }

    /**
     * Adds an activity on a specific date to the {@code History} of the {@code Person}.
     */
    public PersonBuilder withHistory(LocalDate date, String activity) {
        if (this.history == null) {
            this.history = new History(dateOfCreation.getDateOfCreation());
        }
        this.history.addActivity(date, activity);
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
     * Sets the {@code PropertyList} of the {@code Person} that we are building.
     */
    public PersonBuilder withPropertyList(PropertyList propertyList) {
        this.propertyList = propertyList;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, remark, birthday, tags, dateOfCreation, history, propertyList);
    }
}
