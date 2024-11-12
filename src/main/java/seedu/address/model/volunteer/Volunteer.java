package seedu.address.model.volunteer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerNotAvailableOnAnyDayException;

/**
 * Represents a Volunteer in the system.
 */
public class Volunteer {

    private static int nextId = 0;

    // Identity fields
    private final int id;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final VolunteerDates availableDates;
    private final ObservableList<String> involvedIn;

    /**
     * Represents a Volunteer in the system.
     *
     * @param name The name of the volunteer.
     * @param phone The phone number of the volunteer.
     * @param email The email address of the volunteer.
     * @param availableDate The date the volunteer is available.
     * @param involvedIn The list of events the volunteer has.
     */
    public Volunteer(Name name, Phone phone, Email email, VolunteerDates availableDate, List<String> involvedIn) {
        requireAllNonNull(name, phone, email, availableDate);

        this.id = nextId++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.availableDates = availableDate;
        this.involvedIn = FXCollections.observableArrayList(involvedIn);
    }

    /**
     * Constructs an {@code Volunteer} without events.
     */
    public Volunteer(Name name, Phone phone, Email email, VolunteerDates availableDate) {
        this(name, phone, email, availableDate, FXCollections.observableArrayList());
    }

    public boolean isInvolvedInEvent(String eventName) {
        return this.involvedIn.contains(eventName);
    }

    public int getId() {
        return id;
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

    public VolunteerDates getAvailableDates() {
        return availableDates;
    }

    public ObservableList<String> getInvolvedIn() {
        return involvedIn;
    }

    /**
     * Returns true if both volunteers have the same name.
     * This defines a weaker notion of equality between two volunteers.
     *
     * @param otherVolunteer The other volunteer to compare with.
     * @return True if both volunteers have the same name.
     */
    public boolean isSameVolunteer(Volunteer otherVolunteer) {
        if (otherVolunteer == this) {
            return true;
        }
        return otherVolunteer != null
                && otherVolunteer.getName().isSameName(getName());
    }

    /**
     * Returns true if both volunteers have the same identity and data fields.
     * This defines a stronger notion of equality between two volunteers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Volunteer)) {
            return false;
        }

        Volunteer otherVolunteer = (Volunteer) other;
        return name.equals(otherVolunteer.name)
                && phone.equals(otherVolunteer.phone)
                && email.equals(otherVolunteer.email)
                && availableDates.equals(otherVolunteer.availableDates);
    }

    /**
     * Adds an event to the volunteer's list of events.
     *
     * @param newEvent The event to add.
     */
    public void addEvent(String newEvent) {
        involvedIn.add(newEvent);
    }

    /**
     * Removes an event from the volunteer's list of events.
     *
     * @param eventToRemove The event to remove.
     */
    public void removeEvent(String eventToRemove) {
        involvedIn.remove(eventToRemove);
    }

    /**
     * Adds a given string list of dates to the volunteer's available dates
     * @param dateList
     */
    public void addAvailableDates(String dateList) {
        String[] dates = dateList.split(",");
        this.availableDates.addStringOfDatesToAvailList(dates);
    }

    /**
     * Removes a given string list of dates from the volunteer's available dates
     *
     * @param dateList
     * @param participatingEvents
     */
    public void removeAvailableDates(String dateList, List<Event> participatingEvents) throws DateTimeParseException,
            VolunteerDeleteMissingDateException, VolunteerNotAvailableOnAnyDayException {
        String[] dates = dateList.split(",");
        this.availableDates.removeStringOfDatesFromAvailList(participatingEvents, dates);
    }

    /**
     * Returns the list of events the volunteer is involved in as an unmodifiable list.
     *
     * @return An unmodifiable ObservableList of events.
     */
    public ObservableList<String> getEvents() {
        return FXCollections.unmodifiableObservableList(involvedIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, availableDates);
    }

    @Override
    public String toString() {
        return "Volunteer{name=" + name
                + ", phone=" + phone
                + ", email=" + email
                + ", availableDate=" + availableDates + "}";
    }

    public boolean isFreeOn(LocalDate date) {
        return this.availableDates.hasAvailableDate(date);
    }
}
