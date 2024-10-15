package seedu.address.testutil;

import seedu.address.model.CampusConnect;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building CampusConnect objects.
 * Example usage: <br>
 *     {@code CampusConnect ab = new CampusConnectBuilder().withPerson("John", "Doe").build();}
 */
public class CampusConnectBuilder {

    private CampusConnect campusConnect;

    public CampusConnectBuilder() {
        campusConnect = new CampusConnect();
    }

    public CampusConnectBuilder(CampusConnect campusConnect) {
        this.campusConnect = campusConnect;
    }

    /**
     * Adds a new {@code Person} to the {@code CampusConnect} that we are building.
     */
    public CampusConnectBuilder withPerson(Person person) {
        campusConnect.addPerson(person);
        return this;
    }

    public CampusConnect build() {
        return campusConnect;
    }
}
