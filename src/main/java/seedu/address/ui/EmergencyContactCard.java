package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.EmergencyContact;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EmergencyContactCard extends UiPart<Region> {

    private static final String FXML = "EmergencyContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final EmergencyContact emergencyContact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label emergencyContactName;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label emergencyContactPhone;
    @FXML
    private Label emergencyContactRelationship;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public EmergencyContactCard(EmergencyContact emergencyContact, int displayedIndex) {
        super(FXML);
        this.emergencyContact = emergencyContact;
        id.setText(displayedIndex + ". ");
        emergencyContactName.setText(emergencyContact.getName().fullName);
        emergencyContactPhone.setText(emergencyContact.getPhone().value);
        emergencyContactRelationship.setText(emergencyContact.getRelationship().relationship);
    }
}
