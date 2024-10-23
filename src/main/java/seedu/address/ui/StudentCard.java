package seedu.address.ui;

import static seedu.address.model.util.ContactType.STUDENT;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.person.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends PersonCard {

    @FXML
    private Label parentName;
    @FXML
    private Label parentPhone;
    @FXML
    private Label parentEmail;
    @FXML
    private Label grade;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public StudentCard(Student person, int displayedIndex) {
        super(STUDENT, person, displayedIndex);
        grade.setText(person.getGrade().gradeIndexToName());
        changeGradeStyle(person);
        parentName.setText("");
        parentPhone.setText("");
        parentEmail.setText("");
        if (person.getParentName() != null && person.getParentPhone() != null && person.getParentEmail() != null) {
            parentName.setText(person.getParentName().fullName);
            parentPhone.setText(person.getParentPhone().value);
            parentEmail.setText(person.getParentEmail().value);
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
