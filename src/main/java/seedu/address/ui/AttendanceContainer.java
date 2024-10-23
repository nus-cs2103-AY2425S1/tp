package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.participation.Participation;

/**
 * An UI component for displaying information related to attendance.
 */
public class AttendanceContainer extends UiPart<Region> {

    private static final String FXML = "AttendanceContainer.fxml";

    public final List<Participation> participationList;

    @FXML
    private Label tutorial;
    @FXML
    private Label week;
    @FXML
    private Label dateRange;
    @FXML
    private VBox attendanceList;

    /**
     * Creates a {@code AttendanceContainer} with the given list of participation list
     * to display the attendance of all tutorials.
     */
    public AttendanceContainer(List<Participation> participationList) {
        super(FXML);
        this.participationList = participationList;

        setDisplayDate();
        setAttendanceList();
        setTutorial();
    }

    private void setDisplayDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");

        week.setText("week " + today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        dateRange.setText(startOfWeek.format(formatter) + " ~ " + endOfWeek.format(formatter));
    }

    private void setAttendanceList() {
        participationList.forEach(participation -> attendanceList.getChildren()
                .add(new AttendanceCard(participation.getTutorial().getSubject(),
                        participation.getAttendanceList()).getRoot()));
    }

    private void setTutorial() {
        StringBuilder tutorials = new StringBuilder();
        for (int i = 0; i < participationList.size(); i++) {
            tutorials.append(participationList.get(i).getTutorial().getSubject());
            if (i != participationList.size() - 1) {
                tutorials.append(" | ");
            }
        }

        if (tutorials.isEmpty()) {
            tutorial.setText("Not enrolled in any tutorial");
        } else {
            tutorial.setText(tutorials.toString());
        }
        attendanceList.layout();
    }
}
