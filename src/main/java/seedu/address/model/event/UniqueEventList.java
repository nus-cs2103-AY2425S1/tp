package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.Person;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * A event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * events uses Event#isSameEvent(Event) for equality to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueEventList. However, the removal of a event uses Event#equals(Object)
 * to ensure that the event with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Returns true if the list contains an event that clashes with the given argument.
     */
    public boolean containsOverlap(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlap);
    }

    /**
     * Returns true if the list contains an event that clashes with the given argument, excluding the given argument.
     */
    public boolean containsOverlap(Event toCheck, Event toIgnore) {
        requireAllNonNull(toCheck, toIgnore);
        return internalList.stream().anyMatch(e -> e.isOverlap(toCheck) && !e.isSameEvent(toIgnore));
    }

    /**
     * Adds an event to the list.
     * The event must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the list.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the list.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, editedEvent);
    }

    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Removes all events with the given person from the list.
     * The person must exist in the list.
     */
    public void clearEventsWithPerson(Person target) {
        requireNonNull(target);
        internalList.removeIf(e -> e.getCelebrity().equals(target));
    }

    /**
     * Removes the given person from contacts of all events.
     * The person must exist in the list.
     */
    public void clearPersonFromContacts(Person target) {
        requireNonNull(target);
        internalList.forEach(e -> removePersonFromContacts(e, target));
    }

    /**
     * Removes the given person from the contacts of the given event.
     * The person must exist in the list.
     */
    public void removePersonFromContacts(Event event, Person target) {
        requireAllNonNull(event, target);
        if (!event.getContacts().contains(target)) {
            return;
        }
        Set<Person> newContacts = new HashSet<>(event.getContacts());
        newContacts.remove(target);
        Event replacement = Event.createEvent(event.getName(), event.getTime(), event.getVenue().orElse(null),
                event.getCelebrity(), newContacts);
        setEvent(event, replacement);
    }

    /**
     * Replaces the given person from all events.
     */
    public void replacePersonInEvents(Person personToEdit, Person editedPerson) {
        requireAllNonNull(personToEdit, editedPerson);
        internalList.forEach(e -> replacePersonInEvent(e, personToEdit, editedPerson));
    }

    /**
     * Replaces the given person from the contacts of the given event.
     */
    public void replacePersonInEvent(Event event, Person personToEdit, Person editedPerson) {
        requireAllNonNull(event, personToEdit, editedPerson);
        if (event.getCelebrity().equals(personToEdit)) {
            Event replacement = Event.createEvent(event.getName(), event.getTime(), event.getVenue().orElse(null),
                    editedPerson, event.getContacts());
            setEvent(event, replacement);
        } else if (event.getContacts().contains(personToEdit)) {
            Set<Person> newContacts = new HashSet<>(event.getContacts());
            newContacts.remove(personToEdit);
            newContacts.add(editedPerson);
            Event replacement = Event.createEvent(event.getName(), event.getTime(), event.getVenue().orElse(null),
                    event.getCelebrity(), newContacts);
            setEvent(event, replacement);
        }
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
    }

    /**
     * Sorts the contents of this list by start time chronologically.
     */
    public void sortByStartTime() {
        internalList.sort(Comparator.comparing(e -> e.getStartTime()));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueEventList)) {
            return false;
        }

        UniqueEventList otherList = (UniqueEventList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code events} contains only unique persons.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}

