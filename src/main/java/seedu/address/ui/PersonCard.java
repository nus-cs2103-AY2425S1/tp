package seedu.address.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> implements Observer {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    private Map<String, String> roleColors = new HashMap<>();

    @FXML
    private HBox cardPane;
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
    @FXML
    private Label telegramUsername;
    @FXML
    private FlowPane roles;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        setTextForTelegramUsername(person);

        roleColors.put("attendee", "#159b07");
        roleColors.put("sponsor", "#12b6f3");
        roleColors.put("vendor", "#f87f26");
        roleColors.put("volunteer", "#d262f3");

        if (person.getRoles().isEmpty()) {
            addDefaultNoRoleLabel();
        } else {
            person.getRoles().stream()
                    .sorted(Comparator.comparing(Role::getRoleName))
                    .forEach(this::addLabel);
        }
    }

    /**
     * Adds a label representing the specified role to the roles container.
     *
     * This method creates a new {@link Label} with the role name, sets the background color
     * of the label based on the predefined mapping of role names to colors, and adds the label
     * to the roles container.
     *
     * @param role The {@link Role} object whose name will be displayed in the label.
     *             The color of the label is determined by the role's name.
     * @throws NullPointerException if the role is null or if the role name is not found
     *                              in the color mapping.
     */
    private void addLabel(Role role) {
        Label roleLabel = new Label(role.getRoleName());
        String color = roleColors.get(role.getRoleName());
        roleLabel.setStyle("-fx-background-color: " + color + ";");
        roles.getChildren().add(roleLabel);
    }

    /**
     * Adds a default "no role" label with a red background color if no roles are assigned.
     */
    private void addDefaultNoRoleLabel() {
        Label noRoleLabel = new Label("no role");
        noRoleLabel.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
        roles.getChildren().add(noRoleLabel);
    }

    private void setTextForTelegramUsername(Person person) {
        String teleUsername = person.getTelegramUsername().telegramUsername;
        if (teleUsername != null) {
            telegramUsername.setText("@" + teleUsername);
        }
    }

    @Override
    public void update(Person person) {
        roles.getChildren().clear();
        person.getRoles().stream()
                .sorted(Comparator.comparing(Role::getRoleName))
                .forEach(this::addLabel);
    }

    @Override
    public void update() {

    }
}
