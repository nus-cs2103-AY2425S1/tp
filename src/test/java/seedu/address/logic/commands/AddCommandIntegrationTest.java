package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.testutil.RestaurantBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newRestaurant_success() {
        Restaurant validRestaurant = new RestaurantBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addRestaurant(validRestaurant);

        assertCommandSuccess(new AddCommand(validRestaurant), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validRestaurant)),
                expectedModel);
    }

    @Test
    public void execute_duplicateRestaurant_throwsCommandException() {
        Restaurant restaurantInList = model.getAddressBook().getRestaurantList().get(0);
        assertCommandFailure(new AddCommand(restaurantInList), model,
                AddCommand.MESSAGE_DUPLICATE_RESTAURANT);
    }

}
