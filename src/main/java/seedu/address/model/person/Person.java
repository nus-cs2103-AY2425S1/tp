package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
    private final RegisterNumber registerNumber;
    private final Sex sex;
    private final StudentClass studentClass;

    // Data fields
    private final Address address;
    private final EcName ecName;
    private final EcNumber ecNumber;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, RegisterNumber registerNumber, Sex sex,
                  StudentClass studentClass, EcName ecName, EcNumber ecNumber, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, registerNumber, sex, studentClass, ecName, ecNumber, tags);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registerNumber = registerNumber;
        this.sex = sex;
        this.studentClass = studentClass;
        this.ecName = ecName;
        this.ecNumber = ecNumber;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns representation of Name in the GUI
     */
    public String getDisplayedName() {
        return String.format(Name.MESSAGE_GUI, name);
    }

    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns representation of Phone in the GUI
     */
    public String getDisplayedPhone() {
        return String.format(Phone.MESSAGE_GUI, phone);
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns representation of Email in the GUI
     */
    public String getDisplayedEmail() {
        return String.format(Email.MESSAGE_GUI, email);
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns representation of Address in the GUI
     */
    public String getDisplayedAddress() {
        return String.format(Address.MESSAGE_GUI, address);
    }

    public RegisterNumber getRegisterNumber() {
        return registerNumber;
    }

    /**
     * Returns representation of RegisterNumber in the GUI
     */
    public String getDisplayedRegisterNumber() {
        return String.format(RegisterNumber.MESSAGE_GUI, registerNumber);
    }

    public Sex getSex() {
        return sex;
    }

    /**
     * Returns representation of Sex in the GUI
     */
    public String getDisplayedSex() {
        return String.format(Sex.MESSAGE_GUI, sex);
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    /**
     * Returns representation of StudentClass in the GUI
     */
    public String getDisplayedStudentClass() {
        return String.format(StudentClass.MESSAGE_GUI, studentClass);
    }

    public EcName getEcName() {
        return ecName;
    }

    /**
     * Returns representation of EcName in the GUI
     */
    public String getDisplayedEcName() {
        return String.format(EcName.MESSAGE_GUI, ecName);
    }

    public EcNumber getEcNumber() {
        return ecNumber;
    }

    /**
     * Returns representation of EcNumber in the GUI
     */
    public String getDisplayedEcNumber() {
        return String.format(EcNumber.MESSAGE_GUI, ecNumber);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && registerNumber.equals(otherPerson.registerNumber)
                && sex.equals(otherPerson.sex)
                && studentClass.equals(otherPerson.studentClass)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, registerNumber, sex, studentClass, ecNumber, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("register number", registerNumber)
                .add("sex", sex)
                .add("class", studentClass)
                .add("emergency contact name", ecName)
                .add("emergency contact number", ecNumber)
                .add("tags", tags)
                .toString();
    }

}
