package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.RandomPersonGenerator.generateRandomPersons;

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
            .withPhone("94351253").withGender("female").withTags("friends").withModules("MA1522").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432").withGender("male").withTags("owesMoney", "friends").withModules("MA1522").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563").withGender("male").withModules("EL1101").withTags("runner").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533").withGender("male").withTags("friends").withModules("CS1101").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94820224").withGender("female").withModules("MA1522").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94824207").withGender("female").withModules("MA1522").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94824402").withGender("male").withModules("EL1101").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84082424").withGender("male").withModules("MA1522").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84821301").withGender("female").withModules("CS2103T").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withGender(VALID_GENDER_AMY).withTags(VALID_TAG_FRIEND).withModules(VALID_MODULE_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withModules(VALID_MODULE_BOB).build();


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
     * Returns an {@code AddressBook} with 1000 random generated persons for stress testing.
     */
    public static AddressBook getStressTestAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : generateRandomPersons(1000)) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
