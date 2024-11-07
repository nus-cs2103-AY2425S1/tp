package seedu.eventfulnus.model.event;

import static seedu.eventfulnus.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javafx.util.Pair;
import seedu.eventfulnus.commons.util.ToStringBuilder;
import seedu.eventfulnus.logic.commands.EditEventCommand;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;
import seedu.eventfulnus.model.person.role.athlete.SportString;

/**
 * Represents an {@code Event} in the {@link AddressBook} of the {@link ModelManager}.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements Comparable<Event> {
    // Class fields
    public static final DateTimeFormatter DATE_TIME_PARSE_FORMATTER = DateTimeFormatter.ofPattern("yyyy MM dd HHmm");
    public static final DateTimeFormatter DATE_TIME_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String MESSAGE_CONSTRAINTS = "DateTime should be entered in the format of yyyy MM dd HHmm";

    // Identity fields
    private final EventName name;
    private final Sport sport;
    private final Pair<Faculty, Faculty> teams;
    private final Venue venue;
    private final LocalDateTime dateTime;

    // Data fields
    private final Set<Person> participants = new HashSet<>();

    /**
     * Constructs an {@code Event}. Every field must be present and not null.
     */
    public Event(Sport sport, Pair<Faculty, Faculty> teams, Venue venue,
                 LocalDateTime dateTime, Set<Person> participants) {
        requireAllNonNull(sport, teams.getKey(), teams.getValue(), venue, dateTime, participants);
        this.sport = sport;
        this.teams = teams;
        this.venue = venue;
        this.dateTime = dateTime;
        this.participants.addAll(participants);
        this.name = new EventName(SportString.getSportString(sport) + " "
                                  + teams.getKey().toString() + "vs" + teams.getValue().toString());
    }

    /**
     * Returns the {@link EventName} of the {@code Event}.
     */
    public EventName getName() {
        return name;
    }

    /**
     * Returns the {@link Sport} of the {@code Event}.
     */
    public Sport getSport() {
        return sport;
    }

    /**
     * Returns the {@code teams} involved in the {@code Event},
     * in the form of a {@link Pair} of {@link Faculty}s.
     */
    public Pair<Faculty, Faculty> getTeams() {
        return teams;
    }

    public Faculty getFirstTeam() {
        return teams.getKey();
    }

    public Faculty getSecondTeam() {
        return teams.getValue();
    }

    /**
     * Returns the {@link Venue} of the {@code Event}.
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * Returns the {@link LocalDateTime} of the {@code Event}.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the {@link LocalDateTime} of the {@code Event} in the format of yyyy MM dd HHmm.
     */
    public String getDateTimeParseString() {
        return dateTime.format(DATE_TIME_PARSE_FORMATTER);
    }

    /**
     * Returns the {@link LocalDateTime} of the {@code Event} in the format of yyyy-MM-dd HH:mm.
     */
    public String getDateTimeDisplayString() {
        return dateTime.format(DATE_TIME_DISPLAY_FORMATTER);
    }

    /**
     * Returns an immutable {@link Set} of {@link Person}s representing the
     * {@code Event}'s {@code participants}, which throws
     * {@link UnsupportedOperationException} if modification is attempted.
     */
    public Set<Person> getParticipants() {
        return Collections.unmodifiableSet(participants);
    }

    /**
     * Adds the changed {@link Person} from a {@link EditEventCommand}
     * to the {@code Event}'s {@code participants}.
     */
    public void updateParticipant(Person oldParticipant, Person editedParticipant) {
        if (participants.contains(oldParticipant)) { // should always be true, as contains is checked before.
            participants.remove(oldParticipant);
            participants.add(editedParticipant);
        }
    }

    /**
     * Removes the {@link Person} from the {@code Event}'s {@code participants}.
     */
    public void removeParticipant(Person person) {
        participants.remove(person);
    }

    /**
     * Returns true if both {@code Event}s have the same name and date time.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getDateTime().equals(getDateTime());
    }

    /**
     * Returns true if both {@code Event}s have the same {@link EventName},
     * {@link Sport}, {@code teams}, and {@link Venue}, and {@link LocalDateTime}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event otherEvent)) {
            return false;
        }

        return sport.equals(otherEvent.sport)
                && teams.equals(otherEvent.teams) && venue.equals(otherEvent.venue)
                && dateTime.equals(otherEvent.dateTime);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sport", sport)
                .add("teams", teams)
                .add("venue", venue)
                .add("dateTime", dateTime.format(DATE_TIME_DISPLAY_FORMATTER))
                .add("participants", participants)
                .toString();
    }

    @Override
    public int compareTo(Event otherEvent) {
        return this.dateTime.compareTo(otherEvent.dateTime);
    }
}
