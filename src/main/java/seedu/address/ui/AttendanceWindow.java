package seedu.address.ui;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.model.attendance.AttendanceRecord;

import java.time.LocalDate;
import java.util.*;

public class AttendanceWindow {

    private final TutorialGroup tutorialGroup;

    public AttendanceWindow(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public void show(Model model) {
        Stage stage = new Stage();
        stage.setTitle("Attendance for Tutorial Group: " + tutorialGroup.toString());

        // Create the table view
        TableView<AttendanceRow> table = new TableView<>();

        // Create columns
        TableColumn<AttendanceRow, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        table.getColumns().add(dateColumn);

        for (Student student : model.getStudentsByTutorialGroup(tutorialGroup)) {
            TableColumn<AttendanceRow, String> studentColumn = new TableColumn<>(student.getName().fullName);

            // Custom cell value factory for each student
            studentColumn.setCellValueFactory(cellData -> {
                AttendanceRow row = cellData.getValue();
                return new SimpleStringProperty(row.getAttendanceForStudent(student.getName().fullName));
            });

            table.getColumns().add(studentColumn);
        }

        ObservableList<AttendanceRow> data = getAttendanceRows(model);
        table.setItems(data);


        VBox vbox = new VBox(table);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);


        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates rows for the attendance table.
     */
    private ObservableList<AttendanceRow> getAttendanceRows(Model model) {
        ObservableList<AttendanceRow> rows = FXCollections.observableArrayList();
        // Assuming we collect all attendance records for each student in this tutorial group
        for (AttendanceRecord record : collectAllAttendanceRecords(model)) {
            AttendanceRow row = new AttendanceRow(record.getDate());
            for (Student student : model.getStudentsByTutorialGroup(tutorialGroup)) {
                row.addAttendance(student.getName().fullName, getAttendanceForStudentOnDate(student, record.getDate()));
                //System.out.println(row.getDate());
            }
            rows.add(row);
        }
        return rows;
    }

    /**
     * Utility method to get attendance for a student on a given date.
     */
    private String getAttendanceForStudentOnDate(Student student, LocalDate date) {
        for (AttendanceRecord record : student.getAttendanceRecord()) {
            if (record.getDate().equals(date)) {
                return record.getAttendance().toString(); // "Present" or "Absent"
            }
        }
        return "Absent"; // Default to absent if no record is found
    }

    /**
     * Collect all attendance records from all students to build the list of rows.
     */
    private List<AttendanceRecord> collectAllAttendanceRecords(Model model) {
        // Flatten attendance records across all students in the tutorial group
        Set<AttendanceRecord> allRecords = new TreeSet<>(Comparator.comparing(AttendanceRecord::getDate));
        for (Student student : model.getStudentsByTutorialGroup(tutorialGroup)) {
            allRecords.addAll(student.getAttendanceRecord());
        }
        return new ArrayList<>(allRecords);
    }
}
