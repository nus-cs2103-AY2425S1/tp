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
        concertListView.setCellFactory(listView -> new ConcertListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Concert} using a {@code ConcertCard}.
     */
    class ConcertListViewCell extends ListCell<Concert> {
        @Override
        protected void updateItem(Concert concert, boolean empty) {
            super.updateItem(concert, empty);

            if (empty || concert == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConcertCard(concert, getIndex() + 1).getRoot());
            }
        }
    }

}
