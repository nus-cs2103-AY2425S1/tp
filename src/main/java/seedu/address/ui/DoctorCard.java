package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.doctor.Doctor;

/**
 * A UI component that displays information of a {@code Doctor}.
 */
public class DoctorCard extends PersonCard {

    private static final String FXML = "DoctorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label specialty;

    /**
     * Creates a {@code DoctorCode} with the given {@code Doctor} and index to display.
     */
    public DoctorCard(Doctor doctor, int displayedIndex) {
        super(doctor, displayedIndex);
        specialty.setText(doctor.getSpeciality().value);
    }
}
