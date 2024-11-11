package seedu.ddd.model.contact.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ddd.commons.util.CollectionUtil;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.exceptions.EventNotFoundException;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Contact implements Displayable {

    // Identity fields
    private final Id contactId;
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Set<Tag> tags;

    // References
    private final Set<Event> events;

    /**
     * Every field must be present and not null.
     */
    public Contact(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Id contactId) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, tags);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.tags = new HashSet<>();
        this.tags.addAll(tags);
        this.events = new HashSet<>();

        this.contactId = contactId;
    }

    public Name getName() {
        return name;
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
     * Returns an immutable {@code Tag} set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Id getId() {
        return contactId;
    }

    /**
     * Returns an immutable {@code EventId} set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Id> getEventIds() {
        return Collections.unmodifiableSet(events.stream().map(Event::getEventId).collect(Collectors.toSet()));
    }

    /**
    * Returns an immutable {@code Event} set, which throws {@code UnsupportedOperationException}
    * if modification is attempted.
    */
    public Set<Event> getEvents() {
        return Collections.unmodifiableSet(events);
    }

    /**
     * Adds an {@code Event} related to the {@code Contact}
     */
    public void addEvent(Event event) {
        events.add(event);
    };

    /**
     * Removes an {@code Event} related to the {@code Contact}
     */
    public void removeEvent(Event event) {
        if (!events.contains(event)) {
            throw new EventNotFoundException();
        }
        events.remove(event);
    }

    /**
     * Returns true if both persons have the same name or phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && (otherContact.getName().isSameName(name)
                || otherContact.getPhone().equals(phone));
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
        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return name.equals(otherContact.name)
                && phone.equals(otherContact.phone)
                && email.equals(otherContact.email)
                && address.equals(otherContact.address)
                && tags.equals(otherContact.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, contactId);
    }

}
