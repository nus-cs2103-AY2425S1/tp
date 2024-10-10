package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRESIDENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withStudentStatus("undergraduate 1").withEmail("alice@example.com")
            .withTelegram("94351253")
            .withTags("Admin").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withStudentStatus("undergraduate 2")
            .withEmail("johnd@example.com").withTelegram("98765432")
            .withTags("President", "Marketing").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withTelegram("95352563")
            .withEmail("heinz@example.com")
            .withStudentStatus("undergraduate 3")
            .withTags("External Relations")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withTelegram("87652533")
            .withEmail("cornelia@example.com")
            .withStudentStatus("undergraduate 4")
            .withTags("Events (external)")
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withTelegram("9482224")
            .withEmail("werner@example.com")
            .withStudentStatus("undergraduate 5")
            .withTags("Events (internal)")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withTelegram("9482427")
            .withEmail("lydia@example.com")
            .withStudentStatus("masters")
            .withTags("Events (external)")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withTelegram("9482442")
            .withEmail("anna@example.com")
            .withStudentStatus("phd")
            .withTags("Vice President")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withTelegram("8482424")
            .withEmail("stefan@example.com").withStudentStatus("undergraduate 6").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withTelegram("8482131")
            .withEmail("hans@example.com").withStudentStatus("undergraduate 1").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withTelegram(VALID_TELEGRAM_AMY)
            .withEmail(VALID_EMAIL_AMY).withStudentStatus(VALID_STUDENT_STATUS_AMY).withTags(VALID_TAG_ADMIN).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withTelegram(VALID_TELEGRAM_BOB)
            .withEmail(VALID_EMAIL_BOB).withStudentStatus(VALID_STUDENT_STATUS_BOB).withTags(VALID_TAG_PRESIDENT, VALID_TAG_ADMIN)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
