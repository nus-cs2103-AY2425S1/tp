package tahub.contacts.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.logic.Logic;
import tahub.contacts.model.course.AttendanceSession;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;

/**
 * A JavaFX window that displays and manages attendance information for a student's courses.
 * <p>
 * This class provides a user interface for viewing and managing student attendance across
 * different courses. It includes features such as:
 * <ul>
 *   <li>Course selection via combo box</li>
 *   <li>Display of course and tutorial details</li>
 *   <li>Attendance history listing</li>
 *   <li>Attendance statistics</li>
 *   <li>Functionality to mark attendance as present or absent</li>
 * </ul>
 * </p>
 *
 * @see UiPart
 * @see Logic
 * @see Person
 * @see StudentCourseAssociation
 */
public class AttendanceWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(AttendanceWindow.class);
    private static final String FXML = "AttendanceWindow.fxml";

    /** The logic component that handles backend operations. */
    private final Logic logic;
    /** The student whose attendance is being displayed. */
    private final Person person;
    /** The currently selected student-course association. */
    private StudentCourseAssociation currentSca;

    // FXML injected fields documentation
    /** ComboBox for selecting different courses the student is enrolled in. */
    @FXML
    private ComboBox<StudentCourseAssociation> courseComboBox;
    /** Label displaying the course code. */
    @FXML
    private Label courseCode;
    /** Label displaying the course name. */
    @FXML
    private Label courseName;
    /** Label displaying the tutorial code. */
    @FXML
    private Label tutorialCode;
    /** Label displaying the tutorial name. */
    @FXML
    private Label tutorialName;
    /** ListView displaying the attendance sessions. */
    @FXML
    private ListView<AttendanceSession> attendanceListView;
    /** Label displaying the attendance percentage. */
    @FXML
    private Label attendancePercentage;
    /** Label displaying the attendance count. */
    @FXML
    private Label attendanceCount;

    /**
     * Creates a new AttendanceWindow with a specified root stage, logic component, and person.
     *
     * @param root The stage that will serve as the root of this window
     * @param logic The logic component to handle backend operations
     * @param person The student whose attendance is being displayed
     */
    public AttendanceWindow(Person person, Logic logic, Stage root) {
        super(FXML, root);
        this.logic = logic;
        this.person = person;
        attendanceListView.setCellFactory(listView -> new AttendanceListCell());
        setupCourseComboBox();
    }

    /**
     * Creates a new AttendanceWindow with a new Stage.
     *
     * @param logic The logic component to handle backend operations
     * @param person The student whose attendance is being displayed
     */
    public AttendanceWindow(Person person, Logic logic) {
        this(person, logic, new Stage());
    }

    /**
     * Sets up the course selection ComboBox with all courses the student is enrolled in.
     * Initializes the display with the first available course or shows a message if no
     * courses are available.
     */
    private void setupCourseComboBox() {
        // Get all SCAs for this student
        ObservableList<StudentCourseAssociation> scaList = this.logic.getStudentScas(person)
                .getByMatric(person.getMatricNumber().value);

        // Set up the combo box
        courseComboBox.setItems(scaList);

        // Set up the display converter for the combo box
        courseComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<StudentCourseAssociation>() {
            @Override
            protected void updateItem(StudentCourseAssociation sca, boolean empty) {
                super.updateItem(sca, empty);
                if (empty || sca == null) {
                    setText(null);
                } else {
                    setText(sca.getCourse().courseCode + ": " + sca.getCourse().courseName);
                }
            }
        });

        courseComboBox.setButtonCell(new javafx.scene.control.ListCell<StudentCourseAssociation>() {
            @Override
            protected void updateItem(StudentCourseAssociation sca, boolean empty) {
                super.updateItem(sca, empty);
                if (empty || sca == null) {
                    setText(null);
                } else {
                    setText(sca.getCourse().courseCode + ": " + sca.getCourse().courseName);
                }
            }
        });

        // Add listener for course selection changes
        courseComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentSca = newValue;
                refreshDisplay();
            }
        });

        // Select first course if available
        if (!scaList.isEmpty()) {
            courseComboBox.getSelectionModel().select(0);
            currentSca = scaList.get(0);
            refreshDisplay();
        } else {
            showNoCourseMessage();
        }
    }

    /**
     * Refreshes the display with the latest data for the currently selected course.
     * Updates all UI elements including course details, attendance list, and statistics.
     */
    private void refreshDisplay() {
        if (currentSca != null) {
            // Refresh the current SCA from the logic to get the latest data
            currentSca = logic.getStudentScas(person)
                    .getByMatric(person.getMatricNumber().value)
                    .filtered(sca -> sca.equals(currentSca))
                    .get(0);

            courseCode.setText(String.valueOf(currentSca.getCourse().courseCode));
            courseName.setText(String.valueOf(currentSca.getCourse().courseName));
            tutorialCode.setText(currentSca.getTutorial().getTutorialId());
            tutorialName.setText(String.valueOf(currentSca.getTutorial().getCourse().courseName));

            // Update attendance list
            attendanceListView.setItems(FXCollections.observableArrayList(
                    currentSca.getAttendance().getAttendanceList()));

            // Update summary
            int attended = currentSca.getAttendance().getAttendanceAttendedCount();
            int total = currentSca.getAttendance().getAttendanceTotalCount();
            double percentage = total == 0 ? 0 : (double) attended / total * 100;

            attendanceCount.setText(String.format("%d/%d sessions", attended, total));
            attendancePercentage.setText(String.format("%.1f%%", percentage));
        }
    }

    /**
     * Displays a message indicating that the student has no course associations.
     * Clears all course-related information from the display.
     */
    private void showNoCourseMessage() {
        courseCode.setText("");
        courseName.setText("Student " + person.getName() + " has no course associations.");
        tutorialCode.setText("");
        tutorialName.setText("");
        attendanceCount.setText("0/0 sessions");
        attendancePercentage.setText("0%");
        attendanceListView.setItems(FXCollections.observableArrayList());
    }

    /**
     * Handles the action of marking a student as present for a new attendance session.
     * Updates the attendance record and refreshes the display.
     */
    @FXML
    private void handleMarkPresent() {
        if (currentSca != null) {
            currentSca.getAttendance().addAttendedLesson();
            refreshDisplay();
        }
    }
    /**
     * Handles the action of marking a student as absent for a new attendance session.
     * Updates the attendance record and refreshes the display.
     */
    @FXML
    private void handleMarkAbsent() {
        if (currentSca != null) {
            currentSca.getAttendance().addAbsentLesson();
            refreshDisplay();
        }
    }


    /**
     * Shows the attendance window and centers it on the screen.
     */
    public void show() {
        logger.fine("Showing attendance window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Checks if the attendance window is currently showing.
     *
     * @return true if the window is showing, false otherwise
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the attendance window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Requests focus for the attendance window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
