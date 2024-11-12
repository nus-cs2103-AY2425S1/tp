package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 * Contains only blacklisted persons.
 */
public class BlacklistedPersons {
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends")
            .withDeadline("15-10-2024").withClientStatus("blacklisted")
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withTags("owesMoney", "friends")
            .withDeadline("20-10-2024").withClientStatus("blacklisted")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withDeadline("10-11-2024").withClientStatus("blacklisted")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withDeadline("20-11-2024").withClientStatus("blacklisted")
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withDeadline("09-09-2024").withClientStatus("blacklisted")
            .build();

    private BlacklistedPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getBlacklistedAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getBlacklistedPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getBlacklistedPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
    }
}
