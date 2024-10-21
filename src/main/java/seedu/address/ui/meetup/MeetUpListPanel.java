package seedu.address.ui.meetup;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meetup.MeetUp;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of buyers.
 */
public class MeetUpListPanel extends UiPart<Region> {
    private static final String FXML = "MeetUpListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetUpListPanel.class);

    @FXML
    private ListView<MeetUp> meetUpListView;

    /**
     * Creates a {@code BuyerListPanel} with the given {@code ObservableList}.
     */
    public MeetUpListPanel(ObservableList<MeetUp> meetUpList) {
        super(FXML);
        logger.info(meetUpList.toString());
        meetUpListView.setItems(meetUpList);
        meetUpListView.setCellFactory(listView -> new MeetUpListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Buyer} using a {@code BuyerCard}.
     */
    class MeetUpListViewCell extends ListCell<MeetUp> {
        @Override
        protected void updateItem(MeetUp meetUp, boolean empty) {
            super.updateItem(meetUp, empty);

            if (empty || meetUp == null) {
                logger.info("graphic or text is null");
                setGraphic(null);
                setText(null);
            } else {
                logger.info("making new card");
                setGraphic(new MeetUpCard(meetUp, getIndex() + 1).getRoot());
            }
        }
    }

}
