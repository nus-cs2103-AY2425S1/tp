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
                .get(INDEX_SECOND_RESTAURANT.getZeroBased());

        restaurantToUnfavourite.setFavourite(true);

        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(INDEX_SECOND_RESTAURANT);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_SUCCESS,
                Messages.format(restaurantToUnfavourite));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favouriteRestaurant(restaurantToUnfavourite);
        expectedModel.unfavouriteRestaurant(restaurantToUnfavourite);

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
