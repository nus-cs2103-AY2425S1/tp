package seedu.address.ui;

import static seedu.address.model.util.ContactType.STUDENT;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends PersonCard {

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label parentName;
    @FXML
    private Label parentPhone;
    @FXML
    private Label parentEmail;
    @FXML
    private Label education;
    @FXML
    private Label grade;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public StudentCard(Student person, int displayedIndex, ReadOnlyAddressBook addressBook) {
        super(STUDENT, person, displayedIndex, addressBook);
        education.setText(person.getEducation().educationLevel);
        education.getStyleClass().add("education-label");
        grade.setText(person.getGrade().gradeIndexToName());
        changeGradeStyle(person);
        parentName.setText("");
        parentPhone.setText("");
        parentEmail.setText("");
        if (person.getParentName() != null) {
            try {
                Person parent = addressBook.personFromName(person.getParentName());
                if (!(parent instanceof Parent)) {
                    throw new IllegalValueException("Parent of" + person.getName().fullName + "is not a Parent");
                }
                parentName.setText(parent.getName().fullName);
                parentPhone.setText(parent.getPhone().value);
                parentEmail.setText(parent.getEmail().value);
            } catch (IllegalValueException e) {
                logger.warning(e.getMessage());
            }
        }
    }

    private void changeGradeStyle(Student person) {
        grade.getStyleClass().add("grade-label");
        switch (person.getGrade().gradeIndex) {
        case "1":
            grade.getStyleClass().add("failing");
            break;
        case "2":
            grade.getStyleClass().add("satisfactory");
            break;
        case "3":
            grade.getStyleClass().add("good");
            break;
        case "4":
            grade.getStyleClass().add("excellent");
            break;
        default:
            // Don't change anything
            break;
        }
    }


}
