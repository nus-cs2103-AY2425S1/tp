package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;

public class Delivery {
    private final Address address;

    private final Cost cost;

    private final Date date;

    private final Eta eta;

    private final Time time;

    public Delivery(Address address, Cost cost, Date date, Eta eta, Time time) {
        requireAllNonNull(address, cost, date, eta, time);
        this.address = address;
        this.cost = cost;
        this.date = date;
        this.eta = eta;
        this.time = time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .add("time", time)
                .add("eta", eta)
                .add("address", address)
                .add("cost", cost)
                .toString();
    }

}
