package seedu.address.testutil;

import seedu.address.model.Prudy;
import seedu.address.model.client.Client;

/**
 * A utility class to help with building Prudy objects.
 * Example usage: <br>
 *     {@code Prudy prudy = new PrudyBuilder().withClient("John", "Doe").build();}
 */
public class PrudyBuilder {

    private Prudy prudy;

    public PrudyBuilder() {
        prudy = new Prudy();
    }

    public PrudyBuilder(Prudy prudy) {
        this.prudy = prudy;
    }

    /**
     * Adds a new {@code Client} to the {@code Prudy} that we are building.
     */
    public PrudyBuilder withClient(Client client) {
        prudy.addClient(client);
        return this;
    }

    public Prudy build() {
        return prudy;
    }
}
