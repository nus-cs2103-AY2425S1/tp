package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.concert.Concert;

/**
 * Panel containing the list of Concerts.
 */
public class ConcertListPanel extends UiPart<Region> {
    private static final String FXML = "ConcertListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ConcertListPanel.class);

    @FXML
    private ListView<Concert> concertListView;

    /**
     * Creates a {@code ConcertListPanel} with the given {@code ObservableList}.
     */
    public ConcertListPanel(ObservableList<Concert> concertList) {
        super(FXML);
        concertListView.setItems(concertList);
        concertListView.setCellFactory(listView -> new ConcertListViewCell(false));
    }

    /**
     * Sets the list to show full details of its {@code Concert} cards.
     */
    public void showFullConcert() {
        concertListView.setCellFactory(listView -> new ConcertListViewCell(true));
        concertListView.refresh();
    }

    /**
     * Sets the list to hide full details of its {@code Concert} cards.
     */
    public void hideFullConcert() {
        concertListView.setCellFactory(listView -> new ConcertListViewCell(false));
        concertListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Concert} using a {@code ConcertCard}.
     */
    class ConcertListViewCell extends ListCell<Concert> {
        private final boolean showFullDetails;

        /**
         * Creates a {@code ConcertListViewCell} with {@param showFullDetails} to indicate whether the details
         * of the {@code ConcertCard} should be truncated or wrapped to a new line.
         *
         * @param showFullDetails
         */
        protected ConcertListViewCell(boolean showFullDetails) {
            this.showFullDetails = showFullDetails;
        }

        @Override
        protected void updateItem(Concert concert, boolean empty) {
            super.updateItem(concert, empty);

            if (empty || concert == null) {
                setGraphic(null);
                setText(null);
            } else {
                ConcertCard concertCard = new ConcertCard(concert, getIndex() + 1, showFullDetails);
                setGraphic(concertCard.getRoot());
            }
        }
    }
}
