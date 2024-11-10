package seedu.address.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        ArrayList<ImageView> icons = new ArrayList<>();
        String[] iconPaths = {"phone_icon.png", "location_icon.png", "email_icon.png", "remark_icon.png"};

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().getDisplayableName());
        name.setWrapText(true);
        phone.setText(person.getPhone().getDisplayablePhone());
        phone.setWrapText(true);
        address.setText(person.getAddress().getDisplayableAddress());
        address.setWrapText(true);
        email.setText(person.getEmail().getDisplayableEmail());
        email.setWrapText(true);
        remark.setText(person.getRemark().getDisplayableRemark());
        remark.setWrapText(true);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Defining a for loop to add icons to the labels
        for (String iconPath : iconPaths) {
            InputStream iconStream = this.getClass().getResourceAsStream("/images/" + iconPath);
            if (iconStream != null) {
                ImageView icon = new ImageView(new Image(iconStream));
                icon.setFitWidth(12);
                icon.setPreserveRatio(true);
                icons.add(icon);
            } else {
                System.err.println("Resource not found: " + iconPath);
            }
        }
        if (icons.size() == iconPaths.length) {
            phone.setGraphic(icons.get(0));
            address.setGraphic(icons.get(1));
            email.setGraphic(icons.get(2));
            remark.setGraphic(icons.get(3));
        }
    }
}
