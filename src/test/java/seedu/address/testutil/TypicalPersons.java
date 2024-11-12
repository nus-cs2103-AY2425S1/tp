package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_SIZE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_SIZE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withPhone("94351253")
            .withEmail("alice@example.com").withAddress("123, Jurong West Ave 6, #08-111")
            .withPriority("MEDIUM").withDateOfBirth(LocalDate.of(1999, 6, 10))
            .withIncome(1000).withTags("friends").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withPhone("98765432")
            .withEmail("johnd@example.com").withAddress("311, Clementi Ave 2, #02-25")
            .withPriority("HIGH").withDateOfBirth(LocalDate.of(1999, 1, 29))
            .withIncome(0).withTags("owesMoney", "friends").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withDateOfBirth(LocalDate.of(2000, 1, 1))
            .withIncome(970.50).build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withDateOfBirth(LocalDate.of(1990, 1, 1))
            .withIncome(2200.46).withTags("friends").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withDateOfBirth(LocalDate.of(2000, 5, 1))
            .withIncome(2532.00).build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withDateOfBirth(LocalDate.of(2000, 5, 31))
            .withIncome(1750.96).build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withDateOfBirth(LocalDate.of(2010, 12, 25))
            .withIncome(1300.54).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withDateOfBirth(LocalDate.of(2000, 1, 1))
            .withIncome(VALID_INCOME_AMY).withFamilySize(VALID_FAMILY_SIZE_AMY).withTags(VALID_TAG_FRIEND).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPriority(VALID_PRIORITY_BOB)
            .withDateOfBirth(LocalDate.of(1989, 1, 3)).withIncome(VALID_INCOME_BOB)
            .withFamilySize(VALID_FAMILY_SIZE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    // Manually added - Terry presenting rich while Berry is poor while Cherry is moderate
    public static final Person TERRY = new PersonBuilder().withName("Terry").withPhone("98765432")
            .withEmail("terry@example.com").withAddress("311, Clementi Ave 2, #02-25")
            .withDateOfBirth(LocalDate.of(1999, 1, 29)).withIncome(1000000).withFamilySize(1).build();

    public static final Person BERRY = new PersonBuilder().withName("Berry").withPhone("98765432")
            .withEmail("berry@example.com").withAddress("311, Clementi Ave 2, #02-25")
            .withDateOfBirth(LocalDate.of(1999, 1, 29)).withIncome(100).withFamilySize(1).build();

    public static final Person CHERRY = new PersonBuilder().withName("Cherry").withPhone("98765432")
            .withEmail("cheery@example.com").withAddress("311, Clementi Ave 2, #02-25")
            .withDateOfBirth(LocalDate.of(1999, 1, 29)).withIncome(4000).withFamilySize(1).build();

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

    public static AddressBook getTypicalAddressBook2() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons2()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static AddressBook getTypicalAddressBook3() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons3()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersons2() {
        return new ArrayList<>(Arrays.asList(BENSON, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersons3() {
        return new ArrayList<>(Arrays.asList(TERRY, BERRY, CHERRY));
    }
}
