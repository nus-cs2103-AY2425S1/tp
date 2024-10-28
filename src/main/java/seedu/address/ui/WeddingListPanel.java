package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.wedding.Wedding;

/**
 * Panel containing the list of weddings.
 */
public class WeddingListPanel extends UiPart<Region> {
    private static final String FXML = "WeddingListPanel.fxml";

    @FXML
    private VBox currentWeddingNameCardContainer;

    @FXML
    private ListView<Wedding> weddingListView;

    /**
     * Creates a {@code WeddingListPanel} with the given {@code ObservableList}.
     */
    public WeddingListPanel(ObservableList<Wedding> weddingList) {
        super(FXML);  // Loads the FXML and initializes FXML fields

        // Set up CurrentWeddingNameCardContainer with dummy wedding name
        CurrentWeddingNameCard currentWeddingNameCard = new CurrentWeddingNameCard();
        currentWeddingNameCard.setCurrentWeddingName("John and Jane");  // Dummy name
        currentWeddingNameCardContainer.getChildren().add(currentWeddingNameCard.getRoot());  // Add the card to the VBox

        // Set up the ListView with weddings
        weddingListView.setItems(weddingList);
        weddingListView.setCellFactory(listView -> new WeddingListViewCell());
    }

    class WeddingListViewCell extends ListCell<Wedding> {
        @Override
        protected void updateItem(Wedding wedding, boolean empty) {
            super.updateItem(wedding, empty);

            if (empty || wedding == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WeddingCard(wedding, getIndex() + 1).getRoot());
            }
        }
    }
}