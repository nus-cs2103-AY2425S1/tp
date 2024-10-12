package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

public class Delivery {
    private final Person person;
    private final Address address;

    private final Cost cost;

    private final Date date;

    private final Time time;

    private final Eta eta;

    public Delivery(Person person, Address address, Cost cost, Date date, Time time, Eta eta) {
        requireAllNonNull(person, address, cost, date, time, eta);
        this.person = person;
        this.address = address;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.eta = eta;
    }

    public Delivery(Person person, Address address, Cost cost, Eta eta) {
        requireAllNonNull(person, address, cost, eta);
        this.person = person;
        this.address = address;
        this.cost = cost;
        this.date = new Date(LocalDate.now().toString());
        this.time = new Time(LocalTime.now().toString());
        this.eta = eta;
    }

    public Person getPerson() {
        return person;
    }

    public Address getAddress() {
        return address;
    }

    public Cost getCost() {
        return cost;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Eta getEta() {
        return eta;
    }

    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        return equals(otherDelivery);
    }

    public boolean forPerson(Person person) {
        return this.person.equals(person);
    }

    /**
     * Returns true if both deliveries have the same identity and data fields.
     * This defines a stronger notion of equality between two deliveries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Delivery)) {
            return false;
        }

        Delivery otherDelivery = (Delivery) other;
        return person.equals(otherDelivery.person)
                && address.equals(otherDelivery.address)
                && cost.equals(otherDelivery.cost)
                && date.equals(otherDelivery.date)
                && time.equals(otherDelivery.time)
                && eta.equals(otherDelivery.eta);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", person)
                .add("date", date)
                .add("time", time)
                .add("eta", eta)
                .add("address", address)
                .add("cost", cost)
                .toString();
    }

}
