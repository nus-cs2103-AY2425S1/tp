package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
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

    @javafx.fxml.FXML
    private ListView<Delivery> deliveryListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public DeliveryListPanel(ObservableList<Delivery> deliveryList) {
        super(FXML);
        deliveryListView.setItems(deliveryList);
        deliveryListView.setCellFactory(listView -> new DeliveryListPanel.DeliveryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Delivery} using a {@code DeliveryCard}.
     */
    class DeliveryListViewCell extends ListCell<Delivery> {
        @Override
        protected void updateItem(Delivery delivery, boolean empty) {
            super.updateItem(delivery, empty);

            if (empty || delivery == null) {
                setGraphic(null);
                setText(null);
            } else {
                DeliveryCard deliveryCard = new DeliveryCard(delivery, getIndex() + 1);
                setGraphic(deliveryCard.getRoot());
            }
        }
    }

}
