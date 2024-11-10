package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final boolean isVisualsEnabled;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, MainWindow mainWindow, boolean isVisualsEnabled) {
        super(FXML);
        this.isVisualsEnabled = isVisualsEnabled;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell(mainWindow, isVisualsEnabled));

        // Listen for ENTER key event on the list view
        personListView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    new PersonCard(selectedPerson, personListView.getSelectionModel().getSelectedIndex() + 1,
                            mainWindow, isVisualsEnabled).handleOnClick();
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private final MainWindow mainWindow;
        private final boolean isVisualsEnabled;

        public PersonListViewCell(MainWindow mainWindow, boolean isVisualsEnabled) {
            this.mainWindow = mainWindow;
            this.isVisualsEnabled = isVisualsEnabled;
        }
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, mainWindow, isVisualsEnabled).getRoot());
            }
        }
    }

}
