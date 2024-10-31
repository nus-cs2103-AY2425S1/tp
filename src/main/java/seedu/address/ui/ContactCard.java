package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.Role;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label telegramHandle;
    @FXML
    private Label studentStatus;
    @FXML
    private Label email;
    @FXML
    private FlowPane roles;
    @FXML
    private FlowPane nickname;

    /**
     * Creates a {@code ContactCard} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        telegramHandle.setText(contact.getTelegramHandle().value);
        studentStatus.setText(contact.getStudentStatus().value);
        email.setText(contact.getEmail().value);
        contact.getRoles().stream()
                .sorted(Comparator.comparing(tag -> tag.roleName))
                .forEach(role -> roles.getChildren().add(getRoleLabel(role)));

        String nicknameObtained = contact.getNickname().value;
        if (!nicknameObtained.isEmpty()) {
            nickname.getChildren().add(new Label(nicknameObtained));
        }
    }

    /**
     * Gets role label with id that corresponds to its role name.
     *
     * @param role Role object.
     * @return Label of the role.
     */
    private Label getRoleLabel(Role role) {
        assert Role.isValidRoleName(role.roleName);

        Label label = new Label(role.roleName);
        String id = switch (role.roleName) {
        case "President" -> "president";
        case "Vice President" -> "vice-president";
        case "Admin" -> "admin";
        case "Marketing" -> "marketing";
        case "Events (internal)" -> "events-internal";
        case "Events (external)" -> "events-external";
        case "External Relations" -> "external-relations";
        default -> null;
        };

        label.setId(id);
        return label;
    }
}
