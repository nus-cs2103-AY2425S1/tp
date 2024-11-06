package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURS_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOURS_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DEACON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Tutor ALICE = new TutorBuilder().withId(0).withName("Alice Pauline")
            .withAddress("jurong, 123456").withEmail("alice@example.com")
            .withPhone("94351253").withHours("1")
            .withSubjects("Math").build();
    public static final Tutor BENSON = new TutorBuilder().withId(1).withName("Benson Meier")
            .withAddress("Clementi, 345212")
            .withEmail("johnd@example.com").withPhone("98765432").withHours("2")
            .withSubjects("english").build();
    public static final Tutor CARL = new TutorBuilder().withId(2).withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street, 987543").withHours("3").build();
    public static final Tutee DANIEL = new TuteeBuilder().withId(3).withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street, 123456").withHours("4")
            .withSubjects("Math").build();
    public static final Tutee ELLE = new TuteeBuilder().withId(4).withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave, 878777").withHours("5")
            .withSubjects("English").build();
    public static final Tutee FIONA = new TuteeBuilder().withId(5).withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo, 123456").withHours("6").build();
    public static final Tutee GEORGE = new TuteeBuilder().withId(6).withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street, 897978").withHours("7").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withId(7).withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india, 123211").build();
    public static final Person IDA = new PersonBuilder().withId(8).withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave, 898888").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Tutor AMY = new TutorBuilder().withId(VALID_ID_AMY).withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withHours(VALID_HOURS_AMY).build();
    public static final Tutor BOB = new TutorBuilder().withId(VALID_ID_BOB).withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withHours(VALID_HOURS_BOB).build();
    public static final Tutee CLARA = new TuteeBuilder().withId(VALID_ID_CLARA).withName(VALID_NAME_CLARA)
            .withPhone(VALID_PHONE_CLARA).withEmail(VALID_EMAIL_CLARA).withAddress(VALID_ADDRESS_CLARA)
            .withHours(VALID_HOURS_CLARA).build();
    public static final Tutee DEACON = new TuteeBuilder().withId(VALID_ID_DEACON).withName(VALID_NAME_DEACON)
            .withPhone(VALID_PHONE_DEACON).withEmail(VALID_EMAIL_DEACON).withAddress(VALID_ADDRESS_DEACON)
            .withHours(VALID_HOURS_DEACON).build();


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

    public static AddressBook getTypicalAddressBookWithLesson() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        ab.addLesson(new Lesson(ALICE, DANIEL, new Subject("Math")));
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
