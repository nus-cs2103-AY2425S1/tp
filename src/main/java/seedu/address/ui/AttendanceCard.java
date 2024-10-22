package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AttendanceCard extends UiPart<Region> {

    private static final String FXML = "AttendanceCard.fxml";

    public final List<Attendance> attendanceList;
    public final String tutorial;

    @FXML
    private Label subject;
    @FXML
    private Label attendance;

    public AttendanceCard(String tutorial, List<Attendance> attendanceList) {
        super(FXML);
        this.attendanceList = attendanceList;
        this.tutorial = tutorial;

        subject.setText(tutorial);
        Attendance currentWeekAttendance = getCurrentWeekAttendance();

        if (currentWeekAttendance == null) {
            attendance.setText(": not attended");
        } else {
            attendance.setText(": " + currentWeekAttendance.toDisplayString());
        }
    }

    private Attendance getCurrentWeekAttendance() {
        int currentWeek = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        for (Attendance attendance : attendanceList) {
            int attendanceWeek = attendance.attendanceDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            if (attendanceWeek == currentWeek) {
                return attendance;
            }
        }
        return null;
    }

}