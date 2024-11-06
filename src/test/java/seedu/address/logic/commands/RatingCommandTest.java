package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

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
    public void equals() {
        RatingCommand rateFirstCommand = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(5));
        RatingCommand rateSecondCommand = new RatingCommand(INDEX_SECOND_RESTAURANT, new Rating(5));
        assertFalse(rateFirstCommand.equals(rateSecondCommand));
    }

    @Test
    public void toStringMethod() {
        RatingCommand ratingCommand = new RatingCommand(INDEX_FIRST_RESTAURANT, new Rating(5));
        String expected = String.format("%s, Rating= %s", null, "5");
        assertEquals(expected, ratingCommand.toString());
    }
}
