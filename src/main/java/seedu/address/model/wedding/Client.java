package seedu.address.model.wedding;

import seedu.address.model.person.Person;

public class Client {
    private Person person;

    public Client(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    @Override
    public String toString() {
        return person.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return this.person.equals(otherClient.person);
    }

    @Override
    public int hashCode() {
        return this.person.hashCode();
    }
}
