package tahub.contacts.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.util.logging.Logger;

import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.logic.Logic;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.course.AttendanceSession;
import tahub.contacts.model.course.Course;

public class AttendanceWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(AttendanceWindow.class);
    private static final String FXML = "AttendanceWindow.fxml";

    private Logic logic;
    private Person person;
    private StudentCourseAssociation currentSca;

    @FXML
    private ComboBox<StudentCourseAssociation> courseComboBox;
    @FXML
    private Label courseCode;
    @FXML
    private Label courseName;
    @FXML
    private Label tutorialCode;
    @FXML
    private Label tutorialName;
    @FXML
    private ListView<AttendanceSession> attendanceListView;
    @FXML
    private Label attendancePercentage;
    @FXML
    private Label attendanceCount;

    public AttendanceWindow(Stage root, Logic logic, Person person) {
        super(FXML, root);
        this.logic = logic;
        this.person = person;

        attendanceListView.setCellFactory(listView -> new AttendanceListCell());
        setupCourseComboBox();
    }

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

    private void showNoCourseMessage() {
        courseCode.setText("");
        courseName.setText("Student " + person.getName() + " has no course associations.");
        tutorialCode.setText("");
        tutorialName.setText("");
        attendanceCount.setText("0/0 sessions");
        attendancePercentage.setText("0%");
        attendanceListView.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void handleMarkPresent() {
        if (currentSca != null) {
            currentSca.getAttendance().addAttendedLesson();
            refreshDisplay();
        }
    }

    @FXML
    private void handleMarkAbsent() {
        if (currentSca != null) {
            currentSca.getAttendance().addAbsentLesson();
            refreshDisplay();
        }
    }

    public AttendanceWindow(Logic logic, Person person) {
        this(new Stage(), logic, person);
    }

    public void show() {
        logger.fine("Showing attendance window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }
}
