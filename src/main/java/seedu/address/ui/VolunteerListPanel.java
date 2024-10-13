package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.volunteer.Volunteer;

import java.util.logging.Logger;

/**
 * Panel containing the list of volunteers.
 */
public class VolunteerListPanel extends UiPart<Region> {
    private static final String FXML = "VolunteerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(VolunteerListPanel.class);

    @FXML
    private ListView<Volunteer> volunteerListView;

    /**
     * Creates a {@code VolunteerListPanel} with the given {@code ObservableList}.
     */
    public VolunteerListPanel(ObservableList<Volunteer> volunteerList) {
        super(FXML);
        volunteerListView.setItems(volunteerList);
        volunteerListView.setCellFactory(listView -> new VolunteerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Volunteer} using a {@code VolunteerCard}.
     */
    class VolunteerListViewCell extends ListCell<Volunteer> {
        @Override
        protected void updateItem(Volunteer volunteer, boolean empty) {
            super.updateItem(volunteer, empty);

            if (empty || volunteer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VolunteerCard(volunteer, getIndex() + 1).getRoot());
            }
        }
    }

}
