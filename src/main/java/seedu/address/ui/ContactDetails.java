package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for the contact details panel.
 */
public class ContactDetails extends UiPart<Region> {

    private static final String FXML = "ContactDetails.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetails.class);

    private Person person;

    @FXML
    private HBox contactDetailPlane;

    @FXML
    private VBox notesList;

    /**
     * Creates a {@code ContactDetailPannel} with the given {@code Person} information.
     */
    public ContactDetails(Person person) {
        super(FXML);
    }

    /**
     * Sets the person object as the contact to be displayed on the panel.
     *
     * @param person The person object to be updated onto the panel.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Updates the contact details panel with person info.
     */
    public void updatePanel() {
        this.clearPanel();
        this.setPanelInformation();
    }

    /**
     * Clears any previous contact details from the panel.
     */
    private void clearPanel() {
        // Clear existing labels
        notesList.getChildren().clear();
    }

    /**
     * Adds the contact details of the person into the panel.
     */
    private void setPanelInformation() {
        // Update with new person details
        if (person != null) {
            logger.info(person.toString());
            Label notes = new Label("Notes:");
            notes.setId("notes-label");
            notesList.getChildren().add(notes);

            // Use name as placeholder (update after Notes backend is done)
            Label nameLabel = new Label(person.getName().toString());
            nameLabel.setId("notes-label");
            notesList.getChildren().add(nameLabel);
        }
    }

}
