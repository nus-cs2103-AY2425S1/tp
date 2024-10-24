package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;


/**
 * Represents a window that displays the attendance of students in a tutorial group.
 */
public class AttendanceWindow {

    private final TutorialGroup tutorialGroup;

    public AttendanceWindow(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    /**
     * Displays the attendance window.
     * @param model The model containing the data to display.
     */
    public void show(Model model) {
        Platform.runLater(() -> { });
        Stage stage = new Stage();
        stage.setTitle("Attendance for Tutorial Group: " + tutorialGroup.toString());

        TableView<AttendanceRow> table = new TableView<>();

        TableColumn<AttendanceRow, String> studentNameColumn = new TableColumn<>("Student");
        studentNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentName()));
        table.getColumns().add(studentNameColumn);

        Set<LocalDate> attendanceDates = getAllAttendanceDates(model);
        for (LocalDate date : attendanceDates) {
            TableColumn<AttendanceRow, String> dateColumn =
                    new TableColumn<>( DateTimeFormatter.ofPattern("MMM d yyyy").format(date));

            dateColumn.setCellValueFactory(cellData -> {
                AttendanceRow row = cellData.getValue();
                return new SimpleStringProperty(row.getAttendanceForDate(date));
            });

            table.getColumns().add(dateColumn);
        }

        ObservableList<AttendanceRow> data = getStudentAttendanceRows(model);
        table.setItems(data);

        VBox vbox = new VBox(table);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Collect all unique attendance dates from all students.
     */
    public Set<LocalDate> getAllAttendanceDates(Model model) {
        Set<LocalDate> allDates = new TreeSet<>(); // TreeSet to ensure sorted order
        for (Student student : model.getStudentsByTutorialGroup(tutorialGroup)) {
            for (AttendanceRecord record : student.getAttendanceRecord()) {
                allDates.add(record.getDate());
            }
        }
        return allDates;
    }

    /**
     * Creates rows for the student attendance table.
     */
    public ObservableList<AttendanceRow> getStudentAttendanceRows(Model model) {
        ObservableList<AttendanceRow> rows = FXCollections.observableArrayList();
        for (Student student : model.getStudentsByTutorialGroup(tutorialGroup)) {
            AttendanceRow row = new AttendanceRow(student);
            for (AttendanceRecord record : student.getAttendanceRecord()) {
                row.addAttendance(record.getDate(), record.getAttendance().toString());
            }
            rows.add(row);
        }
        return rows;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }
}
