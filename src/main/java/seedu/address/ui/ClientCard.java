package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label clientType;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;
        id.setText(displayedIndex + ". ");
        name.setText(client.getName().fullName);
        clientType.setText("[" + client.getTypeString() + "]");
        phone.setText(client.getPhone().value);
        email.setText(client.getEmail().value);
    }

    public HBox getCardPane() {
        return this.cardPane;
    }
}
