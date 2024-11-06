package seedu.address.ui;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import seedu.address.model.person.EmergencyContact;

/**
 * Manages selection behavior across multiple {@code ListView<EmergencyContact>} instances
 * to ensure only one {@code EmergencyContact} is selected at a time.
 */
public class EmergencyContactSelectionController {
    private final List<ListView<EmergencyContact>> emergencyContactListViews = new ArrayList<>();

    /**
     * Adds a {@code ListView<EmergencyContact>} to the controller and sets up
     * its selection listener to manage exclusive selection across all registered
     * {@code ListView<EmergencyContact>} instances.
     *
     * @param emergencyContactListView The {@code ListView<EmergencyContact>} to add.
     */
    public void addEmergencyContactListView(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListViews.add(emergencyContactListView);
        setupEmergencyContactSelectionListener(emergencyContactListView);
    }

    /**
     * Removes a {@code ListView<EmergencyContact>} from the controller
     *
     * @param emergencyContactListView The {@code ListView<EmergencyContact>} to remove.
     */
    public void removeEmergencyContactListView(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListViews.remove(emergencyContactListView);
    }

    /**
     * Sets up a selection listener on a given {@code ListView<EmergencyContact>} to clear
     * selections in other {@code ListView<EmergencyContact>} instances when an item
     * is selected in this list view.
     *
     * @param emergencyContactListView The {@code ListView<EmergencyContact>} to set up the listener for.
     */
    private void setupEmergencyContactSelectionListener(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        emergencyContactListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Clear selection in other ListViews
                for (ListView<EmergencyContact> otherEmergencyContactListView : emergencyContactListViews) {
                    if (otherEmergencyContactListView != emergencyContactListView) {
                        otherEmergencyContactListView.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }
}
