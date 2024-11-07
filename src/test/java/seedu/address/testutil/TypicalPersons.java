package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_NOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC_SUB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_SOL_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_SOL_SUB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.EthAddress;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.SolAddress;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final PublicAddress BTC_MAIN_ADDRESS =
            new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN, "Main wallet");
    public static final PublicAddress BTC_MAIN_ADDRESS_ALT =
            new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN, "Main wallet");
    public static final PublicAddress BTC_SUB_ADDRESS =
            new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_SUB, "Sub wallet");
    public static final PublicAddress BTC_NOT_IN_ADDRESS_BOOK_ADDRESS =
            new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_NOT, "Sub wallet");
    public static final PublicAddress ETH_MAIN_ADDRESS =
            new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN, "Main wallet");
    public static final PublicAddress ETH_SUB_ADDRESS =
            new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN, "Sub wallet");
    public static final PublicAddress SOL_MAIN_ADDRESS =
            new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN, "Main wallet");

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withPublicAddresses(BTC_MAIN_ADDRESS, BTC_SUB_ADDRESS)
            .withTags("friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withPublicAddresses(SOL_MAIN_ADDRESS).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();
    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();
    public static final Person JOE = new PersonBuilder().withName("Joe Mama").withPhone("87654321")
            .withEmail("joe@example.com").withAddress("kent ridge")
            .withPublicAddresses(BTC_MAIN_ADDRESS, BTC_SUB_ADDRESS, ETH_MAIN_ADDRESS).build();
    public static final Person MOE = new PersonBuilder().withName("Moe Papa").withPhone("99887766")
            .withEmail("moe@example.com").withAddress("nus")
            .withPublicAddresses(BTC_MAIN_ADDRESS, SOL_MAIN_ADDRESS).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    static final PublicAddress SOL_SUB_ADDRESS =
            new SolAddress(VALID_PUBLIC_ADDRESS_SOL_SUB, "Sub wallet");
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withPublicAddresses(SOL_MAIN_ADDRESS, SOL_SUB_ADDRESS)
            .withTags("owesMoney", "friends").build();


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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
