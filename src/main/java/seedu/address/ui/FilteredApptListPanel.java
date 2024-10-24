package seedu.address.ui;

import java.util.TreeSet;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.filteredappointment.FilteredAppointment;


/**
 * Panel containing the list of filtered appointments.
 */
public class FilteredApptListPanel extends UiPart<Region> {
    private static final String FXML = "FilteredApptListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FilteredApptListPanel.class);

    @FXML
    private ListView<FilteredAppointment> apptListView;

    /**
     * Creates a {@Code FilteredApptListPanel} with the given {@code TreeSet<FilteredAppointment>}
     * @param filteredAppointments
     */
    public FilteredApptListPanel(TreeSet<FilteredAppointment> filteredAppointments) {
        super(FXML);

        apptListView.setItems(FXCollections.observableArrayList(filteredAppointments));
        apptListView.setCellFactory(listView -> new FilteredApptListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code FilteredAppointment} uisng a {@code ApptCard}
     */
    class FilteredApptListViewCell extends ListCell<FilteredAppointment> {
        @Override
        protected void updateItem(FilteredAppointment filteredAppointment, boolean empty) {
            super.updateItem(filteredAppointment, empty);

            if (empty || filteredAppointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FilteredApptCard(filteredAppointment, getIndex() + 1).getRoot());
            }
        }
    }
}
