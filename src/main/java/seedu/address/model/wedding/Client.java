package seedu.address.model.wedding;

import seedu.address.model.person.Person;

/**
 * Represents a Client in a Wedding.
 * Guarantees: Field values are validated and immutable.
 */
public class Client {
    private final Person person;

    public Client(Person person) {
        this.person = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getRole(), null);
    }

    public Person getPerson() {
        return this.person;
    }

    @Override
    public String toString() {
        return person.toString();
    }

    public void setOwnWedding(Wedding wedding) {
        this.person.setOwnWedding(wedding);
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
