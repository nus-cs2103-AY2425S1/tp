package seedu.address.ui;


import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
/**
 * An UI component that displays details of a {@code Person}.
 *
 */
public class PersonDetails extends UiPart<Region> {

    private static final String FXML = "PersonDetailsCard.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetails.class);

    @FXML
    private VBox personNotes;

    /**
     * Creates a {@code PersonDetails} instance.
     */
    public PersonDetails() {
        super(FXML);
    }

    /**
     * Updates detail plane with person info.
     */
    public void updatePersonDetails(Person person) {
        // Clear existing labels
        personNotes.getChildren().clear();

        // Update with new person details
        if (person != null) {
            logger.info(person.toString());
            Label notes = new Label("Notes:");
            notes.setStyle("-fx-text-fill: white;");
            personNotes.getChildren().add(notes);
            // Use name as placeholder (update after Notes backend is done)
            Label nameLabel = new Label(person.getName().toString());
            nameLabel.setStyle("-fx-text-fill: white;");
            personNotes.getChildren().add(nameLabel);
        }
    }


}
