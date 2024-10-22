package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private FlowPane tags;
    @FXML
    private Label subjects;
    @FXML
    private Label classes;
    @FXML
    private Label gender;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        if (person.getTags().stream().anyMatch(tag -> tag.tagName.equals("student"))) {
            cardPane.setStyle("-fx-background-color: #A8D1FF;"); // Inline style for student
        }

        if (person.getTags().stream().anyMatch(tag -> tag.tagName.equals("teacher"))) {
            cardPane.setStyle("-fx-background-color: #A4D4A1;"); // Inline style for teacher
        }

        // Set other UI components
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        String formattedGender = person.getGender().value == "male" ? "Male" : "Female";
        gender.setText("👫 " + formattedGender);
        phone.setText("📱 " + person.getPhone().value);
        address.setText("📍 " + person.getAddress().value);
        email.setText("📨 " + person.getEmail().value);
        String formattedSubjects = String.join(" • ", person.getSubjects().stream()
                .map(subject -> subject.subjectName)
                .toArray(String[]::new));
        subjects.setText("📚 " + formattedSubjects);
        String formattedClasses = String.join(" • ", person.getClasses().stream()
                .toArray(String[]::new));
        classes.setText("🏫 " + formattedClasses);

    }

}
