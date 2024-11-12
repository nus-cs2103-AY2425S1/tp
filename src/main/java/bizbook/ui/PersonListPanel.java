package bizbook.ui;

import java.util.logging.Logger;

import bizbook.commons.core.LogsCenter;
import bizbook.model.person.Person;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

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
    public PersonListPanel(ObservableList<Person> personList, ObjectProperty<Person> focusedPerson) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Add event handler
        personListView.setOnMouseClicked(event -> handleListViewClick(event, focusedPerson));
    }

    /**
     * When user clicks on a person, the details plane changes.
     */
    private void handleListViewClick(MouseEvent event, ObjectProperty<Person> focusedPerson) {
        // Get the index of the clicked item
        int index = personListView.getSelectionModel().getSelectedIndex();
        Person selectedPerson = personListView.getSelectionModel().getSelectedItem();

        if (index != -1) {
            focusedPerson.set(selectedPerson);
            logger.info("Clicked on person: " + selectedPerson + " at index " + index);
        } else {
            // Known issue: once a person is selected, clicking on the empty area would not be triggered
            logger.info("Clicked on an empty area of the ListView");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
