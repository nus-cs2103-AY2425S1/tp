package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.restaurant.Restaurant;

/**
 * A utility class containing a list of {@code Person} objects to be used in
 * tests.
 */
public class TypicalRestaurants {

    public static final Restaurant ALICE = new RestaurantBuilder()
            .withName("Alice Pauline Restaurant")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("italian")
            .withPrice("$$")
            .withIsFavourite(false)
            .build();
    public static final Restaurant BENSON = new RestaurantBuilder()
            .withName("Benson Meier Restaurant")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withRating(5)
            .withTags("western", "outdoors")
            .withPrice("$$$")
            .withIsFavourite(false)
            .build();
    public static final Restaurant CARL = new RestaurantBuilder()
            .withName("Carl Kurz Restaurant")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withPrice("$$")
            .build();
    public static final Restaurant DANIEL = new RestaurantBuilder()
            .withName("Daniel Meier Restaurant")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends")
            .withPrice("$$$")
            .build();
    public static final Restaurant ELLE = new RestaurantBuilder()
            .withName("Elle Meyer Restaurant")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withPrice("$$$")
            .build();
    public static final Restaurant FIONA = new RestaurantBuilder()
            .withName("Fiona Kunz Restaurant")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withPrice("$")
            .build();
    public static final Restaurant GEORGE = new RestaurantBuilder()
            .withName("George Best Restaurant")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withPrice("$")
            .build();

    // Manually added
    public static final Restaurant HOON = new RestaurantBuilder()
            .withName("Hoon Meier Restaurant")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withPrice("$$$$")
            .build();
    public static final Restaurant IDA = new RestaurantBuilder()
            .withName("Ida Mueller Restaurant")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withPrice("$$$$")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Restaurant AMY = new RestaurantBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withPrice(VALID_PRICE_AMY)
            .build();
    public static final Restaurant BOB = new RestaurantBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withPrice(VALID_PRICE_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRestaurants() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Restaurant restaurant : getTypicalPersons()) {
            ab.addRestaurant(restaurant);
        }
        return ab;
    }

    public static List<Restaurant> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
