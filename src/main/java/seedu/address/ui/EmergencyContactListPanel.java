package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.EmergencyContact;

/**
 * Panel containing the list of emergency contacts.
 */
public class EmergencyContactListPanel extends UiPart<Region> {
    private static final String FXML = "EmergencyContactListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EmergencyContactListPanel.class);

    @FXML
    private ListView<EmergencyContact> emergencyContactListView;

    /**
     * Creates a {@code EmergencyContactListPanel} with the given {@code ObservableList}.
     */
    public EmergencyContactListPanel(ObservableList<EmergencyContact> emergencyContacts) {
        super(FXML);
        emergencyContactListView.setItems(emergencyContacts);
        emergencyContactListView.setCellFactory(listView -> new EmergencyContactListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EmergencyContact}
     * using a {@code EmergencyContactCard}.
     */
    class EmergencyContactListViewCell extends ListCell<EmergencyContact> {
        @Override
        protected void updateItem(EmergencyContact emergencyContact, boolean empty) {
            super.updateItem(emergencyContact, empty);

            if (empty || emergencyContact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmergencyContactCard(emergencyContact, getIndex() + 1).getRoot());
            }
        }
    }
}
