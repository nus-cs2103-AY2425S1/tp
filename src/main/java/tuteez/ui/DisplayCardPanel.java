package tuteez.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
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
    private VBox displayCardContainer;

    private ObjectProperty<Optional<Person>> lastViewedPerson;

    /**
     * Creates a {@code DisplayCardPanel} that listens to the lastViewedPerson property.
     */
    public DisplayCardPanel(ObjectProperty<Optional<Person>> lastViewedPerson) {
        super(FXML);
        this.lastViewedPerson = lastViewedPerson;
        lastViewedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Last viewed person changed to: " + newValue);
            updateDisplayCard(newValue);
        });
        updateDisplayCard(lastViewedPerson.get());
    }

    /**
     * Updates the display card with the new person information.
     *
     * @param personToDisplay The person whose details will be displayed.
     */
    private void updateDisplayCard(Optional<Person> personToDisplay) {
        displayCardContainer.getChildren().clear(); // Clear previous content
        personToDisplay.ifPresent(person -> {
            DisplayCard displayCard = new DisplayCard(Optional.of(person));
            displayCardContainer.getChildren().add(displayCard.getRoot()); // Add display card to container
        });
    }
}
