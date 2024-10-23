package seedu.address.ui;

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
 * A UI Component that displays a detailed view of a {@code Person}
 */
public class PersonDetailedView extends UiPart<Region> {
    private static final String FXML = "PersonDetailedView.fxml";

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
    private Label birthday;
    @FXML
    private Label hasPaid;
    @FXML
    private FlowPane tags;
    @FXML
    private Label frequency;
    @FXML
    private Label placeholderLabel;
    @FXML
    private ImageView profileImage;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} to display.
     */
    public PersonDetailedView(Person person) {
        super(FXML);
        this.person = person;

        Image profileImg = new Image(getClass().getResourceAsStream("/images/profilepicture.png"));
        profileImage.setImage(profileImg);

        name.setText(person.getName().fullName);
        phone.setText("+65 " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        birthday.setText("Birthday: " + person.getBirthday().value);
        email.setText(person.getEmail().value);
        hasPaid.setText("Paid status: " + (person.getHasPaid() ? "Paid" : "Not Paid"));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        frequency.setText("Policy Renewal Frequency: " + person.getFrequency().value + " month(s)");
    }
}
