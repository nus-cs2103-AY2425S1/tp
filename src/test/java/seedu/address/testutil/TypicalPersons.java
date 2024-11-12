package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BLANK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DOMINIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ERIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEIGHBOR;
import static seedu.address.testutil.TypicalTags.FLORIST;
import static seedu.address.testutil.TypicalTags.PHOTOGRAPHER;
import static seedu.address.testutil.TypicalTasks.DEADLINE_TASK;
import static seedu.address.testutil.TypicalTasks.TODO_TASK;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;
import static seedu.address.testutil.TypicalWeddings.CARLA_WEDDING;
import static seedu.address.testutil.TypicalWeddings.CLIVE_WEDDING;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("photographer").withWeddings("Amy's Wedding").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("florist", "photographer").withWeddings("Wedding 2", "Carla's Wedding").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withTasks("todo: Buy groceries")
            .withVendorStatus("true").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("florist").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withWeddings("Wedding 2").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    public static final Person CLIVE = new PersonBuilder().withName(VALID_NAME_CLIVE).withPhone(VALID_PHONE_CLIVE)
            .withEmail(VALID_EMAIL_CLIVE).withAddress(VALID_ADDRESS_BLANK).withTags(VALID_TAG_NEIGHBOR).build();
    public static final Person DOMINIC = new PersonBuilder().withName(VALID_NAME_DOMINIC).withPhone(VALID_PHONE_DOMINIC)
            .withEmail(VALID_EMAIL_DOMINIC).withAddress(VALID_ADDRESS_DOMINIC).withTags(VALID_TAG_FRIEND)
            .build();
    public static final Person ERIC = new PersonBuilder().withName(VALID_NAME_ERIC).withPhone(VALID_PHONE_ERIC)
            .withEmail(VALID_EMAIL_ERIC).withAddress(VALID_ADDRESS_ERIC).withTags(VALID_TAG_NEIGHBOR)
            .build();

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.addTag(FLORIST);
        ab.addTag(PHOTOGRAPHER);
        ab.addWedding(AMY_WEDDING);
        ab.addWedding(BOB_WEDDING);
        ab.addWedding(WEDDING_TWO);
        ab.addWedding(CARLA_WEDDING);
        ab.addWedding(CLIVE_WEDDING);
        ab.addTask(TODO_TASK);
        ab.addTask(DEADLINE_TASK);

        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
            for (Wedding wedding : person.getWeddings()) {
                if (!ab.hasWedding(wedding)) {
                    ab.addWedding(wedding);
                }
                Wedding weddingInAb = ab.getWedding(wedding);
                weddingInAb.addToGuestList(person);
            }
            for (Tag tag : person.getTags()) {
                if (!ab.hasTag(tag)) {
                    ab.addTag(tag);
                }
                Tag tagInAb = ab.getTag(tag);
                tagInAb.increaseTaggedCount();
            }
        }

        for (Wedding wedding : ab.getWeddingList()) {
            for (Person person : wedding.getGuestList()) {
                person.addWedding(wedding);
            }
        }
        return ab;
    }

    /**
     * Returns an address book with the persons reset to their original values
     */
    public static AddressBook resetTypicalAddressBook() {
        AddressBook resetAddressBook = getTypicalAddressBook();
        resetAddressBook.setPersons(resetTypicalPersons());
        resetAddressBook.setTags(List.of(FLORIST, PHOTOGRAPHER));
        resetAddressBook.setWeddings(List.of(AMY_WEDDING, BOB_WEDDING, WEDDING_TWO, CARLA_WEDDING, CLIVE_WEDDING));
        resetAddressBook.setTasks(List.of(TODO_TASK, DEADLINE_TASK));
        return resetAddressBook;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Returns a list of the typical people in the address book, reset with their original values
     */
    public static List<Person> resetTypicalPersons() {
        return List.of(
                new PersonBuilder().withName("Alice Pauline")
                        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                        .withPhone("94351253")
                        .withTags("photographer").withWeddings("Amy's Wedding").build(),
                new PersonBuilder().withName("Benson Meier")
                        .withAddress("311, Clementi Ave 2, #02-25")
                        .withEmail("johnd@example.com").withPhone("98765432")
                        .withTags("florist", "photographer").withWeddings("Wedding 2", "Carla's Wedding").build(),
                new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
                        .withEmail("heinz@example.com").withAddress("wall street").withTasks("todo: Buy groceries")
                        .withVendorStatus("true").build(),
                new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                        .withEmail("cornelia@example.com").withAddress("10th street").withTags("florist").build(),
                new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
                        .withEmail("werner@example.com").withAddress("michegan ave").withWeddings("Wedding 2").build(),
                new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
                        .withEmail("lydia@example.com").withAddress("little tokyo").build(),
                new PersonBuilder().withName("George Best").withPhone("9482442")
                        .withEmail("anna@example.com").withAddress("4th street").build()
        );
    }
}
