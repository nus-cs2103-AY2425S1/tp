package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.delivery.Delivery;

/**
 * Panel containing the list of deliveries.
 */
public class DeliveryListPanel extends UiPart<Region> {
    private static final String FXML = "DeliveryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeliveryListPanel.class);

    @FXML
    private ListView<Delivery> deliveryListView;

    /**
     * Creates a {@code DeliveryListPanel} with the given {@code ObservableList}.
     */
    public DeliveryListPanel(ObservableList<Delivery> deliveryList) {
        super(FXML);
        deliveryListView.setItems(deliveryList);
        deliveryListView.setCellFactory(listView -> new DeliveryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Delivery} using a {@code DeliveryCard}.
     */
    static class DeliveryListViewCell extends ListCell<Delivery> {

        @Override
        protected void updateItem(Delivery delivery, boolean empty) {
            super.updateItem(delivery, empty);

            if (empty || delivery == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeliveryCard(delivery, getIndex() + 1).getRoot());
            }
        }
    }
}


