package seedu.sellsavvy.testutil;

import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_EMAIL_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_PHONE_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_PHONE_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalOrders.ATLAS;
import static seedu.sellsavvy.testutil.TypicalOrders.BLOCKS;
import static seedu.sellsavvy.testutil.TypicalOrders.BOTTLE;
import static seedu.sellsavvy.testutil.TypicalOrders.CAMERA;
import static seedu.sellsavvy.testutil.TypicalOrders.DAGGER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.customer.Customer;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final Customer ALICE = new CustomerBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends").withOrders().build();
    public static final Customer BENSON = new CustomerBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withOrders().build();
    public static final Customer CARL = new CustomerBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withOrders().build();
    public static final Customer DANIEL = new CustomerBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withTags("friends").withOrders(ABACUS, BLOCKS, CAMERA, DAGGER).build();
    public static final Customer ELLE = new CustomerBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withOrders().build();
    public static final Customer FIONA = new CustomerBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withOrders(ABACUS).build();
    public static final Customer GEORGE = new CustomerBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withOrders(ABACUS).build();

    // Manually added
    public static final Customer HOON = new CustomerBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withOrders(ATLAS, BOTTLE).build();
    public static final Customer IDA = new CustomerBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withOrders(ABACUS, BLOCKS, CAMERA, DAGGER).build();

    // Manually added - Customer's details found in {@code CustomerCommandTestUtil}
    public static final Customer AMY = new CustomerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withOrders().build();
    public static final Customer BOB = new CustomerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withOrders().build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCustomers() {} // prevents instantiation

    //TODO: AddressBook to include orders for each customer.
    /**
     * Returns an {@code AddressBook} with all the typical customers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Customer customer : getTypicalCustomers()) {
            ab.addCustomer(customer);
        }
        return ab;
    }

    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
