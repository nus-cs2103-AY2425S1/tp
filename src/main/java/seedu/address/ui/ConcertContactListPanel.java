package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.concert.ConcertContact;

/**
 * Panel containing the list of Concert Contacts.
 */
public class ConcertContactListPanel extends UiPart<Region> {
    private static final String FXML = "ConcertContactListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ConcertListPanel.class);

    @FXML
    private ListView<ConcertContact> concertContactListView;

    /**
     * Creates a {@code ConcertContactListPanel} with the given {@code ObservableList}.
     */
    public ConcertContactListPanel(ObservableList<ConcertContact> concertContactList) {
        super(FXML);
        concertContactListView.setItems(concertContactList);
        concertContactListView.setCellFactory(listView -> new ConcertContactListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ConcertContact} using a
     * {@code ConcertContactCard}.
     */
    class ConcertContactListViewCell extends ListCell<ConcertContact> {
        @Override
        protected void updateItem(ConcertContact concertContact, boolean empty) {
            super.updateItem(concertContact, empty);

            if (empty || concertContact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConcertContactCard(concertContact, getIndex() + 1).getRoot());
            }
        }
    }

}
