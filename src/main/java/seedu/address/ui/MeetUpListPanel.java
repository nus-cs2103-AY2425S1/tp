package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meetup.MeetUp;

/**
 * Panel containing the list of persons.
 */
public class MeetUpListPanel extends UiPart<Region> {
    private static final String FXML = "MeetUpListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<MeetUp> meetUpListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public MeetUpListPanel(ObservableList<MeetUp> meetUpList) {
        super(FXML);
        meetUpListView.setItems(meetUpList);
        meetUpListView.setCellFactory(listView -> new MeetUpListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MeetUpListViewCell extends ListCell<MeetUp> {
        @Override
        protected void updateItem(MeetUp meetUp, boolean empty) {
            super.updateItem(meetUp, empty);

            if (empty || meetUp == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MeetUpCard(meetUp, getIndex() + 1).getRoot());
            }
        }
    }

}
