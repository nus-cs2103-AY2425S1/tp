package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
    private Label age;
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
    public PersonDetailedView(Person person, boolean isVisualsEnabled) {
        super(FXML);
        this.person = person;

        Image profileImg = new Image(getClass()
                .getResourceAsStream("/" + this.person.getProfilePicFilePath().toString()));
        profileImage.setImage(profileImg);

        name.setText(person.getName().fullName);

        // check for recent birthday
        if (isVisualsEnabled && person.getBirthday().hasBirthdayWithin7Days()) {
            name.setStyle("-fx-text-fill: #ffa500");
            Tooltip birthdayTooltip = new Tooltip("Birthday soon!");
            birthdayTooltip.setShowDelay(javafx.util.Duration.millis(10));
            Tooltip.install(name, birthdayTooltip);
        }

        phone.setText("+65 " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        birthday.setText("Birthday: " + person.getBirthday().value);
        age.setText("Age: " + person.getAge().value);
        email.setText(person.getEmail().value);
        hasPaid.setText("Paid status: " + (person.getHasPaid() ? "Paid" : "Not Paid"));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);

                    if (isVisualsEnabled) {
                        if ("highnetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-high");
                        } else if ("midnetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-mid");
                        } else if ("lownetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-low");
                        }
                    }
                    tags.getChildren().add(tagLabel);
                });
        frequency.setText("Policy Renewal Frequency: " + person.getFrequency().value + " month(s)");
    }
}
