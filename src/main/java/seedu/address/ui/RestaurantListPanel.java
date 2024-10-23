package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.restaurant.Restaurant;

/**
 * Panel containing the list of persons.
 */
public class RestaurantListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RestaurantListPanel.class);

    @FXML
    private ListView<Restaurant> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public RestaurantListPanel(ObservableList<Restaurant> restaurantList) {
        super(FXML);
        personListView.setItems(restaurantList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Restaurant> {
        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);

            if (empty || restaurant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RestaurantCard(restaurant, getIndex() + 1).getRoot());
            }
        }
    }

}
