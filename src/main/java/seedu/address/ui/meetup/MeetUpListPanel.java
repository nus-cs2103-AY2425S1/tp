package seedu.address.ui.meetup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.meetup.MeetUp;
import seedu.address.ui.UiPart;

import java.util.List;

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
    public MeetUpListPanel(ObservableList<MeetUp> meetUpList, ObservableList<MeetUp> originalList) {
        super(FXML);
        Map<MeetUp, Boolean> hasOverlapMap = new HashMap<>();

        for (int i = 0; i < meetUpList.size(); i++) {
            boolean foundOverlap = false;
            MeetUp meetUp = meetUpList.get(i);
            for (MeetUp meetUp2 : originalList) {
                if (!meetUp.isSameMeetUp(meetUp2) && doDateRangesOverlap(meetUp, meetUp2)) {
                    foundOverlap = true;
                    break;
                }
            }
            hasOverlapMap.put(meetUp, foundOverlap);
        }
        meetUpListView.setItems(meetUpList);
        meetUpListView.setCellFactory(x -> new MeetUpListViewCell(hasOverlapMap));
    }

    /**
     * Checks for overlaps beteen two meetups
     */
    public boolean doDateRangesOverlap(MeetUp meetUp1, MeetUp meetUp2) {
        LocalDateTime start1 = meetUp1.getFrom().getDateTime();
        LocalDateTime end1 = meetUp1.getTo().getDateTime();
        LocalDateTime start2 = meetUp2.getFrom().getDateTime();
        LocalDateTime end2 = meetUp2.getTo().getDateTime();

        // Check if the date ranges overlap
        return (start1.isBefore(end2) || start1.isEqual(end2))
                && (end1.isAfter(start2) || end1.isEqual(start2));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Buyer} using a {@code BuyerCard}.
     */
    class MeetUpListViewCell extends ListCell<MeetUp> {
        private Map<MeetUp, Boolean> hasOverlapMap;

        public MeetUpListViewCell(Map<MeetUp, Boolean> hasOverlapMap) {
            this.hasOverlapMap = hasOverlapMap;
        }

        @Override
        protected void updateItem(MeetUp meetUp, boolean empty) {
            super.updateItem(meetUp, empty);

            if (empty || meetUp == null) {
                setGraphic(null);
                setText(null);
            } else {
                Boolean hasOverlapForThisItem = hasOverlapMap.getOrDefault(meetUp, false);
                setGraphic(new MeetUpCard(meetUp, getIndex() + 1, hasOverlapForThisItem).getRoot());
            }
        }
    }
}
