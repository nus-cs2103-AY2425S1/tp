package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.role.Attendee;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Vendor;
import seedu.address.model.role.Volunteer;
import seedu.address.model.role.exceptions.InvalidRoleException;
import seedu.address.ui.Observer;


/**
 * Represents an Event in the address book.
 */
public class Event {
    public static final String MESSAGE_CONSTRAINTS = "Event name should not exceed 60 characters and "
            + "should not be blank.";
    public static final String VALIDATION_REGEX = ".{1,60}";
    private final String name;
    private final Set<Person> attendees;
    private final Set<Person> vendors;
    private final Set<Person> sponsors;
    private final Set<Person> volunteers;
    private Observer observer;

    /**
     * Constructs a {@code Event}.
     * Each set of attendees, vendors, sponsors, and volunteers will be initialized to an empty set.
     *
     * @param name A valid event name.
     */
    public Event(String name) {
        requireNonNull(name);
        checkArgument(isValidEvent(name), MESSAGE_CONSTRAINTS);
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
        requireNonNull(name);
        checkArgument(isValidEvent(name), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.attendees = attendees != null ? attendees : new HashSet<>();
        this.vendors = vendors != null ? vendors : new HashSet<>();
        this.sponsors = sponsors != null ? sponsors : new HashSet<>();
        this.volunteers = volunteers != null ? volunteers : new HashSet<>();
    }

    /**
     * Constructs a {@code Event} with the same attributes as the given {@code Event}.
     *
     * @param event {@code Event} that we want to copy.
     */
    public Event(Event event) {
        String toCopyName = event.getName();
        requireNonNull(toCopyName);
        checkArgument(isValidEvent(toCopyName), MESSAGE_CONSTRAINTS);
        this.name = toCopyName;
        this.attendees = event.getAttendees();
        this.vendors = event.getVendors();
        this.sponsors = event.getSponsors();
        this.volunteers = event.getVolunteers();
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
     * Returns true if the person is in the event.
     * @param p Person to check.
     * @return True if the person is in the event.
     */
    public boolean hasPerson(Person p) {
        return attendees.contains(p) || vendors.contains(p) || sponsors.contains(p) || volunteers.contains(p);
    }

    /**
     * Returns the role of a person in the event, if not present, return null
     * @param p Person to check.
     * @return Role of the person in the event.
     */
    public String getRole(Person p) {
        if (attendees.contains(p)) {
            return "attendee";
        } else if (vendors.contains(p)) {
            return "vendor";
        } else if (sponsors.contains(p)) {
            return "sponsor";
        } else if (volunteers.contains(p)) {
            return "volunteer";
        } else {
            return null;
        }
    }

    /**
     * Returns all persons in the event.
     * @return Set of all persons in the event.
     */
    public Set<Person> getAllPersons() {
        Set<Person> allPersons = new HashSet<>();
        allPersons.addAll(attendees);
        allPersons.addAll(vendors);
        allPersons.addAll(sponsors);
        allPersons.addAll(volunteers);
        return allPersons;
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
                throw new IllegalValueException("One or more person(s) to be added does not have the " + role
                        + " role that you are adding him or her to. For example, to add a person as an attendee of "
                        + "Event X, make sure he or she has the attendee role.");
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
                throw new IllegalValueException("Contact " + person.getName() + " already has role in event: "
                        + this.name);
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
        return !event.isEmpty() && event.matches(VALIDATION_REGEX);
    }

    /**
     * Removes a person from the event with the specified role that is known.
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

    /**
     * Removes person from all roles if they are present in that role
     * @param personToRemove Person that will be removed
     */
    public void removePerson(Person personToRemove) {
        requireNonNull(personToRemove);
        // check each set and see it contains the person to remove
        if (attendees.contains(personToRemove)) {
            attendees.remove(personToRemove);
        }

        if (vendors.contains(personToRemove)) {
            vendors.remove(personToRemove);
        }

        if (sponsors.contains(personToRemove)) {
            sponsors.remove(personToRemove);
        }

        if (volunteers.contains(personToRemove)) {
            volunteers.remove(personToRemove);
        }
    }

    /**
     * Edits person in all roles if they are present in that role
     * @param personToEdit Person that will be removed
     * @param editedPerson Person that will replace personToEdit
     */
    public void editPerson(Person personToEdit, Person editedPerson) {
        requireAllNonNull(personToEdit, editedPerson);

        removeContactFromEventRoleOnEdit(personToEdit, editedPerson);
        // check each set and see it contains the person to edit
        editContactAcrossAllRoles(personToEdit, editedPerson);
    }

    private void editContactAcrossAllRoles(Person personToEdit, Person editedPerson) {
        if (attendees.contains(personToEdit)) {
            attendees.remove(personToEdit);
            attendees.add(editedPerson);
        }

        if (vendors.contains(personToEdit)) {
            vendors.remove(personToEdit);
            vendors.add(editedPerson);
        }

        if (sponsors.contains(personToEdit)) {
            sponsors.remove(personToEdit);
            sponsors.add(editedPerson);
        }

        if (volunteers.contains(personToEdit)) {
            volunteers.remove(personToEdit);
            volunteers.add(editedPerson);
        }
    }

    private void removeContactFromEventRoleOnEdit(Person personToEdit, Person editedPerson) {
        if (!editedPerson.hasRole(new Attendee())) {
            attendees.remove(personToEdit);
        }
        if (!editedPerson.hasRole(new Volunteer())) {
            volunteers.remove(personToEdit);
        }
        if (!editedPerson.hasRole(new Vendor())) {
            vendors.remove(personToEdit);
        }
        if (!editedPerson.hasRole(new Sponsor())) {
            sponsors.remove(personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) { // equality of 2 events defined only by the event name
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && name.equals(((Event) other).name));
    }

    /**
     * Checks if a person is in this event.
     *
     * @param person Person to check.
     * @return {@code True} if person in event, else {@code false}
     */
    public boolean isPersonInEvent(Person person) {
        return attendees.contains(person) || volunteers.contains(person)
                || sponsors.contains(person) || vendors.contains(person);
    }

    /**
     * Registers an observer to this object.
     * <p>
     * Sets the specified {@code observer} as the observer of this object. The observer will be
     * notified of changes by calling its {@code update()} method when {@code updateUi()} is invoked.
     * Only one observer can be registered at a time; any previously registered observer will be replaced.
     * </p>
     *
     * @param observer The observer to register, which will be notified of updates.
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Notifies the registered observer of a UI update.
     * <p>
     * If an observer is registered, this method calls the observer's {@code update()} method to
     * reflect any necessary changes in the user interface. If no observer is registered, this method does nothing.
     * </p>
     */
    public void updateUi() {
        if (this.observer != null) {
            this.observer.update();
        }
    }

    /**
     * Returns the total number of distinct contacts involved in the event.
     * <p>
     * This method calculates the total number of unique {@code Person} objects across
     * attendees, volunteers, vendors, and sponsors by combining them into a set to eliminate duplicates.
     * </p>
     *
     * @return The count of distinct persons participating in the event.
     */
    public int getTotalNumberOfDistinctContacts() {
        HashSet<Person> persons = new HashSet<>();
        persons.addAll(attendees);
        persons.addAll(volunteers);
        persons.addAll(vendors);
        persons.addAll(sponsors);
        return persons.size();
    }

    /**
     * Retrieves the currently registered observer.
     * <p>
     * This method returns the observer that has been added to this object via the
     * {@code addObserver(Observer observer)} method. If no observer has been registered,
     * this method will return {@code null}.
     * </p>
     *
     * @return The currently registered {@code Observer}, or {@code null} if no observer is set.
     */
    public Observer getObserver() {
        return this.observer;
    }

    /**
     * Clears all contact lists, including attendees, volunteers, vendors, and sponsors.
     */
    public void clearAllContacts() {
        this.attendees.clear();
        this.volunteers.clear();
        this.vendors.clear();
        this.sponsors.clear();
    }

    /**
     * Determines the roles a given person has in this event.
     * Checks if the specified person is contained within the attendees, volunteers,
     * vendors, and sponsors sets, and adds the corresponding role to a HashSet.
     *
     * @param person the person whose roles in the event are being determined
     * @return a HashSet of {@code Role} objects that the specified person holds in this event
     *         (e.g., Attendee, Volunteer, Vendor, Sponsor)
     */
    public HashSet<Role> makeRoles(Person person) {
        HashSet<Role> roles = new HashSet<>();
        if (this.attendees.contains(person)) {
            roles.add(new Attendee());
        }
        if (this.volunteers.contains(person)) {
            roles.add(new Volunteer());
        }
        if (this.vendors.contains(person)) {
            roles.add(new Vendor());
        }
        if (this.sponsors.contains(person)) {
            roles.add(new Sponsor());
        }
        return roles;
    }
}
