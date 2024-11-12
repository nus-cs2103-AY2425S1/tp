package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.wedding.Wedding;

/**
 * Panel containing the list of weddings.
 */
public class WeddingListPanel extends ListPanel<Wedding> {
    private static final String FXML = "WeddingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WeddingListPanel.class);

    @FXML
    private ListView<Wedding> weddingListView;

    /**
     * Creates a {@code WeddingListPanel} with the given {@code ObservableList}.
     */
    public WeddingListPanel(ObservableList<Wedding> weddingList) {
        super(FXML);
        weddingListView.setItems(weddingList);
        weddingListView.setCellFactory(listView -> new WeddingListViewCell());
    }

    /**
     * Updates the {@code WeddingListView} with an updated list of weddings.
     */
    public void updateWeddingList(ObservableList<Wedding> weddingList) {
        weddingListView.setItems(weddingList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Wedding} using a {@code WeddingCard}.
     */
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
