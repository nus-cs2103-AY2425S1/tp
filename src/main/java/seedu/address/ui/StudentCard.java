package seedu.address.ui;

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

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public StudentCard(Student person, int displayedIndex) {
        super(person, displayedIndex);
        parentName.setText("");
        parentPhone.setText("");
        parentEmail.setText("");
        if (person.getParentName() != null && person.getParentPhone() != null && person.getParentEmail() != null) {
            parentName.setText(person.getParentName().fullName);
            parentPhone.setText(person.getParentPhone().value);
            parentEmail.setText(person.getParentEmail().value);
        }
    }


}
