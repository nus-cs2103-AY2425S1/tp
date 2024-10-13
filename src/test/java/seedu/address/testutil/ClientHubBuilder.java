package seedu.address.testutil;

import seedu.address.model.ClientHub;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building ClientHub objects.
 * Example usage: <br>
 *     {@code clientHub ch = new ClientHubBuilder().withPerson("John", "Doe").build();}
 */
public class ClientHubBuilder {

    private ClientHub clientHub;

    public ClientHubBuilder() {
        clientHub = new ClientHub();
    }

    public ClientHubBuilder(ClientHub clientHub) {
        this.clientHub = clientHub;
    }

    /**
     * Adds a new {@code Person} to the {@code ClientHub} that we are building.
     */
    public ClientHubBuilder withPerson(Person person) {
        clientHub.addPerson(person);
        return this;
    }

    public ClientHub build() {
        return clientHub;
    }
}
