package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;

/**
 * Panel containing the list of rental information.
 */
public class RentalInformationListPanel extends UiPart<Region> {
    private static final String FXML = "RentalInformationListPanel.fxml";

    @FXML
    private ListView<Client> rentalInformationListView;

    /**
     * Creates a {@code RentalInformationListPanel} with the given {@code ObservableList}.
     */
    public RentalInformationListPanel(ObservableList<Client> clientList) {
        super(FXML);
        rentalInformationListView.setItems(clientList);
        rentalInformationListView.setCellFactory(listView -> new RentalInformationListViewCell());
    }

    class RentalInformationListViewCell extends ListCell<Client> {
        @Override
        protected void updateItem(Client client, boolean empty) {
            super.updateItem(client, empty);

            if (empty || client == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RentalInformationCard(client, getIndex() + 1).getRoot());
            }
        }
    }

}
