package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    private TableView<AttendanceRow> table;
    private ObservableList<AttendanceRow> data;

    /**
     * Creates a new AttendanceWindow for the specified tutorial group.
     * @param tutorialGroup The tutorial group to display attendance for.
     */
    public AttendanceWindow(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    /**
     * Displays the attendance window.
     * @param model The model to get the student data from.
     */
    public void show(Model model) {
        Platform.runLater(() -> { });
        Stage stage = new Stage();
        stage.setTitle("Attendance for Tutorial Group: " + tutorialGroup.toString());
        table = new TableView<>();
        TableColumn<AttendanceRow, String> studentNameColumn = new TableColumn<>("Student");
        studentNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentName()));
        table.getColumns().add(studentNameColumn);
        Set<LocalDate> attendanceDates = getAllAttendanceDates(model);
        for (LocalDate date : attendanceDates) {
            addDateColumn(date);
        }
        data = getStudentAttendanceRows(model);
        table.setItems(data);
        VBox vbox = new VBox(table);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMinHeight(Region.USE_COMPUTED_SIZE);
        table.setMinWidth(Region.USE_COMPUTED_SIZE);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();

        model.getFilteredStudentList().addListener((ListChangeListener<Student>) change -> {
            System.out.println("Student list changed");
            refreshTable(model);
        });

        for (AttendanceRow row : data) {
            addAttendanceListener(row, attendanceDates, model);
            for (AttendanceRecord record : row.student.getAttendanceRecord()) {
                record.addListener(observable -> refreshTable(model));
            }
        }

    }

    private void addAttendanceListener(AttendanceRow row, Set<LocalDate> attendanceDates, Model model) {
        row.student.getAttendanceRecord().addListener((ListChangeListener<AttendanceRecord>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (AttendanceRecord newRecord : change.getAddedSubList()) {
                        if (!attendanceDates.contains(newRecord.getDate())) {
                            attendanceDates.add(newRecord.getDate());
                            addDateColumn(newRecord.getDate());
                        }
                        row.addNewAttendanceRecord(newRecord);
                    }
                    refreshTable(model);
                }
            }
        });
    }
    private void addDateColumn(LocalDate date) {
        TableColumn<AttendanceRow, String> dateColumn =
                new TableColumn<>(DateTimeFormatter.ofPattern("MMM d yyyy").format(date));

        dateColumn.setCellValueFactory(cellData -> {
            AttendanceRow row = cellData.getValue();
            return new SimpleStringProperty(row.getAttendanceForDate(date));
        });
        table.getColumns().add(dateColumn);
    }

    public Set<LocalDate> getAllAttendanceDates(Model model) {
        Set<LocalDate> allDates = new TreeSet<>();
        for (Student student : model.getStudentsByTutorialGroup(tutorialGroup)) {
            for (AttendanceRecord record : student.getAttendanceRecord()) {
                allDates.add(record.getDate());
            }
        }
        return allDates;
    }

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

    private void refreshTable(Model model) {
        data.clear();
        data.addAll(getStudentAttendanceRows(model));
        sortAndRefreshDates(model);
        table.refresh();
    }

    private void sortAndRefreshDates(Model model) {
        List<LocalDate> sortedDates = new ArrayList<>(getAllAttendanceDates(model));
        Collections.sort(sortedDates);
        table.getColumns().clear();
        TableColumn<AttendanceRow, String> studentNameColumn = new TableColumn<>("Student");
        studentNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentName()));
        table.getColumns().add(studentNameColumn);
        for (LocalDate date : sortedDates) {
            addDateColumn(date);
        }
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }
}

