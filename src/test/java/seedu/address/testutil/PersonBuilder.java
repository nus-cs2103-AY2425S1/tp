package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
    public static final String DEFAULT_NOTES = "";
    public static final String DEFAULT_INCOME = "low";
    public static final String DEFAULT_AGE = "21";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Notes notes;
    private Set<Tag> tags;
    private Income income;
    private Age age;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        notes = Notes.createEmpty();
        tags = new HashSet<>();
        income = new Income(DEFAULT_INCOME);
        age = new Age(DEFAULT_AGE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        notes = personToCopy.getNotes();
        tags = new HashSet<>(personToCopy.getTags());
        income = personToCopy.getIncome();
        age = personToCopy.getAge();
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
     * Sets an empty {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyAddress() {
        this.address = Address.createEmpty();
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Person} that we are building.
     */
    public PersonBuilder withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Sets an empty {@code Notes} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyNotes() {
        this.notes = Notes.createEmpty();
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
     * Sets an empty {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyEmail() {
        this.email = Email.createEmpty();
        return this;
    }

    /**
     * Sets the {@code Income} of the {@code Person} that we are building.
     */
    public PersonBuilder withIncome(String income) {
        this.income = new Income(income);
        return this;
    }

    /**
     * Sets an empty {@code Income} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyIncome() {
        this.income = Income.createEmpty();
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets an empty {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyAge() {
        this.age = Age.createEmpty();
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, notes, tags, income, age);
    }

}
