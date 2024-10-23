package seedu.academyassist.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.model.person.Person;

/**
 * Controller for a student's detail page.
 */
public class PersonDetailWindow extends UiPart<Stage> {

    public static final Logger LOGGER = LogsCenter.getLogger(PersonDetailWindow.class);
    public static final String FXML = "PersonDetailWindow.fxml";

    @FXML
    private VBox detailPane;
    @FXML
    private Label studentId;
    @FXML
    private Label name;
    @FXML
    private Label ic;
    @FXML
    private Label yearGroup;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label subject;

    /**
     * Creates a new PersonDetailWindow.
     *
     * @param root Stage to use as the root of the PersonDetailWindow.
     */
    public PersonDetailWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new PersonDetailWindow.
     */
    public PersonDetailWindow() {
        this(new Stage());
    }

    /**
     * Shows the person detail window.
     *
     * @param person The person to show details for
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show(Person person) {
        // log the progress
        LOGGER.fine(String.format("Showing details of student %s", studentId));

        // Display the student details
        studentId.setText("Student ID: " + person.getStudentId().value);
        name.setText("Name: " + person.getName().fullName);
        ic.setText("IC: " + person.getIc().value);
        yearGroup.setText("Year: " + person.getYearGroup().value);
        phone.setText("Phone: " + person.getPhone().value);
        email.setText("Email: " + person.getEmail().value);
        address.setText("Address: " + person.getAddress().value);
        subject.setText("Subject(s) taken: " + person.getSubjects().toString());


        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the person detail window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the person detail window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the person detail window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
