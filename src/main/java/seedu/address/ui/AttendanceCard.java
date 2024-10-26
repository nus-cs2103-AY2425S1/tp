package seedu.address.ui;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Attendance;

/**
 * An UI component that displays information of a {@code Attendance}.
 */
public class AttendanceCard extends UiPart<Region> {

    private static final String FXML = "AttendanceCard.fxml";

    public final List<Attendance> attendanceList;
    public final String tutorial;

    @FXML
    private VBox attendanceCard;
    @FXML
    private Label subject;
    @FXML
    private Label attendance;
    @FXML
    private Label otherAttendance;

    /**
     * Creates a {@code AttendanceCard} of the given tutorial
     */
    public AttendanceCard(String tutorial, List<Attendance> attendanceList) {
        super(FXML);
        this.attendanceList = attendanceList;
        this.tutorial = tutorial;

        attendanceCard.setOnMouseClicked((MouseEvent event) -> {
            otherAttendance.setManaged(!otherAttendance.isManaged());
            otherAttendance.setVisible(!otherAttendance.isVisible());
        });
        subject.setText(tutorial);

        setAttendance();
    }

    private void setAttendance() {
        Attendance currentWeekAttendance = getCurrentWeekAttendance();
        if (attendanceList.isEmpty()) {
            attendance.setText(": not attended");
            otherAttendance.setText("No more attendance to show");
        } else if (currentWeekAttendance == null) {
            attendance.setText(": not attended");
            otherAttendance.setText(formatOtherAttendance(attendanceList));
        } else {
            attendance.setText(" Attended on: " + currentWeekAttendance.toDisplayString());
            List<Attendance> otherAttendanceList = attendanceList
                    .stream()
                    .filter(attendance -> !attendance.equals(currentWeekAttendance))
                    .toList();
            otherAttendance.setText(formatOtherAttendance(otherAttendanceList));
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

    private String formatOtherAttendance(List<Attendance> attendanceList) {
        if (attendanceList.isEmpty()) {
            return "No more attendance to show";
        }

        StringBuilder attendance = new StringBuilder();
        for (int i = 0; i < attendanceList.size(); i++) {
            attendance.append(attendanceList.get(i).toDisplayString());
            if (i != attendanceList.size() - 1) {
                attendance.append(", ");
            }
        }
        return "Other attendance: " + attendance;
    }
}
