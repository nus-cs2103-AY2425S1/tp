package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
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

    private MainWindow mainWindow;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Add event handler
        personListView.setOnMouseClicked(this::handleListViewClick);
    }


    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        // Logging here to verify the setter works
        logger.info("MainWindow reference: " + this.mainWindow);
    }
    /**
     * When user clicks on a person, the details plane changes.
     */
    private void handleListViewClick(MouseEvent event) {
        // Get the index of the clicked item
        int index = personListView.getSelectionModel().getSelectedIndex();
        Person selectedItem = personListView.getSelectionModel().getSelectedItem();

        if (index != -1) {
            mainWindow.setPersonDetails(selectedItem);
            logger.info("Clicked on person: " + selectedItem + " at index " + index);
        } else {
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
