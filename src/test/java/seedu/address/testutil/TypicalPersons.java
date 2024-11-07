package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * A utility class encapsulating typical {@code AddressBook} objects containing {@code Student} and
 * {@Code Parent} objects to be used in tests. Includes parent-child links
 */
public class TypicalPersons {

    // Students
    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withLessonTime("tue:12:00")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Elle Meyer")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withLessonTime("sun:13:00")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Fiona Kunz")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withLessonTime("mon:12:00")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("George Best")
            .build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withLessonTime("wed:14:00")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("George Best")
            .build();

    // Parents
    public static final Parent ELLE = new ParentBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withChildren("Alice Pauline")
            .build();
    public static final Parent FIONA = new ParentBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withChildren("Benson Meier")
            .build();
    public static final Parent GEORGE = new ParentBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withChildren("Carl Kurz", "Daniel Meier")
            .build();

    // Names for the Persons above
    public static final Name ALICE_NAME = new Name("Alice Pauline");
    public static final Name BENSON_NAME = new Name("Benson Meier");
    public static final Name CARL_NAME = new Name("Carl Kurz");
    public static final Name DANIEL_NAME = new Name("Daniel Meier");
    public static final Name ELLE_NAME = new Name("Elle Meyer");
    public static final Name FIONA_NAME = new Name("Fiona Kunz");
    public static final Name GEORGE_NAME = new Name("George Best");

    private TypicalPersons() {

    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        // Alice, Benson, Carl and Daniel are Students; Elle, Fiona and George are Parents
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
