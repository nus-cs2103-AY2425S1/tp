package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;

/**
 * Contains integration tests (interaction with the Model) for {@code RatingCommand}.
 */
public class RatingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Restaurant restaurantToRate = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        RatingCommand ratingCommand = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(5));

        Restaurant ratedRestaurant = new Restaurant(
                restaurantToRate.getName(),
                restaurantToRate.getPhone(),
                restaurantToRate.getEmail(),
                restaurantToRate.getAddress(),
                new Rating(5),
                restaurantToRate.getTags(),
                restaurantToRate.getPrice(),
                restaurantToRate.isFavourite()
        );

        String expectedMessage = String.format(RatingCommand.MESSAGE_ADD_RATING_SUCCESS,
                restaurantToRate.getName(), ratedRestaurant.getRating());

        expectedModel.setRestaurant(restaurantToRate, ratedRestaurant);
        assertCommandSuccess(ratingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_removeRating() {
        Restaurant restaurantToRate = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        RatingCommand ratingCommand = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(null));

        Restaurant unratedRestaurant = new Restaurant(
                restaurantToRate.getName(),
                restaurantToRate.getPhone(),
                restaurantToRate.getEmail(),
                restaurantToRate.getAddress(),
                new Rating(null),
                restaurantToRate.getTags(),
                restaurantToRate.getPrice(),
                restaurantToRate.isFavourite()
        );

        String expectedMessage = String.format(RatingCommand.MESSAGE_REMOVE_RATING_SUCCESS,
                restaurantToRate.getName());

        expectedModel.setRestaurant(restaurantToRate, unratedRestaurant);
        assertCommandSuccess(ratingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        RatingCommand ratingCommand = new RatingCommand(outOfBoundIndex, new Rating(5));

        assertCommandFailure(ratingCommand, model, MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RatingCommand rateFirstCommand = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(5));
        RatingCommand rateSecondCommand = new RatingCommand(INDEX_SECOND_RESTAURANT, new Rating(5));
        RatingCommand rateFirstCommandDifferentRating = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(7));

        // same object -> returns true
        assertTrue(rateFirstCommand.equals(rateFirstCommand));

        // different types -> returns false
        assertFalse(rateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(rateFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(rateFirstCommand.equals(rateSecondCommand));

        // different rating -> returns false
        assertFalse(rateFirstCommand.equals(rateFirstCommandDifferentRating));
    }

    @Test
    public void toStringMethod() {
        RatingCommand ratingCommand = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(5));
        String expected = String.format("%s, Rating= %s", null, "5");
        assertEquals(expected, ratingCommand.toString());
    }
}
