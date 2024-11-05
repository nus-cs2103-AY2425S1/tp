package seedu.address.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Role;

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
        final String nicknamePrelabel = "aka ";
        final String telegramPrelabel = "@";
        id.setText(displayedIndex + ". ");

        final int width = 580; // try not to change this
        name.setText(contact.getName().fullName);
        setSize(name, width-18);

        telegramHandle.setText(telegramPrelabel + contact.getTelegramHandle().value);
        studentStatus.setText(contact.getStudentStatus().value);
        email.setText(contact.getEmail().value);
        List.<Label>of(telegramHandle, email, studentStatus).forEach(label -> label.setMaxWidth(width));

        contact.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.getRoleIndex()))
                .forEach(role -> roles.getChildren().add(getRoleLabel(role)));
        String nicknameObtained = contact.getNickname().value;
        if (!nicknameObtained.isEmpty()) {
            Label nicknameLabel = new Label(nicknamePrelabel + nicknameObtained);
            setSize(nicknameLabel, width);
            nicknameLabel.setWrapText(true);
            nickname.getChildren().add(nicknameLabel);
        }
    }

    private void setSize(Label label, int size) {
        label.setMaxWidth(size);
        label.setMinWidth(size);
        label.setPrefWidth(size);
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
        case Role.PRESIDENT -> "president";
        case Role.VICE_PRESIDENT -> "vice-president";
        case Role.ADMIN -> "admin";
        case Role.MARKETING -> "marketing";
        case Role.EVENTS_INTERNAL -> "events-internal";
        case Role.EVENTS_EXTERNAL -> "events-external";
        case Role.EXTERNAL_RELATIONS -> "external-relations";
        default -> null;
        };

        label.setId(id);
        return label;
    }
}
