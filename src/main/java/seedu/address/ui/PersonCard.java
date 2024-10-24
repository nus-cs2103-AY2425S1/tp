package seedu.address.ui;

import java.util.Comparator;
import java.util.Objects;

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
    private static final String LIGHT_PHONE_ICON = "/images/phone_icon_light.png";
    private static final String DARK_PHONE_ICON = "/images/phone_icon_dark.png";
    private static final String LIGHT_ADDRESS_ICON = "/images/address_icon_light.png";
    private static final String DARK_ADDRESS_ICON = "/images/address_icon_dark.png";
    private static final String LIGHT_EMAIL_ICON = "/images/email_icon_light.png";
    private static final String DARK_EMAIL_ICON = "/images/email_icon_dark.png";

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
    private FlowPane tags;
    @FXML
    private ImageView phoneIcon;
    @FXML
    private ImageView addressIcon;
    @FXML
    private ImageView emailIcon;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, String theme) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        Image phoneImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(theme.equals("dark")
                ? DARK_PHONE_ICON : LIGHT_PHONE_ICON)));
        phoneIcon.setImage(phoneImage);
        address.setText(person.getAddress().getValueForUI());
        Image addressImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(theme.equals("dark")
            ? DARK_ADDRESS_ICON : LIGHT_ADDRESS_ICON)));
        addressIcon.setImage(addressImage);
        email.setText(person.getEmail().getValueForUI());
        Image emailImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(theme.equals("dark")
                ? DARK_EMAIL_ICON : LIGHT_EMAIL_ICON)));
        emailIcon.setImage(emailImage);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
