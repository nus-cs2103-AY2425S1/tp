package bizbook.ui;

import java.util.logging.Logger;

import bizbook.commons.core.LogsCenter;
import bizbook.model.person.Note;
import bizbook.model.person.Person;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Controller for the contact details panel.
 */
public class ContactDetails extends UiPart<Region> {

    private static final String FXML = "ContactDetails.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetails.class);

    @FXML
    private ScrollPane contactDetailsPanel;

    @FXML
    private Label name;

    @FXML
    private Label phoneNo;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label notes;

    @FXML
    private VBox notesList;

    /**
     * Creates a {@code ContactDetailsPanel} with the given {@code Person} information.
     */
    public ContactDetails(ObjectProperty<Person> person) {
        super(FXML);
        person.addListener((observable, oldValue, newValue) -> displayPerson(newValue));
    }

    /**
     * Sets the person object as the contact to be displayed on the panel.
     *
     * @param person The person object to be updated onto the panel.
    */
    private void displayPerson(Person person) {
        clearPanel();

        if (person == null || person.equals(null)) {
            return;
        }

        logger.info("Displaying info of " + person.toString());

        name.setText(person.getName().fullName);
        phoneNo.setText("Mobile: " + person.getPhone().toString());
        email.setText("Email: " + person.getEmail().toString());
        address.setText("Address: " + person.getAddress().toString());

        if (!person.getNotes().isEmpty()) {
            Label notesHeader = new Label("Notes");
            notesHeader.setId("notes-header");
            notesList.getChildren().add(notesHeader);
        }

        int index = 1;
        for (Note note : person.getNotes()) {
            Label label = new Label(index + ". " + note.getNote());
            label.setId("notes-label");
            notesList.getChildren().add(label);

            index++;
        }
    }

    /**
     * Clears any previous contact details from the panel.
     */
    private void clearPanel() {
        // Clear existing labels
        name.setText("");
        phoneNo.setText("");
        email.setText("");
        address.setText("");
        notes.setText("");
        notesList.getChildren().clear();
    }
}
