package tahub.contacts.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.logic.Logic;
import tahub.contacts.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private Logic logic;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, Logic logic) {
        super(FXML);
        this.logic = logic;
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
                setGraphic(null);
                setText(null);
                personCard = null;
            } else {
                if (personCard == null || !personCard.person.equals(person)) {
                    personCard = new PersonCard(logic, person, getIndex() + 1);
                } else {
                    // Reuse existing card but refresh content
                    personCard.refresh(getIndex() + 1);
                }
                setGraphic(personCard.getRoot());
            }
        }
    }

    /**
     * Refreshes the person view to reflect any changes in person details or tags
     */
    public void refreshPersonView() {
        Platform.runLater(() -> {
            // Force a complete refresh of the list view
            ObservableList<Person> items = personListView.getItems();
            personListView.setItems(null);
            personListView.setItems(items);
            personListView.refresh();
        });
    }
}
