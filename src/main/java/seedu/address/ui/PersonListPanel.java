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
    private final String currentTheme;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, String currentTheme) {
        super(FXML);
        this.currentTheme = currentTheme;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> {
            PersonListViewCell cell = new PersonListViewCell();
            personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                cell.handleSelectionChange(newValue != null);
            });

            return cell;
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private boolean isSelectionChange = false;

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            // If it's a selection change, ignore updates
            if (isSelectionChange) {
                return;
            }

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, currentTheme).getRoot());
            }
        }

        public void handleSelectionChange(boolean selected) {
            isSelectionChange = selected;
        }
    }

}
