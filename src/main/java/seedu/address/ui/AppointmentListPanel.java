package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.OwnedAppointment;

/**
 * Panel containing the list of appointment
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";

    @FXML
    private ListView<OwnedAppointment> appointmentListView;

    /**
     * Creates a {@code NoteListPanel} with the given {@code ObservableList}.
     */
    public AppointmentListPanel(ObservableList<OwnedAppointment> appointmentList) {
        super(FXML);
        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code OwnedAppointment} using a {@code AppointmentCard}.
     */
    static class AppointmentListViewCell extends ListCell<OwnedAppointment> {
        @Override
        protected void updateItem(OwnedAppointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointment).getRoot());
            }
        }
    }
}
