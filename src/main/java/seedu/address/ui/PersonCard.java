package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.MainApp;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleType;

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
    private VBox vBox;
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
    private FlowPane tags;
    @FXML
    private FlowPane roles;

    private final Image studentIcon =
        new Image(MainApp.class.getResourceAsStream("/images/role-icons/student.png"));
    private final Image tutorIcon =
        new Image(MainApp.class.getResourceAsStream("/images/role-icons/tutor.png"));
    private final Image professorIcon =
        new Image(MainApp.class.getResourceAsStream("/images/role-icons/professor.png"));


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        if (person.hasAddress()) {
            Label address = new Label(person.getAddress().map(Object::toString).orElse(null));
            address.getStyleClass().add("cell_small_label");
            address.setText(person.getAddress().orElse(null).value);
            vBox.getChildren().add(address);
        }

        email = new Label(person.getEmail().value);
        email.getStyleClass().add("cell_small_label");
        vBox.getChildren().add(email);

        // Add tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Add module role pairs
        person.getModuleRoleMap().getData().stream()
                .sorted(Comparator.comparing(moduleRolePair -> moduleRolePair.moduleCode.toString()))
                .forEach(moduleRolePair -> {
                    Label curLabel = new Label(moduleRolePair.toString());

                    RoleType roleType = moduleRolePair.roleType;

                    // Update CSS class
                    String cssClass = roleType.toString().toLowerCase();
                    curLabel.getStyleClass().add(cssClass);

                    if (roleType != null) {
                        Image image =
                            roleType == RoleType.STUDENT ? studentIcon
                            : roleType == RoleType.TUTOR ? tutorIcon
                            : professorIcon;

                        // Scale height but preserve aspect ratio
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(14);
                        imageView.setPreserveRatio(true);

                        curLabel.setGraphic(imageView);
                        curLabel.setContentDisplay(javafx.scene.control.ContentDisplay.RIGHT);
                    }

                    roles.getChildren().add(curLabel);
                });
    }
}
