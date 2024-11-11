package seedu.eventtory.model;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eventtory.commons.util.ToStringBuilder;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.association.UniqueAssociationList;
import seedu.eventtory.model.commons.exceptions.AssociationDeleteException;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.event.UniqueEventList;
import seedu.eventtory.model.id.UniqueId;
import seedu.eventtory.model.vendor.UniqueVendorList;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Wraps all data at the EventTory level
 * Duplicates are not allowed (by .isSameVendor & .isSameEvent comparison)
 */
public class EventTory implements ReadOnlyEventTory {

    private final UniqueVendorList vendors;
    private final UniqueEventList events;
    private final UniqueAssociationList associations;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        vendors = new UniqueVendorList();
        associations = new UniqueAssociationList();
        events = new UniqueEventList();
    }

    public EventTory() {}

    /**
     * Creates an EventTory using the date in {@code toBeCopied}
     */
    public EventTory(ReadOnlyEventTory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the vendor list with {@code vendors}.
     * {@code vendors} must not contain duplicate vendors.
     */
    public void setVendors(List<Vendor> vendors) {
        this.vendors.setVendors(vendors);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Replaces the contents of the association list with {@code associations}.
     * {@code associations} must not contain duplicate associations.
     */
    public void setAssociations(List<Association> associations) {
        this.associations.setAssociations(associations);
    }

    /**
     * Resets the existing data of this {@code EventTory} with {@code newData}.
     */
    public void resetData(ReadOnlyEventTory newData) {
        requireNonNull(newData);

        setVendors(newData.getVendorList());
        setEvents(newData.getEventList());
        setAssociations(newData.getAssociationList());
    }

    //// vendor-level operations

    /**
     * Returns true if a vendor with the same identity as {@code vendor} exists in EventTory.
     */
    public boolean hasVendor(Vendor vendor) {
        requireNonNull(vendor);
        return vendors.contains(vendor);
    }

    /**
     * Adds a vendor to EventTory.
     * The vendor must not already exist in EventTory.
     */
    public void addVendor(Vendor p) {
        vendors.add(p);
    }

    /**
     * Replaces the given vendor {@code target} in the list with {@code editedVendor}.
     * {@code target} must exist in EventTory.
     * The vendor identity of {@code editedVendor} must not be the same as another existing vendor in EventTory.
     */
    public void setVendor(Vendor target, Vendor editedVendor) {
        requireNonNull(editedVendor);

        vendors.setVendor(target, editedVendor);
    }

    /**
     * Removes {@code key} from this {@code EventTory}.
     * {@code key} must exist in EventTory.
     * @throws AssociationDeleteException if the vendor is not deletable (e.g. it is associated with an event)
     */
    public void removeVendor(Vendor key) throws AssociationDeleteException {
        final boolean isVendorAssignedToAnyEvent = getAssociatedEvents(key).size() > 0;
        if (isVendorAssignedToAnyEvent) {
            throw new AssociationDeleteException();
        }
        vendors.remove(key);
    }

    //// assign operations

    /**
     * Returns true if the given {@code vendor} is already assigned to the given {@code event}.
     * {@code vendor} and {@code event} must exist in EventTory.
     */
    public boolean isVendorAssignedToEvent(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);

        // Get the UniqueIds for vendor and event
        UniqueId vendorId = vendor.getId();
        UniqueId eventId = event.getId();

        // Check for the association
        Association association = new Association(vendorId, eventId);
        return associations.contains(association);
    }

    /**
     * Assigns the given {@code vendor} in the list to {@code event}.
     * {@code vendor} and {@code event} must exist in EventTory.
     */
    public void assignVendorToEvent(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);

        UniqueId vendorId = vendor.getId();
        UniqueId eventId = event.getId();

        Association association = new Association(vendorId, eventId);
        associations.add(association);
    }

    /**
     * Unassigns the given {@code vendor} in the list from {@code event}.
     * {@code vendor} and {@code event} must exist in EventTory.
     */
    void unassignVendorFromEvent(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);

        UniqueId vendorId = vendor.getId();
        UniqueId eventId = event.getId();

        Association association = new Association(vendorId, eventId);
        associations.remove(association);
    }

    /**
     * Returns list of associated vendors to an event.
     */
    public ObservableList<Vendor> getAssociatedVendors(Event event) {
        requireNonNull(event);
        UniqueId eventId = event.getId();
        List<Vendor> vendorsList = associations.asUnmodifiableObservableList().stream()
                .filter(association -> association.getEventId().equals(eventId))
                .map(association -> vendors.getVendorById(association.getVendorId()))
                .filter(Objects::nonNull) // Ensure no null values are added
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(vendorsList);
    }

    /**
     * Returns list of associated events to a vendor.
     */
    public ObservableList<Event> getAssociatedEvents(Vendor vendor) {
        requireNonNull(vendor);
        UniqueId vendorId = vendor.getId();
        List<Event> eventsList = associations.asUnmodifiableObservableList().stream()
                .filter(association -> association.getVendorId().equals(vendorId))
                .map(association -> events.getEventById(association.getEventId()))
                .filter(Objects::nonNull) // Ensure no null values are added
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(eventsList);
    }

    //// event-level operations

    /**
     * Returns true if a event with the same identity as {@code event} exists in EventTory.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the EventTory.
     * The event must not already exist in EventTory.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in EventTory.
     * The event identity of {@code editedEvent} must not be the same as another existing event in EventTory.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code EventTory}.
     * {@code key} must exist in EventTory.
     * @throws AssociationDeleteException if the event is not deletable (e.g. it is associated with a vendor)
     */
    public void removeEvent(Event key) throws AssociationDeleteException {
        final boolean isEventAssignedToAnyVendor = getAssociatedVendors(key).size() > 0;
        if (isEventAssignedToAnyVendor) {
            throw new AssociationDeleteException();
        }
        events.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("vendors", vendors)
                .add("events", events)
                .add("associations", associations)
                .toString();
    }

    @Override
    public ObservableList<Vendor> getVendorList() {
        return vendors.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Association> getAssociationList() {
        return associations.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventTory)) {
            return false;
        }

        EventTory otherEventTory = (EventTory) other;
        return vendors.equals(otherEventTory.vendors) && events.equals(otherEventTory.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendors.hashCode(), events.hashCode());
    }
}
