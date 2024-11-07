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

//Code adapted from ChatGPT prompts, with modifications to fit the project

/**
 * Represents a window that displays the attendance of students in a tutorial group.
 */


public class AttendanceWindow {
    private static final String ICON_APPLICATION = "/images/TT_icon.png";
    private final TutorialGroup tutorialGroup;
    private TableView<AttendanceRow> table;
    private ObservableList<AttendanceRow> data;

    public AttendanceWindow(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    /**
     * Shows the attendance window.
     * @param model the model to get the data from
     */
    public void show(Model model) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("Attendance for Tutorial Group: " + tutorialGroup.toString());
            stage.getIcons().add(new javafx.scene.image.Image(ICON_APPLICATION));

            initializeTable(model);
            initializeStudentListListener(model);

            VBox vbox = new VBox(table);
            vbox.setAlignment(Pos.CENTER);
            vbox.setFillWidth(true);
            VBox.setVgrow(table, Priority.ALWAYS);
            table.setMinHeight(Region.USE_COMPUTED_SIZE);
            table.setMinWidth(Region.USE_COMPUTED_SIZE);

            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.show();
        });
    }

    /**
     * Closes the attendance window.
     */
    public void close() {
        Platform.runLater(() -> {
            Stage stage = (Stage) table.getScene().getWindow();
            stage.close();
        });
    }

    private void initializeTable(Model model) {
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

        for (AttendanceRow row : data) {
            addAttendanceListener(row, attendanceDates, model);
            for (AttendanceRecord record : row.student.getAttendanceRecord()) {
                record.addListener(observable -> refreshTable(model));
            }
        }
    }

    private void initializeStudentListListener(Model model) {
        model.getFilteredStudentList().addListener((ListChangeListener<Student>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    handleNewStudents(change.getAddedSubList(), model);
                }
                if (change.wasRemoved()) {
                    refreshTable(model);
                }
            }
        });
    }

    private void handleNewStudents(List<? extends Student> newStudents, Model model) {
        for (Student newStudent : newStudents) {
            System.out.println("New student added: " + newStudent.getName());
            AttendanceRow newRow = new AttendanceRow(newStudent);

            for (AttendanceRecord record : newStudent.getAttendanceRecord()) {
                newRow.addAttendance(record.getDate(), record.getAttendance().toString());
            }

            data.add(newRow);
            addAttendanceListener(newRow, getAllAttendanceDates(model), model);
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
                        newRecord.addListener(observable -> refreshTable(model));
                    }
                    refreshTable(model);
                }
                if (change.wasRemoved()) {
                    for (AttendanceRecord removedRecord : change.getRemoved()) {
                        attendanceDates.remove(removedRecord.getDate());
                        removeDateColumn(removedRecord.getDate());
                    }
                    refreshTable(model);
                }
            }
        });
    }

    private void addDateColumn(LocalDate date) {
        TableColumn<AttendanceRow, String> dateColumn =
                new TableColumn<>(DateTimeFormatter.ofPattern("MMM d yyyy").format(date));
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAttendanceForDate(date)));
        table.getColumns().add(dateColumn);
    }

    private void removeDateColumn(LocalDate date) {
        String columnHeader = DateTimeFormatter.ofPattern("MMM d yyyy").format(date);
        table.getColumns().removeIf(column -> column.getText().equals(columnHeader));
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
