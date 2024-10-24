package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import static seedu.address.model.tag.Tag.BLOOD_TYPE_PREFIX;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String CSS_THEME_APPT = "-fx-font-family: \"Segoe UI\";\n"
            + "    -fx-font-size: 13px;";

    private static final String CSS_THEME_BLOODTYPE = "    -fx-text-fill: white;\n" +
            "    -fx-background-color: #d06651;\n" +
            "    -fx-padding: 1 3 1 3;\n" +
            "    -fx-border-radius: 2;\n" +
            "    -fx-background-radius: 2;\n" +
            "    -fx-font-size: 11;";

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
    private Label age;
    @FXML
    private Label gender;
    @FXML
    private Label nric;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label appointment;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane bloodType;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        age.setText("Age : " + person.getAge().value);
        gender.setText("Gender : " + person.getGender().value);
        nric.setText("NRIC : " + person.getNric().fullNric);
        phone.setText("Phone : " + person.getPhone().value);
        address.setText("Address : " + person.getAddress().value);
        email.setText("Email : " + person.getEmail().value);
        appointment.setText("Appointment : " + person.getAppointment().dateTime);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = null;

                    if (tag.isBloodType) {
                        tagLabel = new Label(BLOOD_TYPE_PREFIX + tag.tagName);
                        tagLabel.setStyle(CSS_THEME_BLOODTYPE);
                    } else {
                        tagLabel = new Label(tag.tagName);
                    }
                    tags.getChildren().add(tagLabel);

                });

        if (person.getAppointment().isToday()) {
            appointment.setStyle(CSS_THEME_APPT + " -fx-text-fill: #86ff1c;"); // Green
        } else if (person.getAppointment().hasPassed()) {
            appointment.setStyle(CSS_THEME_APPT + " -fx-text-fill: #ff0500;"); // Red
        } else if (person.getAppointment().hasNotPassed()) {
            appointment.setStyle(CSS_THEME_APPT + " -fx-text-fill: #f0c44a;"); // Yellow
        } else {
            appointment.setStyle(CSS_THEME_APPT+ " -fx-text-fill: white;"); // White
        }
    }
}
