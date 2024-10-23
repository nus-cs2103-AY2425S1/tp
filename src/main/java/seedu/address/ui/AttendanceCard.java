package seedu.address.ui;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Attendance;

/**
 * An UI component that displays information of a {@code Attendance}.
 */
public class AttendanceCard extends UiPart<Region> {

    private static final String FXML = "AttendanceCard.fxml";

    public final List<Attendance> attendanceList;
    public final String tutorial;

    @FXML
    private Label subject;
    @FXML
    private Label attendance;

    /**
     * Creates a {@code AttendanceCard} of the given tutorial
     */
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
