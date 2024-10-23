package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.owner.Owner;

/**
 * An UI component that displays information of a {@code Owner}.
 */
public class OwnerCard extends UiPart<Region> {

    private static final String FXML = "OwnerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on PawPatrol level 4</a>
     */

    public final Owner owner;

    @FXML
    private HBox cardPane;
    @FXML
    private Label icNumber;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;

    /**
     * Creates a {@code OwnerCode} with the given {@code Owner} and index to display.
     */
    public OwnerCard(Owner owner, int displayedIndex) {
        super(FXML);
        this.owner = owner;
        id.setText(displayedIndex + ". ");
        icNumber.setText(owner.getIdentificationNumber().getRedacted());
        name.setText(owner.getName().fullName);
        phone.setText(owner.getPhone().value);
        address.setText(owner.getAddress().value);
        email.setText(owner.getEmail().value);
    }
}
