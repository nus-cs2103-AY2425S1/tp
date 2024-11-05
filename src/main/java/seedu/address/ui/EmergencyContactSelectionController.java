package seedu.address.ui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Person;

public class EmergencyContactSelectionController {
    private final List<ListView<EmergencyContact>> emergencyContactListViews = new ArrayList<>();
    public void addEmergencyContactListView(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListViews.add(emergencyContactListView);
        setupEmergencyContactSelectionListener(emergencyContactListView);
    }

    public void removeEmergencyContactListView(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListViews.remove(emergencyContactListView);
    }

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
