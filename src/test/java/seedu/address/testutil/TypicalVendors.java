package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BOB;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalVendors {

    public static final Vendor AVA = new VendorBuilder().withName("Ava Johnson")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("ava.johnson@example.com")
            .withPhone("94351253")
            .withCompany("companyName")
            .withTags("friends")
            .withBudget(5000.00)
            .build();

    public static final Vendor BRIAN = new VendorBuilder().withName("Brian Lee")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("brian.lee@example.com")
            .withPhone("98765432")
            .withCompany("companyName")
            .withTags("owesMoney", "friends")
            .withBudget(3500.00)
            .build();

    public static final Vendor CAM = new VendorBuilder().withName("Cameron White")
            .withPhone("95352563")
            .withEmail("cameron.white@example.com")
            .withCompany("companyName")
            .withAddress("wall street")
            .withBudget(2500.00)
            .build();

    public static final Vendor DAVID = new VendorBuilder().withName("David Brown")
            .withPhone("87652533")
            .withEmail("david.brown@example.com")
            .withAddress("10th street")
            .withCompany("companyName")
            .withTags("friends")
            .withBudget(2000.00)
            .build();

    public static final Vendor ELLA = new VendorBuilder().withName("Ella Thompson")
            .withPhone("9482224")
            .withEmail("ella.thompson@example.com")
            .withCompany("companyName")
            .withAddress("michegan ave")
            .withBudget(4500.00)
            .build();

    public static final Vendor GAVIN = new VendorBuilder().withName("Gavin Kim")
            .withPhone("9482442")
            .withEmail("gavin.kim@example.com")
            .withCompany("companyName")
            .withAddress("4th street")
            .withBudget(3000.00)
            .build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Vendor AMY = new VendorBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withCompany(VALID_COMPANY_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withBudget(VALID_BUDGET_AMY)
            .build();

    public static final Vendor BOB = new VendorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withCompany(VALID_COMPANY_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withBudget(VALID_BUDGET_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalVendors() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical guests.
     */
    public static AddressBook getTypicalAddressBookWithVendors() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalVendors()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Vendor> getTypicalVendors() {
        return new ArrayList<>(Arrays.asList(AVA, BRIAN, CAM, DAVID, ELLA, GAVIN));
    }
}
