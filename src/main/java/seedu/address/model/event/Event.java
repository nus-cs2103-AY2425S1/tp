package seedu.address.model.event;

import seedu.address.model.person.Person;

public class Event {
    private Name name;
    private Time time;
    private Venue venue;
    private Person person;

    public Event(Name name, Time time, Venue venue, Person person) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.person = person;
    }

    public Name getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public Venue getVenue() {
        return venue;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return name + " " + time + " " + venue + " " + person;
    }
}
