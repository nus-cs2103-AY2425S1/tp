package tuteez.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tuteez.commons.core.LogsCenter;
import tuteez.model.person.Person;

/**
 * Panel containing the current person on display.
 */
public class DisplayCardPanel extends UiPart<Region> {
    private static final String FXML = "DisplayCardPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayCardPanel.class);

    @FXML
    private ListView<Person> displayCardListView;

    private ObjectProperty<Optional<Person>> lastViewedPerson;

    /**
     * Creates a {@code DisplayCardPanel} that listens to the lastViewedPerson property.
     */
    public DisplayCardPanel(ObjectProperty<Optional<Person>> lastViewedPerson) {
        super(FXML);
        this.lastViewedPerson = lastViewedPerson;

        // Disable selection, cannot select cell
        displayCardListView.setMouseTransparent(true);
        displayCardListView.setFocusTraversable(false);

        // Initialize with empty list
        displayCardListView.setItems(FXCollections.observableArrayList());

        // Listen for changes to lastViewedPerson
        lastViewedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Last viewed person changed to: " + newValue);
            updateDisplayCard(newValue);
        });

        displayCardListView.setCellFactory(listView -> new DisplayCardListViewCell());

        updateDisplayCard(lastViewedPerson.get());
    }

    /**
     * Updates the display card with the new person information.
     *
     * @param personToDisplay The person whose details will be displayed.
     */
    private void updateDisplayCard(Optional<Person> personToDisplay) {
        displayCardListView.getItems().clear();
        personToDisplay.ifPresent(displayCardListView.getItems()::add);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code DisplayCard}.
     */
    class DisplayCardListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(Optional.of(person)).getRoot());
            }
        }
    }
}
