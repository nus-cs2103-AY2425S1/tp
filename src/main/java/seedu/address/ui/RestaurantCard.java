package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.restaurant.Restaurant;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class RestaurantCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX. As a consequence, UI elements' variable names cannot
     * be set to such keywords or an exception will be thrown by JavaFX during
     * runtime.
     *
     * @see
     * <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     * issue on AddressBook level 4</a>
     */
    public final Restaurant restaurant;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label rating;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane prices;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public RestaurantCard(Restaurant restaurant, int displayedIndex) {
        super(FXML);
        this.restaurant = restaurant;
        id.setText(displayedIndex + ". ");
        name.setText(restaurant.getName().fullName);
        phone.setText(restaurant.getPhone().value);
        address.setText(restaurant.getAddress().value);
        email.setText(restaurant.getEmail().value);
        rating.setText(restaurant.getRating().getStringValue());
        restaurant.getTagsWithoutPrice().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        restaurant.getPriceTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> prices.getChildren().add(new Label(tag.tagName)));

        if (restaurant.isFavourite()) {
            cardPane.getStyleClass().add("favourite");
        } else {
            cardPane.getStyleClass().remove("favourite");
        }
    }
}
