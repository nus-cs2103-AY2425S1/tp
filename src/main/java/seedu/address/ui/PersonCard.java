package seedu.address.ui;

import static seedu.address.model.person.Address.EMPTY_ADDRESS;
import static seedu.address.model.person.Student.STUDENT_TYPE;
import static seedu.address.model.person.Teacher.TEACHER_TYPE;

import java.util.Comparator;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;
import seedu.address.model.person.exceptions.InvalidPersonTypeException;

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
    @FXML
    private Label daysAttended;
    @FXML
    private Label nextOfKin;
    @FXML
    private Label emergencyContact;
    @FXML
    private HBox daysAttendedContainer;
    @FXML
    private HBox nextOfKinContainer;
    @FXML
    private HBox emergencyContactContainer;

    /**
     * Creates a {@code PersonCode} with the given {@code Student} and index to display.
     */
    public PersonCard(Student student, int displayedIndex) {
        super(FXML);
        this.person = student;

        cardPane.setStyle("-fx-background-color: #5a83a3;"); // Inline style for student

        // Set other UI components
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        name.setWrapText(true);
        String formattedGender = student.getGender().value.toLowerCase().equals("male") ? "Male" : "Female";
        gender.setText("👫 " + formattedGender);
        phone.setText("📱 " + student.getPhone().value);
        address.setText("📍 " + student.getAddress().value);
        address.setWrapText(true);
        person.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: #FF6F61");
                tags.getChildren().add(tagLabel);
            });
        if (student.getAddress().value.equals(EMPTY_ADDRESS)) {
            address.setStyle("-fx-text-fill: #BEBEBE;");
        }

        email.setText("📨 " + student.getEmail().value);
        email.setWrapText(true);
        String formattedSubjects = String.join(" • ", student.getSubjects().stream()
            .map(subject -> subject.subjectName)
            .toArray(String[]::new));
        subjects.setText("📚 " + formattedSubjects);
        subjects.setWrapText(true);
        String formattedClasses = String.join(" • ", student.getClasses().stream()
            .toArray(String[]::new));
        classes.setText("🏫 " + formattedClasses);
        classes.setWrapText(true);

        daysAttended.textProperty().bind(
            Bindings.format("📅 Days Attended: %d", student.daysAttendedProperty())
        );
        daysAttendedContainer.setVisible(true);
        daysAttendedContainer.setManaged(true);

        nextOfKin.setText("👪 Next of Kin: " + student.getNextOfKinName().fullName);
        nextOfKin.setWrapText(true);
        nextOfKinContainer.setVisible(true);
        nextOfKinContainer.setManaged(true);

        emergencyContact.setText("📞 Emergency Contact: " + student.getEmergencyContact().value);
        emergencyContactContainer.setVisible(true);
        emergencyContactContainer.setManaged(true);
    }

    /**
     * Creates a {@code PersonCode} with the given {@code Teacher} and index to display.
     */
    public PersonCard(Teacher teacher, int displayedIndex) {
        super(FXML);
        this.person = teacher;

        cardPane.setStyle("-fx-background-color: #5aa366;"); // Inline style for teacher

        // Set other UI components
        id.setText(displayedIndex + ". ");
        name.setText(teacher.getName().fullName);
        name.setWrapText(true);
        String formattedGender = teacher.getGender().value.toLowerCase().equals("male") ? "Male" : "Female";
        gender.setText("👫 " + formattedGender);
        phone.setText("📱 " + teacher.getPhone().value);
        address.setText("📍 " + teacher.getAddress().value);
        address.setWrapText(true);
        person.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: #FF6F61");
                tags.getChildren().add(tagLabel);
            });
        if (teacher.getAddress().value.equals(EMPTY_ADDRESS)) {
            address.setStyle("-fx-text-fill: #BEBEBE");
        }

        email.setText("📨 " + teacher.getEmail().value);
        email.setWrapText(true);
        String formattedSubjects = String.join(" • ", teacher.getSubjects().stream()
            .map(subject -> subject.subjectName)
            .toArray(String[]::new));
        subjects.setText("📚 " + formattedSubjects);
        subjects.setWrapText(true);
        String formattedClasses = String.join(" • ", teacher.getClasses().stream()
            .toArray(String[]::new));
        classes.setText("🏫 " + formattedClasses);
        classes.setWrapText(true);

        daysAttendedContainer.setVisible(false);
        daysAttendedContainer.setManaged(false);
        nextOfKinContainer.setVisible(false);
        nextOfKinContainer.setManaged(false);
        emergencyContactContainer.setVisible(false);
        emergencyContactContainer.setManaged(false);
    }

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public static PersonCard createPersonCard(Person person, int displayedIndex) throws InvalidPersonTypeException {
        if (person.getType().equals(STUDENT_TYPE)) {
            return new PersonCard((Student) person, displayedIndex);
        } else if (person.getType().equals(TEACHER_TYPE)) {
            return new PersonCard((Teacher) person, displayedIndex);
        } else {
            throw new InvalidPersonTypeException();
        }
    }

}
