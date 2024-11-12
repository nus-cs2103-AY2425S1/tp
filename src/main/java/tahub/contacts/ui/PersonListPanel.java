package tahub.contacts.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
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

    private ListChangeListener<Person> personListChangeListener;
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
        setupPersonListChangeListener(personList);
    }

    /**
     * Sets up a listener to monitor changes in the person list.
     */
    private void setupPersonListChangeListener(ObservableList<Person> personList) {
        personListChangeListener = change -> {
            while (change.next()) {
                if (change.wasRemoved() && change.getRemovedSize() == personList.size()) {
                    // This indicates a clear operation
                    Platform.runLater(this::cleanupAllWindows);
                }
            }
        };
        personList.addListener(personListChangeListener);
    }

    /**
     * Cleans up all attendance windows when the list is cleared
     */
    private void cleanupAllWindows() {
        // Get all cells and cleanup their attendance windows
        for (int i = 0; i < personListView.getItems().size(); i++) {
            PersonListViewCell cell = (PersonListViewCell) personListView.lookup("#" + i);
            if (cell != null && cell.getPersonCard() != null) {
                cell.getPersonCard().cleanup();
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private PersonCard personCard;

        public PersonListViewCell() {
            setId(String.valueOf(getIndex()));
        }
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                if (personCard != null) {
                    personCard.cleanup();
                }
                setGraphic(null);
                setText(null);
                personCard = null;
            } else {
                if (personCard == null || !personCard.person.equals(person)) {
                    if (personCard != null) {
                        personCard.cleanup();
                    }
                    personCard = new PersonCard(logic, person, getIndex() + 1);
                } else {
                    // Reuse existing card but refresh content
                    personCard.refresh(getIndex() + 1);
                }
                setGraphic(personCard.getRoot());
            }
        }

        public PersonCard getPersonCard() {
            return personCard;
        }

        @Override
        public void updateIndex(int index) {
            super.updateIndex(index);
            setId(String.valueOf(index));
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

    /**
     * Cleanup method to remove listeners and resources when the panel is being disposed
     */
    public void cleanup() {
        if (personListView.getItems() != null && personListChangeListener != null) {
            personListView.getItems().removeListener(personListChangeListener);
        }
        cleanupAllWindows();
    }
}
