package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final EmergencyContactSelectionController emergencyContactSelectionController;
    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        emergencyContactSelectionController = new EmergencyContactSelectionController();
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private PersonCard personCard;
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                if (personCard != null) {
                    emergencyContactSelectionController.removeEmergencyContactListView(
                            personCard.getEmergencyContactListView());
                }
                setGraphic(null);
                setText(null);
                personCard = null;
            } else {
                personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());
                emergencyContactSelectionController.addEmergencyContactListView(
                        personCard.getEmergencyContactListView());
            }
        }
    }

}
