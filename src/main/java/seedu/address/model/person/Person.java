package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    //  private final Email email;

    // Data fields
    //  private final Address address;
    //  private final Remark remark;
    private final Property property;
    private final Appointment appointment;
    //  private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Appointment appointment, Property property) {
        requireAllNonNull(name, phone, appointment, property);
        this.name = name;
        this.phone = phone;
        //  this.email = email;
        //  this.address = address;
        //  this.remark = remark;
        this.appointment = appointment;
        this.property = property;
        //  this.tags.addAll(tags);
    }

    /**
     * Constructs a {@code Person} object with the specified name.
     * Initializes the phone number as {@code null}, sets the property to a default empty value,
     * and creates an appointment with default values for the date, start time, and end time.
     *
     * @param name The {@code Name} of the person. Must not be {@code null}.
     */
    public Person(Name name) {
        this.name = name;
        this.phone = null;
        this.property = new Property("");
        this.appointment = new Appointment(new Date(""), new From(""), new To(""));
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    //  public Email getEmail() {
    //      return email;
    //  }
    //  public Address getAddress() {
    //      return address;
    //  }
    //  public Remark getRemark() {
    //      return remark;
    //  }

    public Property getProperty() {
        return property;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    //  public Set<Tag> getTags() {
    //      return Collections.unmodifiableSet(tags);
    //  }

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
                && property.equals(otherPerson.property);
                //  && email.equals(otherPerson.email)
                //  && address.equals(otherPerson.address)
                //  && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, appointment, property);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                //  .add("email", email)
                //  .add("address", address)
                //  .add("remark", remark)
                //  .add("tags", tags)
                .add("appointment", appointment)
                .add("property", property)
                .toString();
    }
}
