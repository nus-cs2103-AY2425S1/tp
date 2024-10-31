package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;

public class UnfavouriteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfavouriteSuccessful() {
        Restaurant restaurantToUnfavourite = model.getFilteredRestaurantList()
                .get(INDEX_FIRST_RESTAURANT.getZeroBased());

        model.favouriteRestaurant(restaurantToUnfavourite);

        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(INDEX_FIRST_RESTAURANT);

        Restaurant expectedRestaurantToUnfavourite = new Restaurant(
                restaurantToUnfavourite.getName(), restaurantToUnfavourite.getPhone(),
                restaurantToUnfavourite.getEmail(), restaurantToUnfavourite.getAddress(),
                restaurantToUnfavourite.getRating(), restaurantToUnfavourite.getTags(),
                restaurantToUnfavourite.isFavourite()
        );

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_SUCCESS,
                Messages.format(restaurantToUnfavourite));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favouriteRestaurant(expectedRestaurantToUnfavourite);
        expectedModel.unfavouriteRestaurant(expectedRestaurantToUnfavourite);

        // Assert that the command executes successfully
        assertCommandSuccess(unfavouriteCommand, model, expectedMessage, expectedModel);

        // Verify that the restaurant is not marked as favourite anymore
        assertFalse(restaurantToUnfavourite.isFavourite());

        // Verify that the restaurant is no longer at index 0 in the actual model's list
        assertNotEquals(restaurantToUnfavourite, model.getFilteredRestaurantList().get(0));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCommand, model, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_restaurantNotFavourited_throwsCommandException() {
        Restaurant restaurantToUnfavourite = model.getFilteredRestaurantList()
                .get(INDEX_FIRST_RESTAURANT.getZeroBased());

        assertFalse(restaurantToUnfavourite.isFavourite());

        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(INDEX_FIRST_RESTAURANT);

        assertCommandFailure(unfavouriteCommand, model, UnfavouriteCommand.MESSAGE_ALREADY_UNFAVOURITE);
    }

    @Test
    public void equals() {
        UnfavouriteCommand unfavouriteFirstCommand = new UnfavouriteCommand(INDEX_FIRST_RESTAURANT);
        UnfavouriteCommand unfavouriteSecondCommand = new UnfavouriteCommand(INDEX_SECOND_RESTAURANT);

        // same object -> returns true
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand unfavouriteFirstCommandCopy = new UnfavouriteCommand(INDEX_FIRST_RESTAURANT);
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavouriteFirstCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(unfavouriteFirstCommand.equals(unfavouriteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(targetIndex);
        String expected = UnfavouriteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unfavouriteCommand.toString());
    }
}
