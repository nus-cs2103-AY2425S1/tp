package seedu.address.model.wedding;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a Client in a Wedding.
 * Guarantees: Field values are validated and immutable.
 */
public class Client {

    public static final String MESSAGE_CONSTRAINTS =
            "Client can take any names or index in the address book, and it should not be blank.";
    public static final String INDEX_VALIDATION_REGEX = "^\\d+$\n";
    public static final String NAME_VALIDATION_REGEX = Name.VALIDATION_REGEX;
    private final Person person;

    /**
     * Constructs a {@code Client}.
     *
     * @param person A valid {@code Person} to be classified as the Client
     */
    public Client(Person person) {
        this.person = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getRole(), null);
    }

    public Person getPerson() {
        return this.person;
    }

    public static boolean isValidClientName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public static boolean isValidClientIndex(String test) {
        return test.matches(INDEX_VALIDATION_REGEX);
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
