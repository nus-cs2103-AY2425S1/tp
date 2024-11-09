package seedu.address.ui.meetup;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.AddedBuyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of buyers.
 */
public class MeetUpListPanel extends UiPart<Region> {
    private static final String FXML = "MeetUpListPanel.fxml";
    private final Map<MeetUp, Boolean> hasOverlapMap;
    private final ObservableList<Buyer> buyerList;


    @FXML
    private ListView<MeetUp> meetUpListView;

    /**
     * Creates a {@code MeetUpListPanel} with the given {@code ObservableList}.
     */
    public MeetUpListPanel(ObservableList<MeetUp> meetUpList, ObservableList<MeetUp> originalMeetUpList,
                           ObservableList<Buyer> buyerList) {
        super(FXML);
        requireNonNull(originalMeetUpList);
        requireNonNull(meetUpList);
        requireNonNull(buyerList);

        Map<MeetUp, Boolean> hasOverlapMap = new HashMap<>();
        for (int i = 0; i < meetUpList.size(); i++) {
            boolean foundOverlap = false;
            MeetUp meetUp = meetUpList.get(i);
            for (MeetUp meetUp2 : originalMeetUpList) {
                if (!meetUp.isSameMeetUp(meetUp2) && doDateRangesOverlap(meetUp, meetUp2)) {
                    foundOverlap = true;
                    break;
                }
            }
            hasOverlapMap.put(meetUp, foundOverlap);
        }

        this.hasOverlapMap = hasOverlapMap;
        this.buyerList = buyerList;
        meetUpListView.setItems(meetUpList);
        meetUpListView.setCellFactory(x -> new MeetUpListViewCell());
    }

    /**
     * Checks for overlaps between two meetups
     */
    private boolean doDateRangesOverlap(MeetUp meetUp1, MeetUp meetUp2) {
        requireNonNull(meetUp1);
        requireNonNull(meetUp2);
        LocalDateTime start1 = meetUp1.getFrom().getDateTime();
        LocalDateTime end1 = meetUp1.getTo().getDateTime();
        LocalDateTime start2 = meetUp2.getFrom().getDateTime();
        LocalDateTime end2 = meetUp2.getTo().getDateTime();

        // Check if the date ranges overlap
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Buyer} using a {@code BuyerCard}.
     */
    private class MeetUpListViewCell extends ListCell<MeetUp> {

        @Override
        protected void updateItem(MeetUp meetUp, boolean empty) {
            super.updateItem(meetUp, empty);

            if (empty || meetUp == null) {
                setGraphic(null);
                setText(null);
            } else {
                Boolean doesTimeOverlap = hasOverlapMap.getOrDefault(meetUp, false);

                // Create styled labels for each buyer here
                List<Label> styledBuyerLabels = meetUp.getAddedBuyers().stream()
                        .sorted(Comparator.comparing(buyer -> buyer.fullName))
                        .map(addedBuyer -> createStyledBuyerLabel(addedBuyer))
                        .toList();

                setGraphic(new MeetUpCard(meetUp, getIndex() + 1, doesTimeOverlap, styledBuyerLabels)
                        .getRoot());
            }
        }

        private Label createStyledBuyerLabel(AddedBuyer addedBuyer) {
            boolean doesItContainName = buyerList.stream()
                    .anyMatch(buyer -> buyer.getName().equals(addedBuyer));

            Label label = new Label(addedBuyer.fullName);
            if (!doesItContainName) {
                label.setStyle("-fx-background-color: red;");
            }
            return label;
        }
    }
}
