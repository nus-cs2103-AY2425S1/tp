package seedu.eventtory.testutil;

import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.vendor.Vendor;

/**
 * A utility class to help with building EventTory objects.
 * Example usage: <br>
 *     {@code EventTory et = new EventToryBuilder().withVendor("John", "Doe").build();}
 */
public class EventToryBuilder {

    private EventTory eventTory;

    public EventToryBuilder() {
        eventTory = new EventTory();
    }

    public EventToryBuilder(EventTory eventTory) {
        this.eventTory = eventTory;
    }

    /**
     * Adds a new {@code Vendor} to the {@code EventTory} that we are building.
     */
    public EventToryBuilder withVendor(Vendor vendor) {
        eventTory.addVendor(vendor);
        return this;
    }

    public EventTory build() {
        return eventTory;
    }
}
