package seedu.eventfulnus.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Role;

/**
 * A UI component that displays information of a {@code Person}.
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
    private VBox cardVBox;
    @FXML
    private HBox cardTextHBox;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private VBox roles;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().toString());
        name.setWrapText(true);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        person.getRoles().stream()
                .sorted(Comparator.comparing(Role::getRoleName))
                .forEach(role -> {
                    Label roleLabel = new Label(role.getRoleName() + ", ");
                    roleLabel.setWrapText(true);
                    roles.getChildren().add(roleLabel);
                });
    }
}
