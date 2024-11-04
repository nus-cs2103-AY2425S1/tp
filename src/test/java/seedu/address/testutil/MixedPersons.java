package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 * Contains 3 blacklisted persons and 4 whitelisted persons.
 */
public class MixedPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends")
            .withDeadline("15-10-2024").withClientStatus("blacklisted")
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withTags("owesMoney", "friends")
            .withDeadline("20-10-2024").withClientStatus("old")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withDeadline("10-11-2024").withClientStatus("unresponsive")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withDeadline("20-11-2024").withClientStatus("blacklisted")
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withDeadline("09-09-2024").withClientStatus("blacklisted")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withDeadline("08-08-2024").withClientStatus("active")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withDeadline("07-07-2024").withClientStatus("potential")
            .build();

    private MixedPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the mixed persons.
     */
    public static AddressBook getMixedAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getMixedPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getMixedPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
