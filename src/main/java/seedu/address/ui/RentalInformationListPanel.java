package seedu.address.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Panel containing the list of rental information.
 */
public class RentalInformationListPanel extends UiPart<Region> {
    private static final String FXML = "RentalInformationListPanel.fxml";

    @FXML
    private ListView<RentalInformation> rentalInformationListView;

    @FXML
    private Label ownerName;

    /**
     * Creates a {@code RentalInformationListPanel} with the given {@code ObservableList}.
     */
    public RentalInformationListPanel(ObservableList<RentalInformation> rentalInformationList,
                                      ObjectProperty<Client> client) {
        super(FXML);
        rentalInformationListView.setItems(rentalInformationList);
        rentalInformationListView.setCellFactory(listView -> new RentalInformationListViewCell());
        ownerName.setText("No Rental Information currently, Use rview CLIENT_INDEX");

        rentalInformationListView.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        ownerName.setText("Selected Rental: " + newValue.getAddress());
                    } else {
                        updateOwnerName(rentalInformationList, client);
                    }
                });

        rentalInformationList.addListener((ListChangeListener<RentalInformation>) change -> {
            updateOwnerName(rentalInformationList, client);
        });

        client.addListener((observable, oldValue, newValue) -> {
            updateOwnerName(rentalInformationList, client);
        });
    }

    /**
     * Updates the displayed client's name based on the current state of the rental information list
     * and the provided client property.
     *
     * @param rentalInformationList The list of rental information items, which may affect the
     *                              label's displayed message if empty or populated.
     * @param client An observable property holding the current client; this
     *               property is checked to update the label with the client's name.
     */
    private void updateOwnerName(ObservableList<RentalInformation> rentalInformationList,
                                 ObjectProperty<Client> client) {
        if (rentalInformationList.isEmpty()) {
            ownerName.setText("No Rental Information currently, Use rview CLIENT_INDEX");
        } else {
            Client currentClient = client.get();
            if (currentClient != null) {
                ownerName.setText(currentClient.getName() + "'s Rental Information: ");
            } else {
                ownerName.setText("No Rental Information currently, Use rview CLIENT_INDEX");
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code RentalInformation} using a
     * {@code RentalInformationListCard}.
     */
    class RentalInformationListViewCell extends ListCell<RentalInformation> {
        @Override
        protected void updateItem(RentalInformation rentalInformation, boolean empty) {
            super.updateItem(rentalInformation, empty);

            if (empty || rentalInformation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RentalInformationListCard(rentalInformation, getIndex() + 1).getRoot());
            }
        }
    }
}
