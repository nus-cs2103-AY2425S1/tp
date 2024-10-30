package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRestaurants.ALICE;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.exceptions.DuplicateRestaurantException;
import seedu.address.testutil.RestaurantBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getRestaurantList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateRestaurants_throwsDuplicateRestaurantException() {
        // Two restaurants with the same identity fields
        Restaurant editedAlice = new RestaurantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Restaurant> newRestaurants = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newRestaurants);

        assertThrows(DuplicateRestaurantException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasRestaurant_nullRestaurant_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasRestaurant(null));
    }

    @Test
    public void hasRestaurant_restaurantNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasRestaurant(ALICE));
    }

    @Test
    public void hasRestaurant_restaurantInAddressBook_returnsTrue() {
        addressBook.addRestaurant(ALICE);
        assertTrue(addressBook.hasRestaurant(ALICE));
    }

    @Test
    public void hasRestaurant_restaurantWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addRestaurant(ALICE);
        Restaurant editedAlice = new RestaurantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasRestaurant(editedAlice));
    }

    @Test
    public void getRestaurantList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getRestaurantList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{restaurants=" + addressBook.getRestaurantList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose restaurants list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();

        AddressBookStub(Collection<Restaurant> restaurants) {
            this.restaurants.setAll(restaurants);
        }

        @Override
        public ObservableList<Restaurant> getRestaurantList() {
            return restaurants;
        }
    }

}
