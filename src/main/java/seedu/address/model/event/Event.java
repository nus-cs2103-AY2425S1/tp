package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;





/**
 * Represents an Event in the address book.
 */
public class Event {
    public static final String MESSAGE_CONSTRAINTS = "Event name should not be blank";
    private final String name;
    private final Set<Person> attendees;
    private final Set<Person> vendors;
    private final Set<Person> sponsors;
    private final Set<Person> volunteers;

    /**
     * Constructs a {@code Event}.
     * Each set of attendees, vendors, sponsors, and volunteers will be initialized to an empty set.
     *
     * @param name A valid event name.
     */
    public Event(String name) {
        this.name = name;
        this.attendees = new HashSet<>();
        this.vendors = new HashSet<>();
        this.sponsors = new HashSet<>();
        this.volunteers = new HashSet<>();
    }

    /**
     * Constructs a {@code Event}.
     * Each set of attendees, vendors, sponsors, and volunteers will be initialized to an empty set if null.
     *
     * @param name A valid event name.
     * @param attendees A set of attendees.
     * @param vendors A set of vendors.
     * @param sponsors A set of sponsors.
     * @param volunteers A set of volunteers.
     */
    public Event(String name, Set<Person> attendees, Set<Person> vendors, Set<Person> sponsors,
                 Set<Person> volunteers) {
        requireNonNull(name);
        this.name = name;
        this.attendees = attendees != null ? attendees : new HashSet<>();
        this.vendors = vendors != null ? vendors : new HashSet<>();
        this.sponsors = sponsors != null ? sponsors : new HashSet<>();
        this.volunteers = volunteers != null ? volunteers : new HashSet<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Returns an immutable Person set with attendee roles, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getAttendees() {
        return new HashSet<>(attendees);
    }


    /**
     * Returns an immutable Person set with Vendor Roles, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getVendors() {
        return new HashSet<>(vendors);
    }



    /**
     * Returns an immutable Person set with Sponsor Roles, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getSponsors() {
        return new HashSet<>(sponsors);
    }



    /**
     * Returns an immutable Person set with Volunteer Roles, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getVolunteers() {
        return new HashSet<>(volunteers);
    }



    /**
     * Adds a person to the event with the specified role.
     * @param person Person to be added.
     * @param role Role of the person.
     * @throws IllegalValueException If the person does not have the specified role or role is invalid.
     */
    public void addPerson(Person person, String role) throws IllegalValueException {
        try {
            Role personRole = RoleHandler.getRole(role);
            if (!person.hasRole(personRole)) {
                throw new IllegalValueException("Person does not have the role " + role);
            }
            Set<Person> roleSet;
            switch (personRole.getRoleName()) {
            case "attendee":
                roleSet = attendees;
                break;
            case "vendor":
                roleSet = vendors;
                break;
            case "sponsor":
                roleSet = sponsors;
                break;
            case "volunteer":
                roleSet = volunteers;
                break;
            default:
                throw new InvalidRoleException();
            }
            if (roleSet.contains(person)) {
                throw new IllegalValueException("Person already has role in event: " + this.name);
            }
            roleSet.add(person);
        } catch (InvalidRoleException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

    /**
     * Returns true if a given string is a valid event.
     */
    public static boolean isValidEvent(String event) {
        return !event.isEmpty();
    }

    /**
     * Removes a person from the event with the specified role.
     * @param person Person to be removed.
     * @param role Role of the person.
     * @throws IllegalArgumentException If the person does not have the specified role.
     */
    public void removePerson(Person person, String role) {
        Set<Person> roleSet;

        switch (role) {
        case "attendee":
            roleSet = attendees;
            break;
        case "vendor":
            roleSet = vendors;
            break;
        case "sponsor":
            roleSet = sponsors;
            break;
        case "volunteer":
            roleSet = volunteers;
            break;
        default:
            throw new IllegalArgumentException("Role not found");
        }

        if (!roleSet.contains(person)) {
            throw new IllegalArgumentException("Person does not have role in event: " + this.name);
        }

        roleSet.remove(person);

    }
    @Override
    public boolean equals(Object other) { // equality of 2 events defined only by the event name
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && name.equals(((Event) other).name));
    }
}
