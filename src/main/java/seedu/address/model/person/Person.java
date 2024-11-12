package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Birthday.CUSTOM_BIRTHDAY_FORMAT;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Remark remark;
    private final DateOfCreation dateOfCreation;
    private final History history;
    private final Birthday birthday;
    private final PropertyList propertyList;
    /**
     * array of preset tags, index 0: favourite == 1; archived == -1; default == 0;
     *                       index 1: buyer == 1; seller == 0;
     *                       index 2: business == 1; personal == 0;
     * */
    private final int[] flagWeights = new int[3]; // Initializes with {0, 0, 0}

    /**
     * Every field must be present and not null. Used for new person creation
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = Remark.EMPTY_REMARK;
        this.birthday = Birthday.EMPTY_BIRTHDAY;
        this.tags.addAll(tags);
        this.dateOfCreation = new DateOfCreation(LocalDate.now());
        this.history = new History(dateOfCreation.getDateOfCreation());
        this.propertyList = new PropertyList();
    }
    /**
     * Every field must be present and not null. with non-empty remark and non-empty birthday
     */
    public Person(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.birthday = Birthday.EMPTY_BIRTHDAY;
        this.tags.addAll(tags);
        this.dateOfCreation = new DateOfCreation(LocalDate.now());
        this.history = new History(dateOfCreation.getDateOfCreation());
        this.propertyList = new PropertyList();
    }

    /**
     * Every field must be present and not null. with non-empty remark and non-empty birthday
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Remark remark, Birthday birthday, Set<Tag> tags, DateOfCreation dateOfCreation, History history,
                  PropertyList propertyList) {
        requireAllNonNull(name, phone, email, address, tags, remark, dateOfCreation, history);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.birthday = birthday;
        this.dateOfCreation = dateOfCreation;
        this.history = history;
        this.tags.addAll(tags);
        this.propertyList = propertyList;
    }

    public Name getName() {
        return name;
    }

    public String getFullName() {
        return this.getName().getFullName();
    }

    public String getFullNameToLowerCase() {
        return this.getName().getFullName().toLowerCase();
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Remark getRemark() {
        return this.remark;
    }

    public DateOfCreation getDateOfCreation() {
        return this.dateOfCreation;
    }

    public History getHistory() {
        return this.history;
    }

    public Birthday getBirthday() {
        return birthday;
    }
    public PropertyList getPropertyList() {
        return propertyList;
    }
    public int getWeight(int... tagIndex) {
        int totalWeight = 0;
        for (int index : tagIndex) {
            totalWeight += flagWeights[index];
        }
        return totalWeight;
    }

    /**
     * Returns if the person's birthday is within a week from today.
     */
    public boolean isBirthdayWithinNextWeek() {
        return birthday.isBirthdayWithinNextWeek();
    }

    /**
     * Returns a string with the person's {@code name} and {@code birthday}.
     */
    public String formatBirthdayMessage() {
        return name + CUSTOM_BIRTHDAY_FORMAT + birthday.getDateOfUpcomingBirthday();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
        //TODO modify logic to check duplicates, distinguish persons by phone number.
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && remark.equals(otherPerson.remark)
                && dateOfCreation.equals(otherPerson.dateOfCreation)
                && birthday.equals(otherPerson.birthday)
                && propertyList.equals(otherPerson.propertyList);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("remark", remark)
                .add("birthday", birthday)
                .add("dateOfCreation", dateOfCreation)
                .add("history", history)
                .add("properties", propertyList)
                .toString();
    }
}
