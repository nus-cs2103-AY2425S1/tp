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
    private final Map<ListView<EmergencyContact>, ListCell<Person>> viewCellMap = new HashMap<>();
    private final Map<ListCell<Person>, ListView<EmergencyContact>> cellViewMap = new HashMap<>();
    private List<ListCell<Person>> personListCells = new ArrayList<>();

    private ListView<Person> personListView;

    public EmergencyContactSelectionController(ListView<Person> personListView) {
        personListView = personListView;
    }

    public void addPerson(ListCell<Person> personListCell, ListView<EmergencyContact> emergencyContactListView) {
        addEmergencyContactListView(emergencyContactListView);
        addPersonListCell(personListCell);
        viewCellMap.put(emergencyContactListView, personListCell);
        cellViewMap.put(personListCell, emergencyContactListView);
    }

    public void removePerson(ListView<EmergencyContact> emergencyContactListView) {
        ListCell<Person> personListCellToDelete = viewCellMap.get(emergencyContactListView);
        removePersonListCell(personListCellToDelete);
        removeEmergencyContactListView(emergencyContactListView);
        viewCellMap.remove(emergencyContactListView);
        cellViewMap.remove(personListCellToDelete);
    }

    private void addEmergencyContactListView(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListViews.add(emergencyContactListView);
        setupEmergencyContactSelectionListener(emergencyContactListView);
    }

    private void removeEmergencyContactListView(ListView<EmergencyContact> emergencyContactListView) {
        emergencyContactListViews.remove(emergencyContactListView);
    }

    private void addPersonListCell(ListCell<Person> personListCell) {
        personListCells.add(personListCell);
    }

    private void removePersonListCell(ListCell<Person> personListCell) {
        personListCells.remove(personListCell);
    }

    public void onListCellSelected(ListCell<Person> selectedCell) {
        clearOtherSelections(selectedCell);
    }

    private void clearOtherSelections(ListCell<Person> selectedCell) {
        ListView<EmergencyContact> targetEmergencyContactListView = cellViewMap.get(selectedCell);
        for (ListView<EmergencyContact> emergencyContactListView : emergencyContactListViews) {
            if (emergencyContactListView != targetEmergencyContactListView) {
                emergencyContactListView.getSelectionModel().clearSelection();
            }
        }
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
//                ListCell<Person> personListCell = viewCellMap.get(emergencyContactListView);
////                personListView.getSelectionModel().select(personListCell.getIndex());
//                personListCell.updateSelected(true);
////                personListCell.getListView().getSelectionModel().select(personListCell.getIndex());
//                for (ListCell<Person> otherPersonListCell : personListCells) {
//                    otherPersonListCell.updateSelected(false);
////                    otherPersonListCell.getListView().getSelectionModel().clearSelection(
////                            otherPersonListCell.getIndex());
//                }
            }
        });
    }
}
