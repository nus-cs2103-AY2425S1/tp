package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class FavouriteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_favouriteSuccessful() {
        Restaurant restaurantToFavourite = model.getFilteredRestaurantList()
                .get(INDEX_SECOND_RESTAURANT.getZeroBased());
        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_SECOND_RESTAURANT);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_SUCCESS,
                Messages.format(restaurantToFavourite));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favouriteRestaurant(restaurantToFavourite);

        // Assert that the command executes successfully
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);

        // Verify that the restaurant is marked as favorite
        assertTrue(restaurantToFavourite.isFavourite());

        // Verify that the restaurant is now at index 0 in the actual model's list
        assertEquals(restaurantToFavourite, model.getFilteredRestaurantList().get(0));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        FavouriteCommand favouriteCommand = new FavouriteCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(INDEX_FIRST_RESTAURANT);
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(INDEX_SECOND_RESTAURANT);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand favouriteFirstCommandCopy = new FavouriteCommand(INDEX_FIRST_RESTAURANT);
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        FavouriteCommand favouriteCommand = new FavouriteCommand(targetIndex);
        String expected = FavouriteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, favouriteCommand.toString());
    }
}
