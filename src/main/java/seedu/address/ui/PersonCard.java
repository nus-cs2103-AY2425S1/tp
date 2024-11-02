package seedu.address.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
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

    private static final Image phoneIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/phone.png"));
    private static final Image emailIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/email.png"));
    private static final Image addressIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/address.png"));
    private static final Image descriptionIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/description.png"));
    private static final Image studentIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/role-icons/student.png"));
    private static final Image tutorIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/role-icons/tutor.png"));
    private static final Image professorIcon =
        new Image(MainApp.class.getResourceAsStream("/images/person-card-icons/role-icons/professor.png"));

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
    private FlowPane tags;
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

        if (person.hasPhone()) {
            setRow(person.getPhone().map(Objects::toString).orElse(null),
                phoneIcon);
        }

        if (person.hasEmail()) {
            setRow(person.getEmail().map(Objects::toString).orElse(null),
                emailIcon);
        }

        if (person.hasAddress()) {
            setRow(person.getAddress().map(Objects::toString).orElse(null),
                addressIcon);
        }

        if (person.hasNonEmptyDescription()) {
            setRow(person.getDescription().map(Objects::toString).orElse(null),
                descriptionIcon);
        }

        // Add tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Add module role pairs
        Integer roleLabelFontSize = 14;
        person.getModuleRoleMap().getData(true).stream()
                .forEach(moduleRolePair -> {
                    Label curLabel = new Label(moduleRolePair.toString());

                    curLabel.setStyle(curLabel.getStyle() + " -fx-font-size: " + roleLabelFontSize.toString());

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
                        imageView.setFitHeight(roleLabelFontSize);
                        imageView.setPreserveRatio(true);

                        curLabel.setGraphic(imageView);
                        curLabel.setContentDisplay(javafx.scene.control.ContentDisplay.RIGHT);
                    }

                    roles.getChildren().add(curLabel);
                });
    }

    /**
     * Inserts a new row in the Person Card with the specified text, label and
     * icon.
     */
    public void setRow(String text, Image iconImage) {
        // Configuration of row styles
        int personCardIconSize = 14;
        int personCardRowElementSpacing = 5;
        ColorAdjust personCardIconColor = new ColorAdjust();
        personCardIconColor.setBrightness(1.0);
        personCardIconColor.setSaturation(-1);

        HBox row = new HBox();
        row.setSpacing(personCardRowElementSpacing);

        Label content = new Label(text);
        content.getStyleClass().add("cell_small_label");

        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(personCardIconSize);
        imageView.setPreserveRatio(true);

        // Make sure that the icon is always white regardless of original color
        imageView.setEffect(personCardIconColor);

        VBox icon = new VBox(imageView);
        icon.setAlignment(Pos.CENTER);

        row.getChildren().add(icon);
        row.getChildren().add(content);
        vBox.getChildren().add(row);
    }
}
