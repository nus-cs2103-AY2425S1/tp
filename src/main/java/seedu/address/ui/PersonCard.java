package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Role;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

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
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        telegramHandle.setText(person.getTelegramHandle().value);
        studentStatus.setText(person.getStudentStatus().value);
        email.setText(person.getEmail().value);
        person.getRoles().stream()
                .sorted(Comparator.comparing(tag -> tag.roleName))
                .forEach(role -> roles.getChildren().add(getRoleLabel(role)));

        String nicknameObtained = person.getNickname().value;
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
