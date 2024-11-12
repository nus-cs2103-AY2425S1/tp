package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withStudentId("E0000000")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withStudentId("E0000001")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withStudentId("E0000002")
            .withPhone("95352563").withEmail("heinz@example.com").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withStudentId("E0000003")
            .withPhone("87652533").withEmail("cornelia@example.com")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withStudentId("E0000004")
            .withPhone("9482224").withEmail("werner@example.com").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withStudentId("E0000005")
            .withPhone("9482427").withEmail("lydia@example.com").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withStudentId("E0000006")
            .withPhone("9482442").withEmail("anna@example.com").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withStudentId("E0000009").withPhone("8482424").withEmail("stefan@example.com").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withStudentId("E0000010").withPhone("8482131").withEmail("hans@example.com").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withStudentId(VALID_STUDENTID_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    //Manually added
    public static final Person ADAM = new PersonBuilder().withName("Adam Meier")
            .withStudentId("E0000011")
            .withEmail("adam@example.com")
            .withPhone("91131253")
            .withTags("friends")
            .withTutorials(new String[]{"1"}, new AttendanceStatus[]{AttendanceStatus.PRESENT}).build();
    public static final Person BETTY = new PersonBuilder().withName("Betty Curt")
            .withStudentId("E0000012")
            .withEmail("betty@example.com")
            .withPhone("91405432")
            .withTags("owesMoney", "friends")
            .withTutorials(new String[]{"1"}, new AttendanceStatus[]{AttendanceStatus.ABSENT}).build();
    public static final Person CLAIRE = new PersonBuilder().withName("Claire Kurz")
            .withStudentId("E0000013")
            .withEmail("claire@example.com")
            .withPhone("91150063")
            .withTutorials(new String[]{"1"}, new AttendanceStatus[]{AttendanceStatus.NOT_TAKEN_PLACE}).build();

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

    /**
     * Returns an {@code AddressBook} with all the typical persons with a specific tutorial marked as present.
     */
    public static AddressBook getMarkedAddressBook(String tutorialIndex) {
        AddressBook ab = new AddressBook();
        for (Person person : getMarkedPersons(tutorialIndex)) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons in random order.
     */
    public static AddressBook getShuffledTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getShuffledTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons
     * and their specified tutorial attendance in random order.
     */
    public static AddressBook getShuffledTypicalAddressBookWithTutorials() {
        AddressBook ab = new AddressBook();
        for (Person person : getShuffledTypicalPersonsWithTutorials()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getMarkedPersons(String tutorialIndex) {
        List<Person> markedPersons = new ArrayList<>();
        for (Person p : getTypicalPersons()) {
            markedPersons.add(new PersonBuilder(p)
                    .withAmendedTutorial(tutorialIndex, AttendanceStatus.PRESENT)
                    .build());
        }
        return markedPersons;
    }

    public static List<Person> getShuffledTypicalPersons() {
        List<Person> shuffledList = new ArrayList<>(getTypicalPersons());
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    public static List<Person> getTypicalPersonsWithTutorials() {
        return new ArrayList<>(Arrays.asList(ADAM, BETTY, CLAIRE));
    }

    public static List<Person> getShuffledTypicalPersonsWithTutorials() {
        List<Person> shuffledList = new ArrayList<>(getTypicalPersonsWithTutorials());
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
