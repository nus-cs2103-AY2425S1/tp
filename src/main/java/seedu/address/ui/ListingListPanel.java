package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.listing.Listing;

/**
 * Panel containing the list of persons.
 */
public class ListingListPanel extends UiPart<Region> {
    private static final String FXML = "ListingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Listing> listingListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ListingListPanel(ObservableList<Listing> listingList) {
        super(FXML);
        listingListView.setItems(listingList);
        listingListView.setCellFactory(listView -> new ListingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ListingListViewCell extends ListCell<Listing> {
        @Override
        protected void updateItem(Listing listing, boolean empty) {
            super.updateItem(listing, empty);

            if (empty || listing == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ListingCard(listing, getIndex() + 1).getRoot());
            }
        }
    }

}
