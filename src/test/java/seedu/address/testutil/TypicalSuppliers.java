package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_RICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.supplier.Supplier;

/**
 * A utility class containing a list of {@code Supplier} objects to be used in tests.
 */
public class TypicalSuppliers {

    public static final Supplier ALICE = new SupplierBuilder().withName("Alice Pauline")
            .withCompany("company A").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withProducts("bread")
            .withStatus("active")
            .build();
    public static final Supplier BENSON = new SupplierBuilder().withName("Benson Meier")
            .withCompany("company B")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withProducts("rice")
            .withStatus("active")
            .build();
    public static final Supplier CARL = new SupplierBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withCompany("company C").withProducts("pasta").build();
    public static final Supplier DANIEL = new SupplierBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withCompany("company D").withTags("friends").build();
    public static final Supplier ELLE = new SupplierBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withCompany("company E").withProducts("coca cola").build();
    public static final Supplier FIONA = new SupplierBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withCompany("company F").withProducts("power tools").build();
    public static final Supplier GEORGE = new SupplierBuilder().withName("George Pauline").withPhone("9482442")
            .withEmail("anna@example.com").withCompany("company A").build();

    // Manually added
    public static final Supplier HOON = new SupplierBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withCompany("companyA H").withProducts("swiss army knife").build();
    public static final Supplier IDA = new SupplierBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withCompany("company I").build();

    // Manually added - Supplier's details found in {@code CommandTestUtil}
    public static final Supplier AMY = new SupplierBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY).withTags(VALID_TAG_FRIEND)
            .withProducts(VALID_PRODUCT_BREAD, VALID_PRODUCT_RICE).withStatus(VALID_STATUS_AMY).build();
    public static final Supplier BOB = new SupplierBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withProducts(VALID_PRODUCT_BREAD).withStatus(VALID_STATUS_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSuppliers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical suppliers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Supplier supplier : getTypicalSuppliers()) {
            ab.addSupplier(supplier);
        }
        return ab;
    }

    public static List<Supplier> getTypicalSuppliers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
