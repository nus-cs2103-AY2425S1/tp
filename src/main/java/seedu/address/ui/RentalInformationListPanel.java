package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
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
    public RentalInformationListPanel(ObservableList<RentalInformation> rentalInformationList) {
        super(FXML);
        rentalInformationListView.setItems(rentalInformationList);
        rentalInformationListView.setCellFactory(listView -> new RentalInformationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code RentalInformation} using a
     * {@code RentalInformationCard}.
     */
    class RentalInformationListViewCell extends ListCell<RentalInformation> {
        @Override
        protected void updateItem(RentalInformation rentalInformation, boolean empty) {
            super.updateItem(rentalInformation, empty);

            if (empty || rentalInformation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RentalInformationCard(rentalInformation, getIndex() + 1).getRoot());
            }
        }
    }

}
