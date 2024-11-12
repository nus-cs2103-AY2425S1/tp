package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MAKE_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MAKE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MODEL_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MODEL_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VIN_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VIN_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VRN_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VRN_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISSUE_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISSUE_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

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
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withIssues("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withIssues("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withIssues("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    public static final Person ALICE_WITH_CAR = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withIssues("friends")
            .withCar(VALID_CAR_VRN_A, VALID_CAR_VIN_A, VALID_CAR_MAKE_A, VALID_CAR_MODEL_A).build();

    public static final Person BENSON_WITH_CAR = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withIssues("owesMoney", "friends")
            .withCar(VALID_CAR_VRN_B, VALID_CAR_VIN_B, VALID_CAR_MAKE_B, VALID_CAR_MODEL_B).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withIssues(VALID_ISSUE_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withIssues(VALID_ISSUE_HUSBAND, VALID_ISSUE_FRIEND)
            .build();
    public static final Person AMY_WITH_CAR = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withIssues(VALID_ISSUE_FRIEND)
            .withCar(VALID_CAR_VRN_A, VALID_CAR_VIN_A, VALID_CAR_MAKE_A, VALID_CAR_MODEL_A).build();
    public static final Person BOB_WITH_CAR = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withIssues(VALID_ISSUE_HUSBAND, VALID_ISSUE_FRIEND)
            .withCar(VALID_CAR_VRN_B, VALID_CAR_VIN_B, VALID_CAR_MAKE_B, VALID_CAR_MODEL_B).build();

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
     * Returns an {@code AddressBook} with all the typical persons with car.
     */
    public static AddressBook getTypicalAddressBookSomeWithCars() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsSomeWithCars()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static AddressBook getTypicalAddressBookAllWithCars() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsAllWithCars()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersonsSomeWithCars() {
        return new ArrayList<>(Arrays.asList(AMY_WITH_CAR, CARL, DANIEL, ELLE, FIONA, GEORGE, BENSON_WITH_CAR));
    }

    public static List<Person> getTypicalPersonsAllWithCars() {
        return new ArrayList<>(Arrays.asList(BOB_WITH_CAR, AMY_WITH_CAR));
    }
}
