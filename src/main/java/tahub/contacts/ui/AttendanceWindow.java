package tahub.contacts.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.logic.Logic;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.AttendanceSession;
import tahub.contacts.model.course.Course;
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
    private ListChangeListener<StudentCourseAssociation> scaListChangeListener;

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

        setupScaListChangeListener();
        setupCourseComboBox();

        // Register to listen for model changes
        logic.addListener(() -> {
            if (getRoot().isShowing()) {
                javafx.application.Platform.runLater(() -> {
                    refreshDisplay();
                    updateComboBoxItems();
                });
            }
        });

        // Add listener to the stage's showing property to refresh when shown
        root.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                refreshDisplay();
            }
        });
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
     * Sets up a listener to monitor changes in the SCA list.
     */
    private void setupScaListChangeListener() {
        scaListChangeListener = change -> {
            while (change.next()) {
                if (change.wasUpdated() || change.wasAdded() || change.wasRemoved() || change.wasReplaced()) {
                    if (getRoot().isShowing()) {
                        Platform.runLater(() -> {
                            // Force a complete rebuild of the ComboBox
                            ObservableList<StudentCourseAssociation> scaList =
                                    logic.getStudentScas(person).getByMatric(person.getMatricNumber().value);

                            // Store current selection's course code
                            String currentCourseCode = currentSca != null
                                    ? currentSca.getCourse().courseCode.toString() : null;

                            // Create new ComboBox with fresh data
                            ComboBox<StudentCourseAssociation> newComboBox = new ComboBox<>();
                            newComboBox.setItems(scaList);
                            newComboBox.setCellFactory(lv -> createComboBoxCell());
                            newComboBox.setButtonCell(createComboBoxCell());

                            // Restore selection based on course code
                            if (currentCourseCode != null) {
                                for (StudentCourseAssociation sca : scaList) {
                                    if (sca.getCourse().courseCode.toString().equals(currentCourseCode)) {
                                        newComboBox.setValue(sca);
                                        currentSca = sca;
                                        break;
                                    }
                                }
                            }

                            // Copy all properties from new ComboBox to existing one
                            courseComboBox.setItems(newComboBox.getItems());
                            courseComboBox.setCellFactory(newComboBox.getCellFactory());
                            courseComboBox.setButtonCell(newComboBox.getButtonCell());
                            courseComboBox.setValue(newComboBox.getValue());

                            refreshDisplay();
                        });
                    }
                }
            }
        };

        logic.getStudentScas(person).asUnmodifiableObservableList()
                .addListener(scaListChangeListener);
    }

    private void updateButtonCell() {
        StudentCourseAssociation selected = courseComboBox.getValue();
        if (selected != null) {
            courseComboBox.setButtonCell(new javafx.scene.control.ListCell<StudentCourseAssociation>() {
                @Override
                protected void updateItem(StudentCourseAssociation item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getCourse().courseCode + ": " + item.getCourse().courseName);
                    }
                }
            });
        }
    }

    /**
     * Sets up the course selection ComboBox with all courses the student is enrolled in.
     * Initializes the display with the first available course or shows a message if no
     * courses are available.
     */
    private void setupCourseComboBox() {
        ObservableList<StudentCourseAssociation> scaList = logic.getStudentScas(person)
                .getByMatric(person.getMatricNumber().value);

        courseComboBox.setItems(scaList);
        courseComboBox.setCellFactory(lv -> createComboBoxCell());
        courseComboBox.setButtonCell(createComboBoxCell());

        courseComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentSca = newValue;
                refreshDisplay();
            }
        });

        if (!scaList.isEmpty()) {
            courseComboBox.setValue(scaList.get(0));
            currentSca = scaList.get(0);
            refreshDisplay();
        } else {
            showNoCourseMessage();
        }
    }

    /**
     * Creates a ListCell for the ComboBox
     */
    private javafx.scene.control.ListCell<StudentCourseAssociation> createComboBoxCell() {
        return new javafx.scene.control.ListCell<StudentCourseAssociation>() {
            @Override
            protected void updateItem(StudentCourseAssociation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Get fresh course data every time
                    ObservableList<StudentCourseAssociation> currentList =
                            logic.getStudentScas(person).getByMatric(person.getMatricNumber().value);

                    StudentCourseAssociation latestSca = currentList.stream()
                            .filter(sca -> sca.isSameSca(item))
                            .findFirst()
                            .orElse(item);

                    Course course = latestSca.getCourse();
                    setText(course.courseCode + ": " + course.courseName);
                }
            }
        };
    }

    /**
     * Updates the items in the combo box
     */
    private void updateComboBoxItems() {
        ObservableList<StudentCourseAssociation> scaList = logic.getStudentScas(person)
                .getByMatric(person.getMatricNumber().value);

        StudentCourseAssociation selectedSca = courseComboBox.getValue();
        courseComboBox.setItems(scaList);

        if (selectedSca != null) {
            // Find and select the corresponding item in the new list
            scaList.stream()
                    .filter(sca -> sca.isSameSca(selectedSca))
                    .findFirst()
                    .ifPresent(sca -> {
                        courseComboBox.setValue(sca);
                        currentSca = sca;
                    });
        }
    }

    /**
     * Refreshes the display with current data.
     */
    private void refreshDisplay() {
        if (currentSca != null) {
            // Get fresh data
            ObservableList<StudentCourseAssociation> currentList =
                    logic.getStudentScas(person).getByMatric(person.getMatricNumber().value);

            // Find current SCA in fresh list
            StudentCourseAssociation latestSca = currentList.stream()
                    .filter(sca -> sca.isSameSca(currentSca))
                    .findFirst()
                    .orElse(currentSca);

            currentSca = latestSca;
            Course course = latestSca.getCourse();

            Platform.runLater(() -> {
                // Update labels
                courseCode.setText(course.courseCode.toString());
                courseName.setText(course.courseName.toString());
                tutorialCode.setText(latestSca.getTutorial().getTutorialId());
                tutorialName.setText(course.courseName.toString());

                // Force ComboBox to update its display
                courseComboBox.setButtonCell(createComboBoxCell());
                courseComboBox.setValue(latestSca); // This forces a refresh of the button cell

                // Update attendance list
                attendanceListView.setItems(FXCollections.observableArrayList(
                        latestSca.getAttendance().getAttendanceList()));

                int attended = latestSca.getAttendance().getAttendanceAttendedCount();
                int total = latestSca.getAttendance().getAttendanceTotalCount();
                double percentage = total == 0 ? 0 : (double) attended / total * 100;

                attendanceCount.setText(String.format("%d/%d sessions", attended, total));
                attendancePercentage.setText(String.format("%.1f%%", percentage));
            });
        } else {
            showNoCourseMessage();
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
            String commandText = "attend-present m/" + currentSca.getStudent().getMatricNumber()
                    + " c/" + currentSca.getCourse().courseCode.toString()
                    + " tut/" + currentSca.getTutorial().getTutorialId();
            try {
                logic.execute(commandText);
                refreshDisplay();
            } catch (CommandException | ParseException e) {
                logger.severe("Failed to mark attendance as present: " + e.getMessage());
            }
        }
    }

    /**
     * Handles the action of marking a student as absent for a new attendance session.
     * Updates the attendance record and refreshes the display.
     */
    @FXML
    private void handleMarkAbsent() {
        if (currentSca != null) {
            String commandText = "attend-absent m/" + currentSca.getStudent().getMatricNumber()
                    + " c/" + currentSca.getCourse().courseCode.toString()
                    + " tut/" + currentSca.getTutorial().getTutorialId();
            try {
                logic.execute(commandText);
                refreshDisplay();
            } catch (CommandException | ParseException e) {
                logger.severe("Failed to mark attendance as present: " + e.getMessage());
            }
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

    /**
     * Cleanup method to remove listeners when the window is closed.
     */
    public void cleanup() {
        if (scaListChangeListener != null) {
            logic.getStudentScas(person).asUnmodifiableObservableList()
                    .removeListener(scaListChangeListener);
        }
    }
}
