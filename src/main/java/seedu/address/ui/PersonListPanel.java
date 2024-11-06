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

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell(false));
    }

    /**
     * Sets the list to show full details of its {@code Person} cards.
     */
    public void showFullPerson() {
        personListView.setCellFactory(listView -> new PersonListPanel.PersonListViewCell(true));
        personListView.refresh();
    }

    /**
     * Sets the list to hide full details of its {@code Person} cards.
     */
    public void hideFullPerson() {
        personListView.setCellFactory(listView -> new PersonListPanel.PersonListViewCell(false));
        personListView.refresh();
    }
    
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        private final boolean showFullDetails;

        /**
         * Creates a {@code PersonListViewCell} with {@param showFullDetails} to indicate whether the details
         * of the {@code PersonCard} should be truncated or wrapped to a new line.
         *
         * @param showFullDetails
         */
        protected PersonListViewCell(boolean showFullDetails) {
            this.showFullDetails = showFullDetails;
        }

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, showFullDetails).getRoot());
            }
        }
    }

}
