package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Attendance;

/**
 * An UI component that displays attendance of a student for a tutorial.
 */
public class AttendanceCard extends UiPart<Region> {

    private static final String FXML = "AttendanceCard.fxml";

    private final Logger logger = LogsCenter.getLogger(AttendanceCard.class);

    private final List<Attendance> attendanceList;
    private final String tutorial;

    @FXML
    private VBox attendanceCard;
    @FXML
    private Label subject;
    @FXML
    private Label attendance;
    @FXML
    private Label otherAttendance;
    @FXML
    private ImageView attendanceStatusIcon;

    /**
     * Creates a {@code AttendanceCard} of the given tutorial for the student.
     */
    public AttendanceCard(String tutorial, List<Attendance> attendanceList) {
        super(FXML);
        this.attendanceList = attendanceList;
        this.tutorial = tutorial;

        // sorts the attendance in descending order with latest dates first
        attendanceList.sort((a1, a2) -> {
            if (a2.attendanceDate.isAfter(a1.attendanceDate)) {
                return 1;
            } else if (a2.attendanceDate.isBefore(a1.attendanceDate)) {
                return -1;
            }
            return 0;
        });

        attendanceCard.setOnMouseClicked((MouseEvent event) -> {
            otherAttendance.setManaged(!otherAttendance.isManaged());
            otherAttendance.setVisible(!otherAttendance.isVisible());
        });

        subject.setText(tutorial);
        setAttendance();

        logger.info("Successfully created attendance card for tutorial: " + tutorial);
    }

    /**
     * Sets the UI component to display current week's attendance and other attendance.
     */
    private void setAttendance() {
        logger.info("Setting attendance display for tutorial: " + tutorial);

        Attendance currentWeekAttendance = getCurrentWeekAttendance();
        if (attendanceList.isEmpty()) {
            attendance.setText("not attended");
            setAttendanceLabelNotAttendedStyle();

            otherAttendance.setText("No more attendance to show");
        } else if (currentWeekAttendance == null) {
            attendance.setText("not attended");
            setAttendanceLabelNotAttendedStyle();

            otherAttendance.setText(formatOtherAttendance(attendanceList));
        } else {
            attendance.setText(currentWeekAttendance.toDisplayString());
            setAttendanceLabelAttendedStyle();

            List<Attendance> otherAttendanceList = attendanceList
                    .stream()
                    .filter(attendance -> !attendance.equals(currentWeekAttendance))
                    .toList();
            otherAttendance.setText(formatOtherAttendance(otherAttendanceList));
        }
    }

    /**
     * Returns {@code Attendance} of the current week.
     */
    private Attendance getCurrentWeekAttendance() {
        int currentWeek = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int currentYear = LocalDate.now().get(IsoFields.WEEK_BASED_YEAR);
        for (Attendance attendance : attendanceList) {
            int attendanceWeek = attendance.attendanceDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            int attendanceYear = attendance.attendanceDate.get(IsoFields.WEEK_BASED_YEAR);
            if (attendanceWeek == currentWeek && attendanceYear == currentYear) {
                return attendance;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the attendance list to be displayed in the UI.
     *
     * @param attendanceList The list of attendance that does not belong to the current week.
     */
    private String formatOtherAttendance(List<Attendance> attendanceList) {
        if (attendanceList.isEmpty()) {
            return "No more attendance to show";
        }

        StringBuilder attendance = new StringBuilder();
        for (int i = 0; i < attendanceList.size(); i++) {
            String attendanceDate = attendanceList.get(i).attendanceDate.format(
                    DateTimeFormatter.ofPattern("dd/MM/yy"));
            attendance.append(attendanceDate);
            if (i != attendanceList.size() - 1) {
                attendance.append(", ");
            }
        }
        return "Other attendance (" + attendanceList.size() + ") : " + attendance;
    }

    /**
     * Sets the style for label if there is no {@code Attendance} for the current week.
     */
    private void setAttendanceLabelNotAttendedStyle() {
        attendance.getStyleClass().clear();
        attendance.setStyle("-fx-text-fill: #E58D8D; -fx-padding: 0 0 0 4");

        Image icon = new Image("/images/cancel_icon.png");
        attendanceStatusIcon.setImage(icon);
    }

    /**
     * Sets the style for label if there is an {@code Attendance} for the current week.
     */
    private void setAttendanceLabelAttendedStyle() {
        attendance.getStyleClass().clear();
        attendance.setStyle("-fx-text-fill: #52A853; -fx-padding: 0 0 0 4");

        Image icon = new Image("/images/check_icon.png");
        attendanceStatusIcon.setImage(icon);
    }
}
