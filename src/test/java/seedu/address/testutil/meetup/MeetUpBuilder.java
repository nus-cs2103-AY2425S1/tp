package seedu.address.testutil.meetup;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.meetup.AddedBuyer;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;
import seedu.address.model.util.SampleMeetUpDataUtil;

/**
 * A utility class to help with building MeetUp objects.
 */
public class MeetUpBuilder {

    public static final String DEFAULT_NAME = "Client Meeting";
    public static final String DEFAULT_INFO = "Get clients requirements";
    public static final String DEFAULT_FROM = "2024-09-01 12:00";
    public static final String DEFAULT_TO = "2024-09-01 14:00";
    public static final String DEFAULT_ADDED_BUYER = "David Li";

    private Name name;
    private Info info;
    private From from;
    private To to;
    private Set<AddedBuyer> addedBuyers;

    /**
     * Creates a {@code MeetUpBuilder} with the default details.
     */
    public MeetUpBuilder() {
        name = new Name(DEFAULT_NAME);
        info = new Info(DEFAULT_INFO);
        from = new From(DEFAULT_FROM);
        to = new To(DEFAULT_TO);
        addedBuyers = new HashSet<>();;
        addedBuyers.add(new AddedBuyer(DEFAULT_ADDED_BUYER));
    }

    /**
     * Initializes the MeetUpBuilder with the data of {@code meetUpToCopy}.
     */
    public MeetUpBuilder(MeetUp meetUpToCopy) {
        name = meetUpToCopy.getName();
        info = meetUpToCopy.getInfo();
        from = meetUpToCopy.getFrom();
        to = meetUpToCopy.getTo();
        addedBuyers = new HashSet<>(meetUpToCopy.getAddedBuyers());
    }

    /**
     * Sets the {@code Name} of the {@code MeetUp} that we are building.
     */
    public MeetUpBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }


    /**
     * Sets the {@code Info} of the {@code MeetUp} that we are building.
     */
    public MeetUpBuilder withInfo(String info) {
        this.info = new Info(info);
        return this;
    }

    /**
     * Sets the {@code From} of the {@code MeetUp} that we are building.
     */
    public MeetUpBuilder withFrom(String from) {
        this.from = new From(from);
        return this;
    }

    /**
     * Sets the {@code To} of the {@code MeetUp} that we are building.
     */
    public MeetUpBuilder withTo(String to) {
        this.to = new To(to);
        return this;
    }

    /**
     * Parses the {@code addedBuyers} into a {@code Set<AddedBuyer>}
     * and set it to the {@code MeetUp} that we are building.
     */
    public MeetUpBuilder withAddedBuyers(String ... addedBuyers) {
        this.addedBuyers = SampleMeetUpDataUtil.getAddedBuyerSet(addedBuyers);
        return this;
    }

    public MeetUp build() {
        return new MeetUp(name, info, from, to, addedBuyers);
    }

}
