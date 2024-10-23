package seedu.address.model.concert;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.NamedObject;

/**
 * Represents a Concert in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Concert implements NamedObject {
    // Identity fields
    private final Name name;
    private final Address address;
    private final ConcertDate concertDate;

    /**
     * Every field must be present and not null.
     */
    public Concert(Name name, Address address, ConcertDate concertDate) {
        requireAllNonNull(name, address, concertDate);
        this.name = name;
        this.address = address;
        this.concertDate = concertDate;
    }

    @Override
    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public ConcertDate getDate() {
        return concertDate;
    }

    /**
     * Returns the result of the equals method, is here to be consistent with the person class.
     */
    public boolean isSameConcert(Concert otherConcert) {
        return equals(otherConcert);
    }
    /**
     * Returns true if both concerts have the same name, address and concertDate.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Concert)) {
            return false;
        }

        Concert otherConcert = (Concert) other;
        return name.equals(otherConcert.name) && address.equals(
                otherConcert.address) && concertDate.equals(otherConcert.concertDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, concertDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).add("address", address).add("date",
                concertDate).toString();
    }
}
