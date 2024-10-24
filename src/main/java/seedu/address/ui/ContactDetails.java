package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Controller for the contact details panel.
 */
public class ContactDetails extends UiPart<Region> {

    private static final String FXML = "ContactDetails.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetails.class);

    @FXML
    private HBox contactDetailsPanel;

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

    private void addNote(String note) {
        Label label = new Label("• " + note);
        // TODO: use classes instead
        label.setId("notes-label");
        notesList.getChildren().add(label);
    }

    /**
     * Sets the person object as the contact to be displayed on the panel.
     *
     * @param person The person object to be updated onto the panel.
    */
    private void displayPerson(Person person) {
        logger.info("Displaying info of " + person.toString());
        clearPanel();

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
